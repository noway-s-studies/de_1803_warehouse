package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.UnitPrice;
import hu.unideb.inf.warehouse.pojo.UnitPrice;
import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class UnitPriceModel implements AutoCloseable {

    private EntityManager entityManager;

    public UnitPriceModel() {
        this.entityManager  = new EntityManagerFactoryUtil().getEntityManager();
    }

    public void addUnitPrice(UnitPrice UnitPrice) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(UnitPrice);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba a/az '"+UnitPrice.getClass().toString()+"' osztály adatainak betöltésekor:\n");
        }
    }

    public void modUnitPrice(UnitPrice UnitPrice) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(UnitPrice);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba a/az '"+UnitPrice.getClass().toString()+"' osztály adatainak betöltésekor:\n");
        }
    }

    public List<UnitPrice> getUnitPrice() {
        List<UnitPrice> list = null;
        try {
            TypedQuery<UnitPrice> query = entityManager.createQuery(
                    "SELECT u FROM UnitPrice u", UnitPrice.class);
            list = query.getResultList();
        } catch (Exception ex){
            System.out.println("Hiba az 'Egységár' adatainak lekérdezésekor:\n" + ex);
        }
        return list;
    }

    public List<String> getUnitPriceName() {
        List<String> list = null;
        try {
            Query query = entityManager.createNativeQuery(
                    "SELECT price FROM UnitPrice");
            list = query.getResultList();
        } catch (Exception ex){
            System.out.println("Hiba az 'Áru' adatainak lekérdezéskor:\n" + ex);
        }
        return list;
    }
    public void removeUnitPrice(UnitPrice UnitPrice) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(UnitPrice.class, UnitPrice.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba az 'Egységár' adatainak törlésekor:\n" + ex);
        }
    }

    @Override
    public void close() {
        this.entityManager.close();
    }
}
