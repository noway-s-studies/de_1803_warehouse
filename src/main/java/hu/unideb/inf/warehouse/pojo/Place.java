package hu.unideb.inf.warehouse.pojo;

import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Egy telephelyet reprezentáló osztály.
 *
 */
@Entity
public class Place {
    /**
     * Telephely egyedi azonosítója. Autómatikusan generált.
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * Telephely betöltésének ideje.
     */
    private LocalDateTime recorded;
    /**
     * Telephely státusza.
     */
    private boolean status;
    /**
     * Telephely megnevezése. Egyedi azonosító.
     */
    @Column(unique=true)
    private String label;
    /**
     * Telephely elérhetősége.
     */
    private String availability;
    /**
     * Telephely súlyozása.
     */
    private int weighting;
    /**
     * Konstruktor, mely létrehoz egy telephelyet reprezentáló objektumot.
     */
    public Place() {
        super();
    }

    /**
     * Konstruktor, mely létrehoz egy telephelyet reprezentáló objektumot.
     *
     * @param label megnevezés
     *              a telephely megnevezése
     * @param availability elérhetőség
     *                     a telephely elérhetőség
     * @param weighting súlyozás
     *                  a telephely súlyozás
     */
    public Place(String label, String availability, int weighting) {
        this.status = true;
        this.recorded = LocalDateTime.now();
        this.label = label;
        this.availability = availability;
        this.weighting = weighting;
    }

    /**
     * Visszaadja a paraméterként kapott id-hez tartozó telephelyet reprezentáló objektumot.
     * @param id telephely id
     * @return telephelyet reprezentáló objektum
     */
    public Place findPlace(Long id) {
        EntityManager entityManager  = new EntityManagerFactoryUtil().getEntityManager();
        return entityManager.find(Place.class, id);
    }

    /**
     * Visszaadja a telephely egyedi azonosítója.
     *
     * @return egyedi azonosító
     */
    public Long getId() {
        return id;
    }

    /**
     * Visszaadja a telephely rögzítési dátumát.
     *
     * @return rögzítési dátum
     */
    public LocalDateTime getRecorded() {
        return recorded;
    }

    /**
     * Visszaadja a telephely státuszát.
     * Ha <code>igaz</code>, a telephely aktív állapotú, rendelkezik raktárral.
     *
     * @return státusz
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Beállítja a telephely státuszát.
     * Hamis értéket csak akkor vehet fel, ha a telephely nem rendelkezik raktárkészleten lévő áruval.
     *
     * @param status státusz
     *               a telephely státusza
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Visszaadja a telephely nevét.
     *
     * @return név
     */
    public String getLabel() {
        return label;
    }

    /**
     * Beállítja a telephely nevét.
     *
     * @param label név
     *              a telephely neve
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Visszaadja a telephely elérhetőségét.
     *
     * @return elérhetőség
     */
    public String getAvailability() {
        return availability;
    }

    /**
     * Beállítja a telephely elérhetőségét.
     *
     * @param availability elérhetőség
     *                     a telephely elérhetősége (címe, telefonszáma, stb.)
     */
    public void setAvailability(String availability) {
        this.availability = availability;
    }

    /**
     * Visszaadja a telephely súlyozását.
     * Alacsonyabb érték esetén a telephely átkérhet raktárkészleten lévő árut más telephelyről.
     *
     * @return súlyozás
     */
    public int getWeighting() {
        return weighting;
    }

    /**
     * Beállítja a telephely súlyozását.
     *
     * @param weighting súlyozás
     *                  a telephely súlyozás
     */
    public void setWeighting(int weighting) {
        this.weighting = weighting;
    }
}
