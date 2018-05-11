package hu.unideb.inf.warehouse.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Az alkalmazás felületéért felelős osztály.
 *
 */
public class MasterController implements Initializable {
    /**
     * Logger osztály egy példánya.
     */
    private static Logger logger = LoggerFactory.getLogger(MasterController.class);
    /**
     * Alapértelmezett főmenü gomb stílus.
     */
    private final String defaultButtonStyle = "-fx-background-color: transparent; -fx-text-fill: #F0F0F0;";
    /**
     * Aktív főmenü gomb stílus.
     */
    private final String activeButtonStyle = "-fx-background-color: #404040; -fx-text-fill: #F0F0F0;";
    /**
     * A BorderPane osztály egy példánya.
     */
    @FXML
    private BorderPane borderPane;
    /**
     * A Button osztály egy példánya. Nyitólap gomb.
     */
    @FXML
    private Button homeButton;
    /**
     * A Button osztály egy példánya. Adatkezelés gomb.
     */
    @FXML
    private Button dataHandlingViewButton;
    /**
     * A Button osztály egy példánya. Inport gomb.
     */
    @FXML
    private Button importButton;
    /**
     * A Button osztály egy példánya. Export gomb.
     */
    @FXML
    private Button exportButton;

    /**
     * Alapértelmezett gombstílus beállítása.
     */
    private void defaultButtonStyle(){
        homeButton.setStyle(defaultButtonStyle);
        dataHandlingViewButton.setStyle(defaultButtonStyle);
        importButton.setStyle(defaultButtonStyle);
        exportButton.setStyle(defaultButtonStyle);
    }

    /**
     * Egérkattintást kezelő metódus, használatával bezárásra kerül az alkalmazás.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void close (MouseEvent event) {
        logger.info("Program bezárásának megkezdése.");
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

    /**
     * Egérkattintást kezelő metódus, használatával megjelenik a kezdőlapi panel.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    void homeView(MouseEvent event) {
        defaultButtonStyle();
        homeButton.setStyle(activeButtonStyle);
        loadCenter("HomeView");
    }

    /**
     * Egérkattintást kezelő metódus, használatával megjelenik a betöltéseket kezelő panel.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    void importView(MouseEvent event) {
        defaultButtonStyle();
        importButton.setStyle(activeButtonStyle);
        loadCenter("ImportView");
    }

    /**
     * Egérkattintást kezelő metódus, használatával megjelenik a exportálásokat kezelő panel.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    void exportView(MouseEvent event) {
        defaultButtonStyle();
        exportButton.setStyle(activeButtonStyle);
        loadCenter("ExportView");
    }

    /**
     * Egérkattintást kezelő metódus, használatával megjelenik a adatkezelési panel.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    void DataHandlingView(MouseEvent event) {
        defaultButtonStyle();
        dataHandlingViewButton.setStyle(activeButtonStyle);
        TabPane tp = new TabPane();
        try {
            tp.getTabs().add(FXMLLoader.load(getClass().getResource("/view/dataHandling/StockView.fxml")));
            tp.getTabs().add(FXMLLoader.load(getClass().getResource("/view/dataHandling/PlaceView.fxml")));
            tp.getTabs().add(FXMLLoader.load(getClass().getResource("/view/dataHandling/ProductView.fxml")));
            tp.getTabs().add(FXMLLoader.load(getClass().getResource("/view/dataHandling/PurveyorView.fxml")));
            tp.getTabs().add(FXMLLoader.load(getClass().getResource("/view/dataHandling/UnitPriceView.fxml")));
        } catch (IOException ex) {
            logger.error("Oldal megjelenítési hiba.");
        }
        logger.info("Panel betöltve.");
        borderPane.setCenter(tp);
    }

    /**
     * A paraméterként kapott objektum főpanelre történő betöltését végzi.
     *
     * @param ui panel megnevezése
     */
    private void loadCenter(String ui){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/" +ui+".fxml"));
        } catch (Exception e) {
            logger.error("Panel betöltési hiba!");
        }
        logger.info("Panel betöltve.");
        borderPane.setCenter(root);
    }

    /**
     * Itt kerül inicializálásra a főprogram.
     * Továbbá beállítódik a kezdeti (alapértelmezett) panel.
     *
     * @param location inicializálás URL objektuma
     * @param resources inicializálás ResourceBundle objektuma
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCenter("HomeView");
        logger.info("Panel betöltve.");
    }
}