package hu.webarticum.inno.restdemo;

import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

import java.lang.reflect.Method;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.transaction.annotation.Transactional;

@Singleton
@Requires(env = "demo")
public class HoloInit {
    
    private final EntityManager entityManager;
    

    public HoloInit(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    
    @EventListener
    @Transactional
    public void onStartup(StartupEvent startupEvent) throws ReflectiveOperationException {
        Class<?> driverClazz = Class.forName("hu.webarticum.holodb.jpa.JpaMetamodelDriver");
        Method setMetamodel = driverClazz.getMethod("setMetamodel", Object.class);
        setMetamodel.invoke(null, entityManager.getMetamodel());
    }
    
}
