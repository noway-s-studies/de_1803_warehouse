package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Stock;
import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Az árukészlet adatait kezelő osztály.
 *
 */
public class StockModel  implements AutoCloseable {

    private static Logger logger = LoggerFactory.getLogger(StockModel.class);
    private EntityManager entityManager;

    /**
     * Az árukészlet adatait kezelő osztály példányosítása, adatbáziskapcsolat felépítése.
     *
     */
    public StockModel() {
        this.entityManager  = new EntityManagerFactoryUtil().getEntityManager();
    }

    /**
     * Új árukészlet hozzáadása.
     *
     * @param Stock hozzáadásra szánt árukészlet osztály egy példánya
     */
    public void addStock(Stock Stock) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(Stock);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            logger.error("Hiba az adatatok betöltésekor: "+ex);
        }
    }

    /**
     * Az árukészlet adatainak módosítása.
     *
     * @param Stock módosításra szánt árukészlet osztály egy példánya
     */
    public void modStock(Stock Stock) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(Stock);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            logger.error("Hiba az adatatok módosításakor: "+ex);
        }
    }

    /**
     * Az árukészlet adatainak listába rendezett lekérdezése.
     *
     * @return árukészletek adatainak listálya
     */
    public List<Stock> getStock() {
        List<Stock> list = null;
        try {
            TypedQuery<Stock> query = entityManager.createQuery(
                    "SELECT u FROM Stock u", Stock.class);
            list = query.getResultList();
        } catch (Exception ex){
            logger.error("Hiba az adatatok lekérésekor: "+ex);
        }
        return list;
    }

    /**
     * Az árukészlet adatainak torlése.
     *
     * @param Stock törlésre szánt árukészlet osztály egy példánya
     */
    public void removeStock(Stock Stock) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Stock.class, Stock.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            logger.error("Hiba az adatatok törlésekor: "+ex);
        }
    }

    /**
     * Adatbáziskapcsolat lezárása.
     */
    @Override
    public void close() {
        this.entityManager.close();
    }
}
