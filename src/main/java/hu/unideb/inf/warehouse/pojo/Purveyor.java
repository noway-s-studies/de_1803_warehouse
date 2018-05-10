package hu.unideb.inf.warehouse.pojo;

import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;

import javax.persistence.*;
import java.util.Date;

/**
 * Egy beszerzőt reprezentáló osztály.
 *
 */
@Entity
public class Purveyor {

    @Id
    @GeneratedValue
    private Long id;
    private Date recorded;
    private boolean status;
    @Column(unique=true)
    private String label;
    private String availability;
    private int discount;

    /**
     * Konstruktor, mely létrehoz egy beszerzőt reprezentáló objektumot.
     */
    public Purveyor() {
        super();
    }

    /**
     * Konstruktor, mely létrehoz egy beszerzőt reprezentáló objektumot.
     *
     * @param label megnevezés
     *              a beszerző megnevezése
     * @param availability elérhetőség
     *                a beszerző elérhetőség
     * @param discount kedvezmény
     *                 a beszerző kedvezménye
     */
    public Purveyor(String label, String availability, int discount) {
        this.status = true;
        this.recorded = new Date();
        this.label = label;
        this.availability = availability;
        this.discount = discount;
    }

    /**
     * Visszaadja a paraméterként kapott id-hez tartozó beszerzőt reprezentáló objektumot.
     * @param id beszerző id
     * @return beszerzőt reprezentáló objektum
     */
    public Purveyor findPurveyor(Long id) {
        EntityManager entityManager  = new EntityManagerFactoryUtil().getEntityManager();
        return entityManager.find(Purveyor.class, id);
    }

    /**
     * Visszaadja a beszerző egyedi azonosítója.
     *
     * @return egyedi azonosító
     */
    public Long getId() {
        return id;
    }

    /**
     * Visszaadja a beszerző rögzítési dátumát.
     *
     * @return rögzítési dátum
     */
    public Date getRecorded() {
        return recorded;
    }

    /**
     * Visszaadja a beszerző státuszát.
     * Ha igaz, a beszerző aktív állapotú, rendelhető tőle árú
     * és van olyan árukészleten lévő áru mely a beszerzőtől származik.
     *
     * @return státusz
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Beállítja a beszerző státuszát.
     * Hamis értéket csak akkor vehet fel, ha a nincs olyan árukészleten
     * lévő áru mely a beszerzőtől származik.
     *
     * @param status státusz
     *               a beszerző státusza
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Visszaadja a beszerző nevét.
     *
     * @return név
     */
    public String getLabel() {
        return label;
    }

    /**
     * Beállítja a beszerző nevét.
     *
     * @param label név
     *              a beszerző neve
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Visszaadja a beszerző elérhetőségét.
     *
     * @return elérhetőség
     */
    public String getAvailability() {
        return availability;
    }

    /**
     * Beállítja a beszerző elérhetőségét.
     *
     * @param availability elérhetőség
     *                     a beszerző elérhetősége (címe, telefonszáma, stb.)
     */
    public void setAvailability(String availability) {
        this.availability = availability;
    }

    /**
     * Visszaadja a beszerző kedvezményét.
     * A beszerz által nyújtott százalékos kedvezmény mértéke az össze áruja árából levonandó.
     *
     * @return kedvezmény
     */
    public int getDiscount() {
        return discount;
    }

    /**
     * Beállítja a beszerző kedvezményét.
     *
     * @param discount kedvezmény
     *                 a beszerző kedvezménye
     */
    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
