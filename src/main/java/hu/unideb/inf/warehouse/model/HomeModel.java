package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Place;
import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String getSumValue() {
        long value = 0;
        try {
            Query query = entityManager.createNativeQuery(
                    "SELECT CAST(sum(UnitPrice.price*Stock.quantity*(CAST(((100-Purveyor.discount))AS DECIMAL(20,2))/100))AS DECIMAL(20,0)) " +
                            "FROM Stock, UnitPrice, Purveyor " +
                            "WHERE Stock.unitPrice_id = UnitPrice.id AND Stock.purveyor_id = Purveyor.id");
            value = ((Number)query.getSingleResult()).intValue();
        } catch (Exception ex){
            logger.error("Hiba az adatatok lekérésekor: "+ex);
        }
        String stringValue = String.valueOf(value) + " Ft.";
        return stringValue;
    }

    /**
     * Telephelyek statisztikai adatainak letöltése.
     *
     */
    public ObservableList<PieChart.Data> buildChartData(){
        TypedQuery<Object[]> q = entityManager.createQuery(
                "SELECT d.label AS label, sum(b.price*a.quantity) AS weighting FROM Stock a, UnitPrice b, Purveyor c, Place d WHERE a.unitPrice = b AND a.purveyor = c AND a.place = d GROUP BY d.label", Object[].class);

        List<Object[]> resultList = q.getResultList();

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (Object[] result : resultList)
            data.add(new PieChart.Data((String)result[0].toString(), (Long)result[1]));

        return data;
    }

    /**
     * Adatbáziskapcsolat lezárása.
     */
    @Override
    public void close() throws Exception {

    }
}
