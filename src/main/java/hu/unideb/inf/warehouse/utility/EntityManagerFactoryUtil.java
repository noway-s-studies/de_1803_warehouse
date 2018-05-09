package hu.unideb.inf.warehouse.utility;

import hu.unideb.inf.warehouse.app.MainApp;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class EntityManagerFactoryUtil implements AutoCloseable{

    private static EntityManagerFactoryUtil emfu = new EntityManagerFactoryUtil();
    private static final String persistenceUnitName = "PostgreSQL";
    private EntityManagerFactoryUtil(){}
    private EntityManagerFactory entityManagerFactory;

    public static EntityManagerFactoryUtil getInstance(){
        return emfu;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        Map<String,String> properties = new HashMap<String, String>();
        properties.put("javax.persistence.jdbc.password", MainApp.DATABASE_PASSWORD);
        if (entityManagerFactory == null){
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName,properties);
        }
        return entityManagerFactory;
    }

    @Override
    public void close() throws Exception {
        if(entityManagerFactory != null){
            entityManagerFactory.close();
        }
    }
}
