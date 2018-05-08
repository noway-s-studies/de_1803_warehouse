package hu.unideb.inf.warehouse.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * Egy árut reprezentáló osztály.
 *
 */
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private Date recorded;
    private boolean status;
    @Column(unique=true)
    private String label;
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
        this.recorded = new Date();
        this.label = label;
        this.unitLabel = unitLabel;
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
    public Date getRecorded() {
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
