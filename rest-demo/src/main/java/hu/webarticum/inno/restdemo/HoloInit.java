package hu.webarticum.inno.restdemo;

import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import hu.webarticum.holodb.jpa.JpaMetamodelDriver;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.transaction.annotation.Transactional;

@Singleton
public class HoloInit {
    
    private final EntityManager entityManager;
    

    public HoloInit(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    
    @EventListener
    @Transactional
    public void onStartup(StartupEvent startupEvent) {
        JpaMetamodelDriver.setMetamodel(entityManager.getMetamodel());
    }
    
}
