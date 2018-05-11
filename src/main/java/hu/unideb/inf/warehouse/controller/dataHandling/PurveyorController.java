package hu.unideb.inf.warehouse.controller.dataHandling;

import hu.unideb.inf.warehouse.model.PurveyorModel;
import hu.unideb.inf.warehouse.pojo.Purveyor;
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
 * A beszerzők megjelenítéséért felelős osztály.
 *
 */
public class PurveyorController implements Initializable {

    /**
     * Itt kerül inicializálásra a beszerzők panel.
     * A megjelenő táblázat adatai trölődnek majd a lekérdezett adatokkal feltöltésre kerülnek.
     * Beállításra kerül a beviteli mező korlátozásainak figyelése.
     *
     * @param location inicializálás URL objektuma
     * @param resources inicializálás ResourceBundle objektuma
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pm = new PurveyorModel();
        table.getColumns().removeAll();
        updateTableData();
        new TextListenerUtil().numberMaxMinTextFieldListener(inputDiscount, 0,100);
        table.setOnMouseClicked((MouseEvent event) -> {
            log.info("Táblázat sorának megjelölése.");
            editedRow();
        });
    }

    private static Logger log = LoggerFactory.getLogger(PurveyorController.class);
    private PurveyorModel pm;
    private ObservableList<Purveyor> data = FXCollections.observableArrayList();
    private Purveyor selectedPurveyor = null;

    @FXML
    TableView table;
    @FXML
    TextField inputDescription;
    @FXML
    TextField inputAddress;
    @FXML
    TextField inputDiscount;
    @FXML
    Button cleanPurveyorTextFieldButton;
    @FXML
    Button addPurveyorButton;
    @FXML
    Button delPurveyorButton;
    @FXML
    Button modPurveyorButton;
    @FXML
    private TableColumn<Purveyor, String> label = null;
    @FXML
    private TableColumn<Purveyor, String> addressColumn = null;
    @FXML
    private TableColumn<Purveyor, Number> discountColumn = null;

    /**
     * Egérkattintást kezelő metódus, használatával a beviteli mezők értékei
     * törlődnek és a gombok láthatósága alapállapotba kerül.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void actionCleanPurveyorTextField(MouseEvent event){
        log.info("Gomb használva: Takarít.");
        clearInputBox();
        cleanPurveyorTextFieldButton.setVisible(true);
        addPurveyorButton.setVisible(true);
        delPurveyorButton.setVisible(false);
        modPurveyorButton.setVisible(false);
        selectedPurveyor = null;
    }

    /**
     * Beviteli mezők értékeinek törlését végző metódus.
     */
    private void clearInputBox() {
        inputDescription.clear();
        inputDiscount.clear();
        inputAddress.clear();
        log.info("Beviteli mezők törölve.");
    }

    /**
     * Egérkattintást kezelő metódus, használatával törlésre kerül
     * a kiválasztott beszerző objektum és frissül a táblázat.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void actionDelPurveyorContact(MouseEvent event){
        log.info("Gomb használva: Töröl.");
        if (selectedPurveyor != null){
            pm.removePurveyor(selectedPurveyor);
            actionCleanPurveyorTextField(event);
            updateTableData();
            log.info("Beszerző törölve.");
        }
    }


    /**
     * Egérkattintást kezelő metódus, használatával törlésre kerül
     * a kiválasztott beszerző objektum.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void actionModPurveyorContact(MouseEvent event){
        log.info("Gomb használva: Módosítás.");
        if (selectedPurveyor != null){
            selectedPurveyor.setLabel(inputDescription.getText());
            selectedPurveyor.setAvailability(inputAddress.getText());
            selectedPurveyor.setDiscount(Integer.parseInt(inputDiscount.getText().trim()));
            pm.modPurveyor(selectedPurveyor);
            actionCleanPurveyorTextField(event);
            updateTableData();
            log.info("Beszerző adatai módosítva.");
        }
    }

    /**
     * Egérkattintást kezelő metódus, használatával tárolásra kerül
     * a mezőkben rögzített adatokból létrehozott beszerző objektum.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void actionAddPurveyorContact(MouseEvent event){
        log.info("Gomb használva: Hozzáadás.");
        if (inputDescription != null && inputAddress != null && inputDiscount != null){
            Purveyor newPureyor = new Purveyor(
                    inputDescription.getText(),
                    inputAddress.getText(),
                    Integer.parseInt(inputDiscount.getText().trim())
            );
            data.add(newPureyor);
            pm.addPurveyor(newPureyor);
            clearInputBox();
            log.info("Beszerző betőltve.");
        }
    }

    /**
     * Táblázat adatait frissítő metódus.
     */
    private void updateTableData() {
        table.getItems().clear();
        table.getColumns().clear();
        label = new TableColumn("Megnevezés");
        label.setMinWidth(200);
        addressColumn = new TableColumn("Elérhetőség");
        addressColumn.setMinWidth(100);
        discountColumn = new TableColumn("Kedvezmény");
        discountColumn.setMinWidth(100);
        label.setCellFactory(TextFieldTableCell.forTableColumn());
        label.setCellValueFactory(new PropertyValueFactory<Purveyor, String>("label"));
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setCellValueFactory(new PropertyValueFactory<Purveyor, String>("availability"));
        discountColumn.setCellFactory(TextFieldTableCell.<Purveyor, Number>forTableColumn(new NumberStringConverter()));
        discountColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Purveyor, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Purveyor, Number> param) {
                return new SimpleIntegerProperty(param.getValue().getDiscount());
            }
        });
        table.getColumns().addAll(label, addressColumn, discountColumn);
        data.addAll(pm.getPurveyor());
        table.setItems(data);
        log.info("Táblázat adatai frissítésre kerültek.");
    }

    /**
     * Táblázat sorának kiválasztását kezelő metódis.
     * A kiválasztott sor adatait beviteli mezőbe másolja és szerkesztési gombokra vált.
     */
    public void editedRow() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            selectedPurveyor = (Purveyor) table.getSelectionModel().getSelectedItem();
            cleanPurveyorTextFieldButton.setVisible(true);
            addPurveyorButton.setVisible(false);
            delPurveyorButton.setVisible(true);
            modPurveyorButton.setVisible(true);
            inputDescription.setText(selectedPurveyor.getLabel());
            inputAddress.setText(selectedPurveyor.getAvailability());
            inputDiscount.setText(String.valueOf(selectedPurveyor.getDiscount()));
            log.info("Táblázat adatai beviteli mezőkbe másolódtak.");
        }
    }
}

