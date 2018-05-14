package hu.unideb.inf.warehouse.controller;

import hu.unideb.inf.warehouse.model.HomeModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Kezdőlap kezelő osztály.
 */
public class HomeController implements Initializable {
    /**
     * Logger osztály egy példánya.
     */
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * HomeModel osztály egy példánya.
     */
    private HomeModel hm;

    /**
     * A raktárkészlet értékét megjelenítő label elem.
     */
    @FXML
    private Label sumValue;

    /**
     * A raktárkészlet értékének beállítását végzi.
     */
    public void setSumValue() {
        this.sumValue.setText(hm.getSumValue() + " Ft.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hm = new HomeModel();
        setSumValue();
    }
}
