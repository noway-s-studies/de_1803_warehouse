package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Stock;
import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class StockModel  implements AutoCloseable {

    private EntityManager entityManager;

    public StockModel() {
        this.entityManager  = new EntityManagerFactoryUtil().getEntityManager();
    }

    public void addStock(Stock Stock) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(Stock);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba a/az '"+Stock.getClass().toString()+"' osztály adatainak betöltésekor:\n");
        }
    }

    public void modStock(Stock Stock) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(Stock);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba a/az '"+Stock.getClass().toString()+"' osztály adatainak betöltésekor:\n");
        }
    }

    public List<Stock> getStock() {
        List<Stock> list = null;
        try {
            TypedQuery<Stock> query = entityManager.createQuery(
                    "SELECT u FROM Stock u", Stock.class);
            list = query.getResultList();
        } catch (Exception ex){
            System.out.println("Hiba a 'Beszerző' adatainak lekérdezéskor:\n" + ex);
        }
        return list;
    }

    public void removeStock(Stock Stock) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Stock.class, Stock.getId()));
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
