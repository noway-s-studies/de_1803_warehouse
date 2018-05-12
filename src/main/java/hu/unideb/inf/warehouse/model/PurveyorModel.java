package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Purveyor;
import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * A beszerző adatait kezelő osztály.
 *
 */
public class PurveyorModel implements AutoCloseable {
    /**
     * Logger osztály egy példánya.
     */
    private static final Logger logger = LoggerFactory.getLogger(PurveyorModel.class);
    /**
     * EntityManager osztály egy példánya az adatbáziskapcsolat felépítéséhez.
     */
    private EntityManager entityManager;

    /**
     * A beszerző adatait kezelő osztály példányosítása, adatbáziskapcsolat felépítése.
     *
     */
    public PurveyorModel() {
        this.entityManager  = new EntityManagerFactoryUtil().getEntityManager();
    }

    /**
     * Új beszerző hozzáadása.
     *
     * @param purveyor hozzáadásra szánt beszerző osztály egy példánya
     */
    public void addPurveyor(Purveyor purveyor) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(purveyor);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            logger.error("Hiba az adatatok betöltésekor: "+ex);
        }
    }

    /**
     * A beszerző adatainak módosítása.
     *
     * @param purveyor módosításra szánt beszerző osztály egy példánya
     */
    public void modPurveyor(Purveyor purveyor) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(purveyor);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            logger.error("Hiba az adatatok módosításakor: "+ex);
        }
    }

    /**
     * A beszerző adatainak listába rendezett lekérdezése.
     *
     * @return beszerzők adatainak listálya
     */
    public List<Purveyor> getPurveyor() {
        List<Purveyor> list = null;
        try {
            TypedQuery<Purveyor> query = entityManager.createQuery(
                    "SELECT u FROM Purveyor u", Purveyor.class);
            list = query.getResultList();
        } catch (Exception ex){
            logger.error("Hiba az adatatok lekérésekor: "+ex);
        }
        return list;
    }

    /**
     * A beszerző megnevezés adatainak listába rendezett lekérdezése.
     *
     * @return beszerzők megnevezés adatainak listálya
     */
    public List<String> getPurveyorName() {
        List<String> list = null;
        try {
            Query query = entityManager.createNativeQuery(
                    "SELECT label FROM Purveyor");
            list = query.getResultList();
        } catch (Exception ex){
            logger.error("Hiba az adatatok lekérésekor: "+ex);
        }
        return list;
    }

    /**
     * A beszerző adatainak torlése.
     *
     * @param purveyor törlésre szánt beszerző osztály egy példánya
     */
    public void removePurveyor(Purveyor purveyor) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Purveyor.class, purveyor.getId()));
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
