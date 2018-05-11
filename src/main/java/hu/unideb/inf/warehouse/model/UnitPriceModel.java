package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.UnitPrice;
import hu.unideb.inf.warehouse.pojo.UnitPrice;
import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Az egységár adatait kezelő osztály.
 *
 */
public class UnitPriceModel implements AutoCloseable {
    /**
     * Logger osztály egy példánya.
     */
    private static Logger logger = LoggerFactory.getLogger(UnitPriceModel.class);
    /**
     * EntityManager osztály egy példánya az adatbáziskapcsolat felépítéséhez.
     */
    private EntityManager entityManager;

    /**
     * Az egységár adatait kezelő osztály példányosítása, adatbáziskapcsolat felépítése.
     *
     */
    public UnitPriceModel() {
        this.entityManager  = new EntityManagerFactoryUtil().getEntityManager();
    }

    /**
     * Új egységár hozzáadása.
     *
     * @param UnitPrice hozzáadásra szánt egységár osztály egy példánya
     */
    public void addUnitPrice(UnitPrice UnitPrice) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(UnitPrice);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            logger.error("Hiba az adatatok betöltésekor: "+ex);
        }
    }

    /**
     * Az egységár adatainak módosítása.
     *
     * @param UnitPrice módosításra szánt egységár osztály egy példánya
     */
    public void modUnitPrice(UnitPrice UnitPrice) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(UnitPrice);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            logger.error("Hiba az adatatok módosításakor: "+ex);
        }
    }

    /**
     * Az egységár adatainak listába rendezett lekérdezése.
     *
     * @return egységárak adatainak listálya
     */
    public List<UnitPrice> getUnitPrice() {
        List<UnitPrice> list = null;
        try {
            TypedQuery<UnitPrice> query = entityManager.createQuery(
                    "SELECT u FROM UnitPrice u", UnitPrice.class);
            list = query.getResultList();
        } catch (Exception ex){
            logger.error("Hiba az adatatok lekérésekor: "+ex);
        }
        return list;
    }

    /**
     * Az egységár megnevezés adatainak listába rendezett lekérdezése.
     *
     * @return egységár megnevezés adatainak listálya
     */
    public List<String> getUnitPriceName() {
        List<String> list = null;
        try {
            Query query = entityManager.createNativeQuery(
                    "SELECT price FROM UnitPrice");
            list = query.getResultList();
        } catch (Exception ex){
            logger.error("Hiba az adatatok lekérésekor: "+ex);
        }
        return list;
    }

    /**
     * Az egységár adatainak torlése.
     *
     * @param UnitPrice törlésre szánt egységár osztály egy példánya
     */
    public void removeUnitPrice(UnitPrice UnitPrice) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(UnitPrice.class, UnitPrice.getId()));
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
