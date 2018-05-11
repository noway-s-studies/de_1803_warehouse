package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Product;
import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Az áru adatait kezelő osztály.
 *
 */
public class ProductModel  implements AutoCloseable {
    /**
     * Logger osztály egy példánya.
     */
    private static Logger logger = LoggerFactory.getLogger(ProductModel.class);
    /**
     * EntityManager osztály egy példánya az adatbáziskapcsolat felépítéséhez.
     */
    private EntityManager entityManager;

    /**
     * Az áru adatait kezelő osztály példányosítása, adatbáziskapcsolat felépítése.
     *
     */
    public ProductModel() {
        this.entityManager  = new EntityManagerFactoryUtil().getEntityManager();
    }

    /**
     * Új áru hozzáadása.
     *
     * @param Product hozzáadásra szánt áru osztály egy példánya
     */
    public void addProduct(Product Product) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(Product);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            logger.error("Hiba az adatatok betöltésekor: "+ex);
        }
    }

    /**
     * Az áru adatainak módosítása.
     *
     * @param product módosításra szánt áru osztály egy példánya
     */
    public void modProduct(Product product) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(product);
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            logger.error("Hiba az adatatok módosításakor: "+ex);
        }
    }

    /**
     * Az áru adatainak listába rendezett lekérdezése.
     *
     * @return áruk adatainak listálya
     */
    public List<Product> getProduct() {
        List<Product> list = null;
        try {
            TypedQuery<Product> query = entityManager.createQuery(
                    "SELECT u FROM Product u", Product.class);
            list = query.getResultList();
        } catch (Exception ex){
            logger.error("Hiba az adatatok lekérésekor: "+ex);
        }
        return list;
    }

    /**
     * Az áru megnevezés adatainak listába rendezett lekérdezése.
     *
     * @return áru megnevezés adatainak listálya
     */
    public List<String> getProductName() {
        List<String> list = null;
        try {
            Query query = entityManager.createNativeQuery(
                    "SELECT label FROM Product");
            list = query.getResultList();
        } catch (Exception ex){
            logger.error("Hiba az adatatok lekérésekor: "+ex);
        }
        return list;
    }

    /**
     * Az áru adatainak torlése.
     *
     * @param Product törlésre szánt áru osztály egy példánya
     */
    public void removeProduct(Product Product) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Product.class, Product.getId()));
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
