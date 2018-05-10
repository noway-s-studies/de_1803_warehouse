package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Place;
import hu.unideb.inf.warehouse.pojo.Place;
import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * A telephelyek adatait kezelő osztály.
 *
 */
public class PlaceModel  implements AutoCloseable {

    private EntityManager entityManager;

    public PlaceModel() {
        this.entityManager  = new EntityManagerFactoryUtil().getEntityManager();
    }

    public void addPlace(Place place) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(place);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba a/az '"+place.getClass().toString()+"' osztály adatainak betöltésekor:\n");
        }
    }

    public void modPlace(Place place) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(place);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba a/az '"+place.getClass().toString()+"' osztály adatainak betöltésekor:\n");
        }
    }

    public List<Place> getPlace() {
        List<Place> list = null;
        try {
            TypedQuery<Place> query = entityManager.createQuery(
                    "SELECT u FROM Place u", Place.class);
            list = query.getResultList();
        } catch (Exception ex){
            System.out.println("Hiba a 'Telephely' adatainak lekérdezéskor:\n" + ex);
        }
        return list;
    }

    public List<String> getPlaceName() {
        List<String> list = null;
        try {
            Query query = entityManager.createNativeQuery(
                    "SELECT label FROM Place");
            list = query.getResultList();
        } catch (Exception ex){
            System.out.println("Hiba a 'Telephely' név adatainak lekérdezéskor:\n" + ex);
        }
        return list;
    }
    public void removePlace(Place Place) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Place.class, Place.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba a 'Telephely' adatainak törlésekor:\n" + ex);
        }
    }

    @Override
    public void close() {
        this.entityManager.close();
    }

}
