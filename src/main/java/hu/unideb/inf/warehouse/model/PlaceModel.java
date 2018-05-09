package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Place;
import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * A telephelyek adatait kezelő osztály.
 *
 */
public class PlaceModel {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public PlaceModel() {
        this.entityManagerFactory  = EntityManagerFactoryUtil.getInstance().getEntityManagerFactory();
        this.entityManager  = entityManagerFactory.createEntityManager();
    }

    /**
     * Letárolja a telephelyet.
     *
     */
    public void addPlace(Object object) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(object);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba a/az '"+object.getClass().toString()+"' osztály adatainak betöltésekor:\n");
        } finally {
            entityManager.close();
        }
    }

    /**
     * Listába rendezve visszaadja a telephelyekről tárolt adatokat.
     *
     * @return telephelyekről tárolt adatok listája
     */
    public List<Place> getPlace() {
        List<Place> list = null;
        try {
            TypedQuery<Place> query = entityManager.createQuery("SELECT u FROM Place u", Place.class);
            list = query.getResultList();
        } catch (Exception ex){
            System.out.println("Hiba a 'Telephely' adatainak lekérdezéskor:\n" + ex);
        } finally {
            entityManager.close();
        }
        return list;
    }

    /**
     * Törli a paraméterként kapott telephelyet.
     *
     * @param place telephelyet reprezentáló osztály
     */
    public void removeProduct(Place place) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Place.class, place.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba az 'Place' osztály adatainak törlésekor:\n" + ex);
        } finally {
            entityManager.close();
        }
    }
}