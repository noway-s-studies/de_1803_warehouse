package hu.unideb.inf.warehouse.utility;

import hu.unideb.inf.warehouse.app.MainApp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class EntityManagerFactoryUtil implements AutoCloseable{

    private static EntityManagerFactoryUtil emfu = new EntityManagerFactoryUtil();
    private static final String persistenceUnitName = "warehouseLocalDatabasa";
    static private EntityManagerFactory entityManagerFactory;

    public static EntityManagerFactoryUtil getInstance(){
        return emfu;
    }


    static {
        Map<String,String> properties = new HashMap<String, String>();
        properties.put("javax.persistence.jdbc.password", MainApp.DATABASE_PASSWORD);

        try {
            if (entityManagerFactory == null){
                entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName,properties);
            }
        } catch (Exception e) {
            System.out.println("Fatal: Unable to create entity manager factory");
            e.printStackTrace();
        }
    }

    static public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    @Override
    public void close() throws Exception {
        if(entityManagerFactory != null){
            entityManagerFactory.close();
        }
    }
}
