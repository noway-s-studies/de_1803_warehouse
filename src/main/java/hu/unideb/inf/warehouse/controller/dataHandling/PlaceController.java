package hu.unideb.inf.warehouse.controller.dataHandling;

import hu.unideb.inf.warehouse.model.PlaceModel;
import hu.unideb.inf.warehouse.pojo.Place;
import hu.unideb.inf.warehouse.utility.TextListenerUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A telephelyek megjelenítéséért felelős osztály.
 *
 */
public class PlaceController implements Initializable {

    /**
     * Itt kerül inicializálásra a telephelyek panel.
     * A megjelenő táblázat adatai trölődnek majd a lekérdezett adatokkal feltöltésre kerülnek.
     * Beállításra kerül a beviteli mező korlátozásainak figyelése.
     *
     * @param location inicializálás URL objektuma
     * @param resources inicializálás ResourceBundle objektuma
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pm = new PlaceModel();
        table.getColumns().removeAll();
        updateTableData();
        new TextListenerUtil().numberMaxMinTextFieldListener(inputWeighting, 0,100);
        table.setOnMouseClicked((MouseEvent event) -> {
            log.info("Táblázat sorának megjelölése.");
            editedRow();
        });
    }

    /**
     * Logger osztály egy példánya.
     */
    private static Logger log = LoggerFactory.getLogger(PlaceController.class);
    /**
     * PlaceModel osztály egy példánya.
     */
    private PlaceModel pm;
    /**
     * A Place osztály elemeinek listája.
     */
    private ObservableList<Place> data = FXCollections.observableArrayList();
    /**
     * A Place osztály táblázatban megjelölt eleme.
     */
    private Place selectedPlace = null;
    /**
     * TableView objektum egy példánya, táblázat az adatok megjelenítéséhez.
     */
    @FXML
    private TableView table;
    /**
     * TextField objektum egy példánya, beviteli mező a megnevezés módosításához.
     */
    @FXML
    private TextField inputLabel;
    /**
     * TextField objektum egy példánya, beviteli mező az elérhetőség módosításához.
     */
    @FXML
    private TextField inputAvailability;
    /**
     * TextField objektum egy példánya, beviteli mező a súlyozás módosításához.
     */
    @FXML
    private TextField inputWeighting;
    /**
     * Button objektum egy példánya, gomb elem a beviteli mezők adatainak törléséhez.
     */
    @FXML
    private Button cleanPlaceTextFieldButton;
    /**
     * Button objektum egy példánya, gomb elem új példány betöltéséhez.
     */
    @FXML
    private Button addPlaceButton;
    /**
     * Button objektum egy példánya, gomb elem a kiválasztott példány törléséhez.
     */
    @FXML
    private Button delPlaceButton;
    /**
     * Button objektum egy példánya, gomb elem a kiválasztott példány adatainak módosításához.
     */
    @FXML
    private Button modPlaceButton;
    /**
     * TableColumn objektum egy példánya, táblázat oszlop a megnevezés megjelenítéséhez.
     */
    @FXML
    private TableColumn<Place, String> labelColumn = null;
    /**
     * TableColumn objektum egy példánya, táblázat oszlop az elérhetőség megjelenítéséhez.
     */
    @FXML
    private TableColumn<Place, String> addressColumn = null;
    /**
     * TableColumn objektum egy példánya, táblázat oszlop a súlyozás megjelenítéséhez.
     */
    @FXML
    private TableColumn<Place, Number> weightingColumn = null;

    /**
     * Egérkattintást kezelő metódus, használatával a beviteli mezők értékei
     * törlődnek és a gombok láthatósága alapállapotba kerül.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void actionCleanPlaceTextField(MouseEvent event){
        log.info("Gomb használva: Takarít.");
        clearInputBox();
        cleanPlaceTextFieldButton.setVisible(true);
        addPlaceButton.setVisible(true);
        delPlaceButton.setVisible(false);
        modPlaceButton.setVisible(false);
        selectedPlace = null;
    }

    /**
     * Beviteli mezők értékeinek törlését végző metódus.
     */
    private void clearInputBox() {
        inputLabel.clear();
        inputWeighting.clear();
        inputAvailability.clear();
        log.info("Beviteli mezők törölve.");
    }

    /**
     * Egérkattintást kezelő metódus, használatával törlésre kerül
     * a kiválasztott telephely objektum és frissül a táblázat.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void actionDelPlaceContact(MouseEvent event){
        log.info("Gomb használva: Töröl.");
        if (selectedPlace != null){
            pm.removePlace(selectedPlace);
            actionCleanPlaceTextField(event);
            updateTableData();
            log.info("Telephely törölve.");
        }
    }

    /**
     * Egérkattintást kezelő metódus, használatával törlésre kerül
     * a kiválasztott telephely objektum.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void actionModPlaceContact(MouseEvent event){
        log.info("Gomb használva: Módosítás.");
        if (selectedPlace != null){
            selectedPlace.setLabel(inputLabel.getText());
            selectedPlace.setAvailability(inputAvailability.getText());
            selectedPlace.setWeighting(Integer.parseInt(inputWeighting.getText().trim()));
            pm.modPlace(selectedPlace);
            actionCleanPlaceTextField(event);
            updateTableData();
            log.info("Telephely adatai módosítva.");
        }
    }

    /**
     * Egérkattintást kezelő metódus, használatával tárolásra kerül
     * a mezőkben rögzített adatokból létrehozott telephely objektum.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void actionAddPlaceContact(MouseEvent event){
        log.info("Gomb használva: Hozzáadás.");
        if (inputLabel != null && inputAvailability != null && inputWeighting != null){
            Place newPureyor = new Place(
                    inputLabel.getText(),
                    inputAvailability.getText(),
                    Integer.parseInt(inputWeighting.getText().trim())
            );
            data.add(newPureyor);
            pm.addPlace(newPureyor);
            clearInputBox();
            log.info("Telephely betőltve.");
        }
    }

    /**
     * Táblázat adatait frissítő metódus.
     */
    private void updateTableData() {
        table.getItems().clear();
        table.getColumns().clear();
        labelColumn = new TableColumn("Megnevezés");
        labelColumn.setMinWidth(200);
        addressColumn = new TableColumn("Elérhetőség");
        addressColumn.setMinWidth(100);
        weightingColumn = new TableColumn("Súlyozás");
        weightingColumn.setMinWidth(100);
        labelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        labelColumn.setCellValueFactory(new PropertyValueFactory<Place, String>("label"));
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setCellValueFactory(new PropertyValueFactory<Place, String>("availability"));
        weightingColumn.setCellFactory(TextFieldTableCell.<Place, Number>forTableColumn(new NumberStringConverter()));
        weightingColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Place, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Place, Number> param) {
                return new SimpleIntegerProperty(param.getValue().getWeighting());
            }
        });
        table.getColumns().addAll(labelColumn, addressColumn, weightingColumn);
        data.addAll(pm.getPlace());
        table.setItems(data);
        log.info("Táblázat adatai frissítésre kerültek.");
    }

    /**
     * Táblázat sorának kiválasztását kezelő metódis.
     * A kiválasztott sor adatait beviteli mezőbe másolja és szerkesztési gombokra vált.
     */
    public void editedRow() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            selectedPlace = (Place) table.getSelectionModel().getSelectedItem();
            cleanPlaceTextFieldButton.setVisible(true);
            addPlaceButton.setVisible(false);
            delPlaceButton.setVisible(true);
            modPlaceButton.setVisible(true);
            inputLabel.setText(selectedPlace.getLabel());
            inputAvailability.setText(selectedPlace.getAvailability());
            inputWeighting.setText(String.valueOf(selectedPlace.getWeighting()));
            log.info("Táblázat adatai beviteli mezőkbe másolódtak.");
        }
    }
}