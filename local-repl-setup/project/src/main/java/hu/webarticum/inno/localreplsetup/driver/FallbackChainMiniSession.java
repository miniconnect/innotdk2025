package hu.webarticum.inno.localreplsetup.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.webarticum.miniconnect.api.MiniErrorException;
import hu.webarticum.miniconnect.api.MiniLargeDataSaveResult;
import hu.webarticum.miniconnect.api.MiniResult;
import hu.webarticum.miniconnect.api.MiniSession;

public class FallbackChainMiniSession implements MiniSession {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    private final List<MiniSession> sessionChain;

    public FallbackChainMiniSession(MiniSession... sessionChain) {
        this(Arrays.asList(sessionChain));
    }
    
    public FallbackChainMiniSession(Collection<MiniSession> sessionChain) {
        this.sessionChain = new ArrayList<>(sessionChain);
    }

    @Override
    public MiniResult execute(String query) {
        MiniResult result = null;
        List<Exception> exceptions = new ArrayList<>();
        for (MiniSession session : sessionChain) {
            if (session.isClosed()) {
                continue;
            }
            try {
                result = except(() -> execute(session, query));
                logger.debug("Executed with: " + session);
                break;
            } catch (Exception e) {
                logger.warn("Execution error, trying next session", e);
                exceptions.add(e);
            }
        }
        if (result == null) {
            if (exceptions.isEmpty()) {
                logger.error("No available session was found");
                throw createException("No available session was found");
            } else {
                logger.error("All session was failed to execute query");
                throw aggregateExceptions(exceptions, "Failed to execute query");
            }
        }
        return result;
    }
    
    private MiniResult except(Supplier<MiniResult> task) {
        MiniResult result = task.get();
        if (!result.success()) {
            throw new MiniErrorException(result.error());
        }
        return result;
    }

    @Override
    public MiniLargeDataSaveResult putLargeData(String variableName, long length, InputStream dataSource) {
        MiniLargeDataSaveResult result = null;
        List<Exception> exceptions = new ArrayList<>();
        for (MiniSession session : sessionChain) {
            if (session.isClosed()) {
                continue;
            }
            try {
                result = exceptLargeData(() -> session.putLargeData(variableName, length, dataSource));
                break;
            } catch (Exception e) {
                logger.warn("Large data putting error, trying next session", e);
                exceptions.add(e);
            }
        }
        if (result == null) {
            if (exceptions.isEmpty()) {
                logger.error("No available session was found");
                throw createException("No available session was found");
            } else {
                logger.error("All session was failed to execute query");
                throw aggregateExceptions(exceptions, "Failed to put large data");
            }
        }
        return result;
    }

    private MiniLargeDataSaveResult exceptLargeData(Supplier<MiniLargeDataSaveResult> task) {
        MiniLargeDataSaveResult result = task.get();
        if (!result.success()) {
            throw new MiniErrorException(result.error());
        }
        return result;
    }

    @Override
    public void close() {
        List<Exception> exceptions = new ArrayList<>();
        for (MiniSession session : sessionChain) {
            try {
                session.close();
            } catch (Exception e) {
                exceptions.add(e);
            }
        }
        if (!exceptions.isEmpty()) {
            throw aggregateExceptions(exceptions, "Failed to close some wrapped session");
        }
    }

    @Override
    public boolean isClosed() {
        for (MiniSession session : sessionChain) {
            if (!session.isClosed()) {
                return false;
            }
        }
        return true;
    }
    
    private MiniResult execute(MiniSession session, String query) {
        logger.debug("Running SQL '{}' with {}", query, session);
        long startTime = System.nanoTime();
        MiniResult result = session.execute(query);
        long endTime = System.nanoTime();
        long timeDiff = endTime - startTime;
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time: {}", String.format("%.6f", (timeDiff / 1_000_000_000.0)));
        }
        return result;
    }
    
    private RuntimeException aggregateExceptions(List<Exception> exceptions, String message) {
        for (Exception e : exceptions) {
            if (e instanceof MiniErrorException) {
                return (MiniErrorException) e;
            }
        }
        UncheckedIOException aggregatedException = createException(message);
        for (Exception e : exceptions) {
            aggregatedException.addSuppressed(e);
        }
        return aggregatedException;
    }
    
    private UncheckedIOException createException(String message) {
        return new UncheckedIOException(new IOException(message));
    }

}
