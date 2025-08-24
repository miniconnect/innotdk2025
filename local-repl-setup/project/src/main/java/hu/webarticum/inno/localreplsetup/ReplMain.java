package hu.webarticum.inno.localreplsetup;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.webarticum.holodb.bootstrap.factory.ConfigLoader;
import hu.webarticum.holodb.bootstrap.factory.EngineBuilder;
import hu.webarticum.holodb.bootstrap.factory.StorageAccessFactory;
import hu.webarticum.holodb.config.HoloConfig;
import hu.webarticum.inno.localreplsetup.driver.FallbackChainMiniSession;
import hu.webarticum.miniconnect.jdbcadapter.JdbcAdapterSession;
import hu.webarticum.minibase.calcite.driver.MinibaseCalciteSchema;
import hu.webarticum.minibase.engine.api.TackedEngine;
import hu.webarticum.minibase.engine.facade.FrameworkSession;
import hu.webarticum.minibase.engine.facade.FrameworkSessionManager;
import hu.webarticum.minibase.storage.api.StorageAccess;
import hu.webarticum.miniconnect.api.MiniSession;
import hu.webarticum.miniconnect.client.client.SqlRepl;
import hu.webarticum.miniconnect.client.repl.RichReplRunner;

public class ReplMain {
    
    private static final String DEFAULT_SCHEMA = "economy";
    
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    private Thread watchThread = null;

    public void run(String configFilePath) {
        try (TackedEngine engine = loadDynamicEngine(configFilePath)) {
            try (MiniSession session = createCompundSession(engine)) {
                new RichReplRunner().run(new SqlRepl(session));
            }
        }
        if (watchThread != null) {
            watchThread.interrupt();
        }
    }

    public static void main(String[] args) {
        new ReplMain().run(args[0]);
    }

    private TackedEngine loadDynamicEngine(String configFilePath) {
        StorageAccess[] storageAccessRef = new StorageAccess[] { loadStorageAccess(configFilePath) };
        startWatchingConfigFile(configFilePath, () -> storageAccessRef[0] = loadStorageAccess(configFilePath));
        return EngineBuilder.ofStorageAccessSupplier(() -> storageAccessRef[0]).build();
    }

    private void startWatchingConfigFile(String configFilePath, Runnable consumer) {
        watchThread = new Thread(() -> watchConfigFile(configFilePath, consumer));
        watchThread.start();
    }
    
    private void watchConfigFile(String configFilePath, Runnable callback) {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            Path filePath = Paths.get(configFilePath);
            Path dirPath = filePath.getParent();
            dirPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE);
            logger.info("Watching file: {} ...", filePath.getFileName());
            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    Path changedPath = (Path) event.context();
                    if (changedPath.equals(filePath.getFileName())) {
                        String kindName = event.kind().name();
                        logger.info("File {} has changed ({})", changedPath, kindName);
                        callback.run();
                    }
                }
                if (!key.reset()) {
                    logger.info("WatchKey is no longer valid");
                    break;
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private StorageAccess loadStorageAccess(String configFilePath) {
        HoloConfig config = loadConfig(configFilePath);
        return StorageAccessFactory.createStorageAccess(config);
    }
    
    private HoloConfig loadConfig(String configFilePath) {
        File configFile = new File(configFilePath);
        return new ConfigLoader(configFile).load();
    }
    
    private MiniSession createCompundSession(TackedEngine engine) {
        MiniSession frameworkSession = openMiniSession(engine);
        MiniSession calciteSession = openCalciteSessionOver(engine);
        return new FallbackChainMiniSession(frameworkSession, calciteSession);
    }

    private MiniSession openCalciteSessionOver(TackedEngine engine) {
        try {
            return openCalciteSessionOverThrowing(engine);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    private MiniSession openMiniSession(TackedEngine engine) {
        FrameworkSession frameworkSession = new FrameworkSessionManager(engine).openSession();
        frameworkSession.engineSession().state().setCurrentSchema(DEFAULT_SCHEMA);
        return frameworkSession;
    }
    
    private MiniSession openCalciteSessionOverThrowing(TackedEngine engine) throws SQLException {
        DriverManager.registerDriver(new org.apache.calcite.jdbc.Driver());
        Properties properties = new Properties();
        properties.setProperty("lex", org.apache.calcite.config.Lex.JAVA.name());
        properties.setProperty("caseSensitive", Boolean.toString(false));
        Connection connection = DriverManager.getConnection("jdbc:calcite:", properties); // NOSONAR intentionally unclosed
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        calciteConnection.setSchema(DEFAULT_SCHEMA);
        SchemaPlus schemaPlus = calciteConnection.getRootSchema();
        registerSchemasInCalcite(schemaPlus, engine);
        return new JdbcAdapterSession(connection);
    }
    
    private void registerSchemasInCalcite(SchemaPlus schemaPlus, TackedEngine engine) {
        for (String schemaName : engine.storageAccess().schemas().names()) {
            Schema dynamicSchema = new MinibaseCalciteSchema(() -> engine.storageAccess().schemas().get(schemaName));
            schemaPlus.add(schemaName, dynamicSchema);
        }
    }

}
