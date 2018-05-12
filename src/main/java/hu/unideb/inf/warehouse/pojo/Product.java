package hu.unideb.inf.warehouse.pojo;

import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Egy árut reprezentáló osztály.
 *
 */
@Entity
public class Product {
    /**
     * Az áru egyedi azonosítója. Autómatikusan generált.
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * Az áru betöltésének ideje.
     */
    private LocalDateTime recorded;
    /**
     * Az áru státusza.
     */
    private boolean status;
    /**
     * Az áru megnevezése. Egyedi azonosító.
     */
    @Column(unique=true)
    private String label;
    /**
     * Az áru mértékegysége.
     */
    private String unitLabel;

    /**
     * Konstruktor, mely létrehoz egy árut reprezentáló objektumot.
     */
    public Product() {
        super();
    }

    /**
     * Konstruktor, mely létrehoz egy árut reprezentáló objektumot.
     *
     * @param label  megnevezés
     *               az áru megnevezése
     * @param unitLabel mértékegység
     * 	 *            sz áru megnevezése
     */
    public Product(String label, String unitLabel) {
        this.status = true;
        this.recorded = LocalDateTime.now();
        this.label = label;
        this.unitLabel = unitLabel;
    }

    /**
     * Visszaadja a paraméterként kapott id-hez tartozó árut reprezentáló objektumot.
     * @param id beszerző id
     * @return árut reprezentáló objektum
     */
    public Product findProduct(Long id) {
        EntityManager entityManager  = new EntityManagerFactoryUtil().getEntityManager();
        return entityManager.find(Product.class, id);
    }

    /**
     * Visszaadja az áru egyedi azonosítója.
     *
     * @return egyedi azonosító
     */
    public Long getId() {
        return id;
    }

    /***
     * Visszaadja az áru rögzítési dátumát.
     *
     * @return rögzítési dátum
     */
    public LocalDateTime getRecorded() {
        return recorded;
    }

    /**
     * Visszaadja az áru státuszát.
     * Ha igaz, az árú aktív állapotú, raktárban/forgalomban van.
     *
     * @return státusz
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Beállítja az áru státuszát.
     * Hamis értéket csak akkor vehet fel, ha nincs aktuális raktárkészleten.
     *
     * @param status státusz
     *               az áru státusza
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Visszaadja az áru nevét.
     *
     * @return név
     */
    public String getLabel() {
        return label;
    }

    /**
     * Beállítja az áru nevét.
     *
     * @param label név
     *              az áru neve
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Visszaadja az áru mértékegységének megnevezését.
     *
     * @return mértékegység megnevezés
     */
    public String getUnitLabel() {
        return unitLabel;
    }

    /**
     * Beállítja az áru mértékegységének megnevezését.
     *
     * @param unitLabel mértékegység megnevezés
     *                  az áru mértékegység megnevezése
     */
    public void setUnitLabel(String unitLabel) {
        this.unitLabel = unitLabel;
    }
}
