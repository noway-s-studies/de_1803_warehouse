package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A kezdőlap adatait kezelő osztály.
 */
public class HomeModel implements AutoCloseable {
    /**
     * Logger osztály egy példánya.
     */
    private static final Logger logger = LoggerFactory.getLogger(PlaceModel.class);
    /**
     * EntityManager osztály egy példánya az adatbáziskapcsolat felépítéséhez.
     */
    private EntityManager entityManager;

    /**
     * A kezdőlap adatait kezelő osztály példányosítása, adatbáziskapcsolat felépítése.
     *
     */
    public HomeModel() {
        this.entityManager  = new EntityManagerFactoryUtil().getEntityManager();
    }

    /**
     * Telephelyek megnevezés adatainak listába rendezett lekérdezése.
     *
     * @return telephelyek megnevezés adatainak listálya
     */
    public int getSumValue() {
        int value = 0;
        try {
            Query query = entityManager.createNativeQuery(
                    "SELECT CAST(sum(UnitPrice.price*Stock.quantity*(CAST(((100-Purveyor.discount))AS DECIMAL(5,2))/100))AS DECIMAL(10,0)) " +
                            "FROM Stock, UnitPrice, Purveyor " +
                            "WHERE Stock.unitPrice_id = UnitPrice.id AND Stock.purveyor_id = Purveyor.id");
            value = ((Number)query.getSingleResult()).intValue();
        } catch (Exception ex){
            logger.error("Hiba az adatatok lekérésekor: "+ex);
        }
        return value;
    }


    /**
     * Adatbáziskapcsolat lezárása.
     */
    @Override
    public void close() throws Exception {

    }
}
