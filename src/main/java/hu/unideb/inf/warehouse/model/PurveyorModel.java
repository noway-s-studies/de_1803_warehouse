package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Purveyor;
import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class PurveyorModel implements AutoCloseable {

    private EntityManager entityManager;

    public PurveyorModel() {
        this.entityManager  = new EntityManagerFactoryUtil().getEntityManager();
    }

    public void addPurveyor(Purveyor purveyor) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(purveyor);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba a/az '"+purveyor.getClass().toString()+"' osztály adatainak betöltésekor:\n");
        }
    }
    public void modPurveyor(Purveyor purveyor) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(purveyor);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba a/az '"+purveyor.getClass().toString()+"' osztály adatainak betöltésekor:\n");
        }
    }

    public List<Purveyor> getPurveyor() {
        List<Purveyor> list = null;
        try {
            TypedQuery<Purveyor> query = entityManager.createQuery(
                    "SELECT u FROM Purveyor u", Purveyor.class);
            list = query.getResultList();
        } catch (Exception ex){
            System.out.println("Hiba a 'Beszerző' adatainak lekérdezéskor:\n" + ex);
        }
        return list;
    }

    public List<Purveyor> getPurveyorBy() {
        List<Purveyor> list = null;
        try {
            Query query = entityManager.createNativeQuery(
                    "SELECT * FROM Purveyor", Purveyor.class);
            list = query.getResultList();
        } catch (Exception ex){
            System.out.println("Hiba a 'Beszerző' adatainak lekérdezéskor:\n" + ex);
        }
        return list;
    }

    public void removePurveyor(Purveyor purveyor) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Purveyor.class, purveyor.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba az 'Beszerző' adatainak törlésekor:\n" + ex);
        }
    }

    @Override
    public void close() {
        this.entityManager.close();
    }
}
