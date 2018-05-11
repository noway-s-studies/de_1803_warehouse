package hu.unideb.inf.warehouse.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Egy árukészletet reprezentáló osztály.
 *
 */
@Entity
public class Stock {

    /**
     * Az árukészlet egyedi azonosítója. Autómatikusan generált.
     */
    @Id
    @GeneratedValue
    /**
     * Az árukészlet betöltésének ideje.
     */
    private Long id;
    /**
     * Az árukészlet státusza.
     */
    private Date recorded;
    /**
     * Az árukészlet megnevezése. Egyedi azonosító.
     */
    private boolean status;
    /**
     * Az árukészlethez kapcsolódó beszerző objektum.
     */
    @ManyToOne
    private Purveyor purveyor;
    /**
     * Az árukészlethez kapcsolódó áru objektum.
     */
    @ManyToOne
    private Product product;
    /**
     * Az árukészlethez kapcsolódó telephely objektum.
     */
    @ManyToOne
    private Place place;
    /**
     * Az árukészlethez kapcsolódó egységár objektum.
     */
    @ManyToOne
    private UnitPrice unitPrice;
    /**
     * Az árukészlet mennyisége.
     */
    private int quantity;

    /**
     * Konstruktor, mely létrehoz egy árukészletet reprezentáló objektumot.
     */
    public Stock() {
        super();
    }

    /**
     * Konstruktor, mely létrehoz egy árukészletet reprezentáló objektumot.
     *
     * @param purveyor beszerzőt reprezentáló osztály
     * @param product árut reprezentáló osztály
     * @param place telephelyet reprezentáló osztály
     * @param unitPrice egységárat reprezentáló osztály
     * @param quantity mennyiség
     */
    public Stock(Purveyor purveyor,
                 Product product,
                 Place place,
                 UnitPrice unitPrice,
                 int quantity) {
        this.status = true;
        this.recorded = new Date();
        this.purveyor = purveyor;
        this.product = product;
        this.place = place;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    /**
     * Visszaadja az árukészlet egyedi azonosítója.
     *
     * @return egyedi azonosító
     */
    public Long getId() {
        return id;
    }

    /**
     * Visszaadja az árukészlet rögzítési dátumát.
     *
     * @return rögzítési dátum
     */
    public Date getRecorded() {
        return recorded;
    }

    /**
     * Visszaadja az árukészlet státuszát.
     * Ha igaz, az árukészlet aktív állapotú, a mennyiség nagyobb mint 0.
     *
     * @return státusz
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Beállítja az árukészlet státuszát.
     * Hamis értéket vesz fel ha a mennyiség 0, nem lehet újra igaz.
     *
     * @param status státusz
     *               az árukészlet státusza
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Visszaadja az árukészlethez kapcsolódó beszerzőt reprezentáló osztályt.
     *
     * @return  beszerzőt reprezentáló osztály
     */
    public Purveyor getPurveyor() {
        return purveyor;
    }

    /**
     * Beállítja az árukészlethez kapcsolódó beszerzőt reprezentáló osztályt.
     *
     * @param purveyor beszerzőt reprezentáló osztály
     *                 az árukészlethez kapcsolódó beszerzőt reprezentáló osztály
     */
    public void setPurveyor(Purveyor purveyor) {
        this.purveyor = purveyor;
    }

    /**
     * Visszaadja az árukészlethez kapcsolódó árut reprezentáló osztályt.
     *
     * @return árut reprezentáló osztály
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Beállítja az árukészlethez kapcsolódó árut reprezentáló osztályt.
     *
     * @param product árut reprezentáló osztály
     *                az árukészlethez kapcsolódó árut reprezentáló osztály
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Visszaadja az árukészlethez kapcsolódó telephelyet reprezentáló osztályt.
     *
     * @return telephelyet reprezentáló osztály
     */
    public Place getPlace() {
        return place;
    }

    /**
     * Beállítja az árukészlethez kapcsolódó telephelyet reprezentáló osztályt.
     *
     * @param place telephelyet reprezentáló osztály
     *              az árukészlethez kapcsolódó telephelyet reprezentáló osztályt
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * Visszaadja az árukészlethez kapcsolódó egységárat reprezentáló osztályt.
     *
     * @return egységárat reprezentáló osztály
     */
    public UnitPrice getUnitPrice() {
        return unitPrice;
    }

    /**
     * Beállítja az árukészlethez kapcsolódó egységárat reprezentáló osztályt.
     *
     * @param unitPrice egységárat reprezentáló osztály
     *                  az árukészlethez kapcsolódó egységárat reprezentáló osztályt
     */
    public void setUnitPrice(UnitPrice unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Visszaadja az árukészletet mennyisét.
     *
     * @return mennyiség
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Beállítja az árukészletet mennyisét.
     *
     * @param quantity mennyiség
     *                 az árukészletet mennyisét
     *
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
