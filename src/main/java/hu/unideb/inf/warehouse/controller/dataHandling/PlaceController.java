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

public class PlaceController implements Initializable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private PlaceModel pm;
    private ObservableList<Place> data = FXCollections.observableArrayList();
    private Place selectedPlace = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pm = new PlaceModel();
        table.getColumns().removeAll();
        updateTableData();
        new TextListenerUtil().numberMaxMinTextFieldListener(inputWeighting, 0,100);
        table.setOnMouseClicked((MouseEvent event) -> {
            editedRow();
        });
    }

    @FXML
    TableView table;
    @FXML
    TextField inputLabel;
    @FXML
    TextField inputAvailability;
    @FXML
    TextField inputWeighting;
    @FXML
    Button cleanPlaceTextFieldButton;
    @FXML
    Button addPlaceButton;
    @FXML
    Button delPlaceButton;
    @FXML
    Button modPlaceButton;
    @FXML
    private TableColumn<Place, String> labelColumn = null;
    @FXML
    private TableColumn<Place, String> addressColumn = null;
    @FXML
    private TableColumn<Place, Number> weightingColumn = null;

    @FXML
    public void actionCleanPlaceTextField(MouseEvent event){
        clearInputBox();
        cleanPlaceTextFieldButton.setVisible(true);
        addPlaceButton.setVisible(true);
        delPlaceButton.setVisible(false);
        modPlaceButton.setVisible(false);
        selectedPlace = null;
    }

    private void clearInputBox() {
        inputLabel.clear();
        inputWeighting.clear();
        inputAvailability.clear();
    }

    @FXML
    public void actionDelPlaceContact(MouseEvent event){
        if (selectedPlace != null){
            pm.removePlace(selectedPlace);
            actionCleanPlaceTextField(event);
            updateTableData();
        }
    }

    @FXML
    public void actionModPlaceContact(MouseEvent event){
        if (selectedPlace != null){
            selectedPlace.setLabel(inputLabel.getText());
            selectedPlace.setAvailability(inputAvailability.getText());
            selectedPlace.setWeighting(Integer.parseInt(inputWeighting.getText().trim()));

            pm.modPlace(selectedPlace);
            actionCleanPlaceTextField(event);
            updateTableData();
        }
    }

    @FXML
    public void actionAddPlaceContact(MouseEvent event){
        if (inputLabel != null && inputAvailability != null && inputWeighting != null){
            Place newPureyor = new Place(
                    inputLabel.getText(),
                    inputAvailability.getText(),
                    Integer.parseInt(inputWeighting.getText().trim())
            );
            data.add(newPureyor);
            pm.addPlace(newPureyor);
            clearInputBox();
            log.info("Új beszerzőt betőltve");
        }
    }

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
    }

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
        }
    }
}