package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Product;
import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


public class ProductModel  implements AutoCloseable {

    private EntityManager entityManager;

    public ProductModel() {
        this.entityManager  = new EntityManagerFactoryUtil().getEntityManager();
    }

    public void addProduct(Product Product) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(Product);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba a/az '"+Product.getClass().toString()+"' osztály adatainak betöltésekor:\n");
        }
    }

    public void modProduct(Product product) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(product);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba a/az '"+product.getClass().toString()+"' osztály adatainak betöltésekor:\n");
        }
    }

    public List<Product> getProduct() {
        List<Product> list = null;
        try {
            TypedQuery<Product> query = entityManager.createQuery(
                    "SELECT u FROM Product u", Product.class);
            list = query.getResultList();
        } catch (Exception ex){
            System.out.println("Hiba az 'Áru' adatainak lekérdezéskor:\n" + ex);
        }
        return list;
    }

    public List<String> getProductName() {
        List<String> list = null;
        try {
            Query query = entityManager.createNativeQuery(
                    "SELECT label FROM Product");
            list = query.getResultList();
        } catch (Exception ex){
            System.out.println("Hiba az 'Áru' adatainak lekérdezéskor:\n" + ex);
        }
        return list;
    }

    public void removeProduct(Product Product) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Product.class, Product.getId()));
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
