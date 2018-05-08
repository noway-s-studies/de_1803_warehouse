package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.UnitPrice;
import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class UnitPriceModel {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public UnitPriceModel() {
        this.entityManagerFactory  = EntityManagerFactoryUtil.getInstance().getEntityManagerFactory();
        this.entityManager  = entityManagerFactory.createEntityManager();
    }

    public void addUnitPrice(Object object) {
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
    public List<UnitPrice> getUnitPrice() {
        List<UnitPrice> list = null;
        try {
            TypedQuery<UnitPrice> query = entityManager.createQuery("SELECT u FROM UnitPrice u", UnitPrice.class);
            list = query.getResultList();
        } catch (Exception ex){
            System.out.println("Hiba az 'Egységár' adatainak lekérdezéskor:\n" + ex);
        } finally {
            entityManager.close();
        }
        return list;
    }

    public void removePurveyor(UnitPrice unitPrice) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(UnitPrice.class, unitPrice.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba az 'Egységár' adatainak törlésekor:\n" + ex);
        } finally {
            entityManager.close();
        }
    }
}
