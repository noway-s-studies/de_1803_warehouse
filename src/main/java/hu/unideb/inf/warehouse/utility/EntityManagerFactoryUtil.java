package hu.unideb.inf.warehouse.utility;

import hu.unideb.inf.warehouse.app.MainApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;


/**
 * Az adatbáziskapcsolat kiépítéséért és leállításáért felelős osztály.
 *
 */
public class EntityManagerFactoryUtil implements AutoCloseable{
    /**
     * Logger osztály egy példánya.
     */
    private static Logger logger = LoggerFactory.getLogger(EntityManagerFactoryUtil.class);
    /**
     * Adatbázis beállítások hivatkozásának neve.
     */
    private static final String persistenceUnitName = "warehouseLocalDatabasa";
    /**
     * Az EntityManagerFactoryUtil osztály egy példánya.
     */
    private static EntityManagerFactoryUtil emfu = new EntityManagerFactoryUtil();
    /**
     * Az EntityManagerFactory osztály egy példánya.
     */
    private static EntityManagerFactory entityManagerFactory;

    static {
        Map<String,String> properties = new HashMap<String, String>();
        properties.put("javax.persistence.jdbc.password", MainApp.DATABASE_PASSWORD);

        try {
            if (entityManagerFactory == null){
                entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName,properties);
                logger.info("Adatbázis kapcsolat létrejött!");
            }
        } catch (Exception ex) {
            System.out.println("Fatal: Unable to create entity manager factory");
            logger.error("Adatbázis kapcsoódási hiba: "+ex);
        }
    }

    /**
     * Visszaad egy az adatbáziskapcsolathoz használt EntityManagerFactoryUtil objektumot.
     *
     * @return EntityManagerFactoryUtil objektum
     */
    public static EntityManagerFactoryUtil getInstance(){
        return emfu;
    }

    /**
     * Visszaad egy az adatbáziskapcsolathoz használt EntityManagerFactory objektumot.
     *
     * @return EntityManagerFactory objektum
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }


    /**
     * Visszaad egy az adatbáziskapcsolathoz használt EntityManager objektumot.
     *
     * @return EntityManager objektum
     */
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    /**
     * Az adatbáziskapcsolat bezárására használt metódus.
     *
     */
    @Override
    public void close() throws Exception {
        if(entityManagerFactory != null){
            logger.info("Adatbázis kapcsolat szétkapcsolva!");
            entityManagerFactory.close();
        }
    }
}
