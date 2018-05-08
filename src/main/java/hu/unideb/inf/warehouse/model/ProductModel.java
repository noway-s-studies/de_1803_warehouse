package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Product;
import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;


public class ProductModel {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public ProductModel() {
        this.entityManagerFactory  = EntityManagerFactoryUtil.getInstance().getEntityManagerFactory();
        this.entityManager  = entityManagerFactory.createEntityManager();
    }
    /**
     * Letárolja a telephelyet.
     *
     */
    public void addProduct(Object object) {
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
    public List<Product> getProduct() {
        List<Product> list = null;
        try {
            TypedQuery<Product> query = entityManager.createQuery("SELECT u FROM Product u", Product.class);
            list = query.getResultList();
        } catch (Exception ex){
            System.out.println("Hiba az 'Áru' adatainak lekérdezéskor:\n" + ex);
        } finally {
            entityManager.close();
        }
        return list;
    }

    public void removeProduct(Product product) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Product.class, product.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            System.out.println("Hiba az 'Product' osztály adatainak törlésekor:\n" + ex);
        } finally {
            entityManager.close();
        }
    }

//    public List<Product> getProductPerPlace(Place place) {
//        EntityManager em = emf.createEntityManager();
//        List<Product> list = null;
//        try {
//            TypedQuery<Product> query = em.createQuery("SELECT p FROM UnitPrice p WHERE p.user = :place", Post.class);
//            query.setParameter("place", place);
//            list = query.getResultList();
//        } catch (Exception ex){
//            System.out.println("Hiba az 'Raktárankénti árúkészlet' adatainak lekérdezéskor:\n" + ex);
//        } finally {
//            em.close();
//        }
//        return list;
//    }

//
//    public List<Post> getLastPostOfEachThread() {
//        EntityManager em = emf.createEntityManager();
//        TypedQuery<Post> postQuery = em.createQuery("SELECT p FROM Post p WHERE p.postTime = ("
//                + "SELECT MAX(p2.postTime) FROM Post p2 WHERE p2.thread = p.thread"
//                + ")", Post.class);
//        List<Post> postList = postQuery.getResultList();
//        em.close();
//        return postList;
//
//    }
}
