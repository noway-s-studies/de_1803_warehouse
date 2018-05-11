package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Place;
import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * A telephelyek adatait kezelő osztály.
 *
 */
public class PlaceModel  implements AutoCloseable {
    /**
     * Logger osztály egy példánya.
     */
    private static Logger logger = LoggerFactory.getLogger(PlaceModel.class);
    /**
     * EntityManager osztály egy példánya az adatbáziskapcsolat felépítéséhez.
     */
    private EntityManager entityManager;

    /**
     * A telephelyek adatait kezelő osztály példányosítása, adatbáziskapcsolat felépítése.
     *
     */
    public PlaceModel() {
        this.entityManager  = new EntityManagerFactoryUtil().getEntityManager();
    }

    /**
     * Új telephely hozzáadása.
     *
     * @param place hozzáadásra szánt telephely osztály egy példánya
     */
    public void addPlace(Place place) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(place);
            entityManager.getTransaction().commit();
            logger.info("Új adat betöltése.");
        } catch (Exception ex){
            logger.error("Hiba az adatatok betöltésekor: "+ex);
        }
    }

    /**
     * Telephely adatainak módosítása.
     *
     * @param place módosításra szánt telephely osztály egy példánya
     */
    public void modPlace(Place place) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(place);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            logger.error("Hiba az adatatok módosításakor: "+ex);
        }
    }

    /**
     * Telephelyek adatainak listába rendezett lekérdezése.
     *
     * @return telephelyek adatainak listálya
     */
    public List<Place> getPlace() {
        List<Place> list = null;
        try {
            TypedQuery<Place> query = entityManager.createQuery(
                    "SELECT u FROM Place u", Place.class);
            list = query.getResultList();
        } catch (Exception ex){
            logger.error("Hiba az adatatok lekérésekor: "+ex);
        }
        return list;
    }

    /**
     * Telephelyek megnevezés adatainak listába rendezett lekérdezése.
     *
     * @return telephelyek megnevezés adatainak listálya
     */
    public List<String> getPlaceName() {
        List<String> list = null;
        try {
            Query query = entityManager.createNativeQuery(
                    "SELECT label FROM Place");
            list = query.getResultList();
        } catch (Exception ex){
            logger.error("Hiba az adatatok lekérésekor: "+ex);
        }
        return list;
    }

    /**
     * Telephely adatainak torlése.
     *
     * @param Place törlésre szánt telephely osztály egy példánya
     */
    public void removePlace(Place Place) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Place.class, Place.getId()));
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
