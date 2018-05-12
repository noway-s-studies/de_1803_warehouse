package hu.unideb.inf.warehouse.pojo;

import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Egy egységárat reprezentáló osztály.
 *
 */
@Entity
public class UnitPrice {

    /**
     * Az egységár egyedi azonosítója. Autómatikusan generált.
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * Az egységár betöltésének ideje.
     */
    private LocalDateTime recorded;
    /**
     * Az egységár státusza.
     */
    private boolean status;
    /**
     * Az egységárhoz kapcsolódó beszerző objektum.
     */
    @ManyToOne
    private Purveyor purveyor;
    /**
     * Az egységárhoz kapcsolódó áru objektum.
     */
    @ManyToOne
    private Product product;
    /**
     * Az egységár mennyisége.
     */
    private int price;

    /**
     * Konstruktor, mely létrehoz egy egységárat reprezentáló objektumot.
     */
    public UnitPrice() {
    }

    /**
     * Konstruktor, mely létrehoz egy egységárat reprezentáló objektumot.
     *
     * @param purveyor beszerzőt reprezentáló osztály
     * @param product árut reprezentáló osztály
     * @param price egységár
     */
    public UnitPrice(Purveyor purveyor, Product product, int price) {
        this.status = true;
        this.recorded = LocalDateTime.now();
        this.purveyor = purveyor;
        this.product = product;
        this.price = price;
    }

    /**
     * Visszaadja a paraméterként kapott id-hez tartozó egységárat reprezentáló objektumot.
     * @param id egységár id
     * @return egységárat reprezentáló objektum
     */
    public UnitPrice findUnitPrice(Long id) {
        EntityManager entityManager  = new EntityManagerFactoryUtil().getEntityManager();
        return entityManager.find(UnitPrice.class, id);
    }

    /**
     * Visszaadja az egységár egyedi azonosítója.
     *
     * @return egyedi azonosító
     */
    public Long getId() {
        return id;
    }

    /**
     * Visszaadja az egységár rögzítési dátumát.
     *
     * @return rögzítési dátum
     */
    public LocalDateTime getRecorded() {
        return recorded;
    }

    /**
     * Visszaadja az egységár státuszát.
     * Ha igaz, az egységár aktív állapotú, adott beszerző adott árúja ezen az áron szerezhető be.
     *
     * @return státusz
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Beállítja az egységár státuszát.
     * Két igaz érték azonos beszerzővel és áróval nem szerepelhet.
     *
     * @param status státusz
     *               az egységár státusza
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Visszaadja az egységárhoz kapcsolódó beszerzőt reprezentáló osztályt.
     *
     * @return beszerzőt reprezentáló osztály
     */
    public Purveyor getPurveyor() {
        return purveyor;
    }

    /**
     * Beállítja az egységárhoz kapcsolódó beszerzőt reprezentáló osztályt.
     *
     * @param purveyor beszerzőt reprezentáló osztály
     *                 az egységárhoz kapcsolódó beszerzőt reprezentáló osztály
     */
    public void setPurveyor(Purveyor purveyor) {
        this.purveyor = purveyor;
    }

    /**
     * Visszaadja az egységárhoz kapcsolódó árut reprezentáló osztályt.
     *
     * @return árut reprezentáló osztály
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Beállítja az egységárhoz kapcsolódó árut reprezentáló osztályt.
     *
     * @param product árut reprezentáló osztály
     *                az egységárhoz kapcsolódó árut reprezentáló osztály
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Visszaadja az egységárat.
     *
     * @return egységár
     */
    public int getPrice() {
        return price;
    }

    /**
     * Beállítja az egységárat.
     *
     * @param price egységár
     *              az egységár értéke
     */
    public void setPrice(int price) {
        this.price = price;
    }
}
