package hu.unideb.inf.warehouse.controller.dataHandling;

import hu.unideb.inf.warehouse.model.PurveyorModel;
import hu.unideb.inf.warehouse.pojo.Purveyor;
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

public class PurveyorController implements Initializable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private PurveyorModel pm;
    private ObservableList<Purveyor> data = FXCollections.observableArrayList();
    private Purveyor selectedPurveyor = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pm = new PurveyorModel();
        table.getColumns().removeAll();
        updateTableData();
        numberMaxMinTextFieldListener(inputDiscount, 0,100);
        table.setOnMouseClicked((MouseEvent event) -> {
            editedRow();
        });
    }

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

    @FXML
    public void actionCleanPurveyorTextField(MouseEvent event){
        inputDescription.clear();
        inputDiscount.clear();
        inputAddress.clear();
        cleanPurveyorTextFieldButton.setVisible(true);
        addPurveyorButton.setVisible(true);
        delPurveyorButton.setVisible(false);
        modPurveyorButton.setVisible(false);
        selectedPurveyor = null;
    }

    @FXML
    public void actionDelPurveyorContact(MouseEvent event){
        if (selectedPurveyor != null){
            pm.removePurveyor(selectedPurveyor);
            actionCleanPurveyorTextField(event);
            updateTableData();
        }
    }

    @FXML
    public void actionModPurveyorContact(MouseEvent event){
        if (selectedPurveyor != null){
            selectedPurveyor.setLabel(inputDescription.getText());
            selectedPurveyor.setAvailability(inputAddress.getText());
            selectedPurveyor.setDiscount(Integer.parseInt(inputDiscount.getText().trim()));

            pm.modPurveyor(selectedPurveyor);
            actionCleanPurveyorTextField(event);
            updateTableData();
        }
    }

    @FXML
    public void actionAddPurveyorContact(MouseEvent event){
        if (inputDescription != null && inputAddress != null && inputDiscount != null){
            Purveyor newPureyor = new Purveyor(
                    inputDescription.getText(),
                    inputAddress.getText(),
                    Integer.parseInt(inputDiscount.getText().trim())
            );
            data.add(newPureyor);
            pm.addPurveyor(newPureyor);
            inputDescription.clear();
            inputDiscount.clear();
            inputAddress.clear();
            log.info("Új beszerzőt betőltve");
        }
    }

    private void numberMaxMinTextFieldListener(TextField tf, int min, int max) {
        tf.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (change.isDeleted()) {
                return change;
            }
            String txt = change.getControlNewText();
            if (txt.matches("0\\d+")) {
                return null;
            }
            try {
                int n = Integer.parseInt(txt);
                return min <= n && n <= max ? change : null;
            } catch (NumberFormatException e) {
                return null;
            }
        }));
    }

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
    }

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
        }
    }
}

