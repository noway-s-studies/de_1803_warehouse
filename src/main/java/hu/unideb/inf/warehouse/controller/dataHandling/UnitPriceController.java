package hu.unideb.inf.warehouse.controller.dataHandling;

import hu.unideb.inf.warehouse.model.ProductModel;
import hu.unideb.inf.warehouse.model.PurveyorModel;
import hu.unideb.inf.warehouse.model.UnitPriceModel;
import hu.unideb.inf.warehouse.pojo.Product;
import hu.unideb.inf.warehouse.pojo.Purveyor;
import hu.unideb.inf.warehouse.pojo.UnitPrice;
import hu.unideb.inf.warehouse.utility.TextListenerUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class UnitPriceController implements Initializable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private UnitPriceModel pm;
    private ObservableList<UnitPrice> data = FXCollections.observableArrayList();
    private UnitPrice selectedUnitPrice = null;
    private Purveyor purveyor;
    private Product product;
    private PurveyorModel purveyorModel;
    private ProductModel productModel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pm = new UnitPriceModel();
        table.getColumns().removeAll();
        updateTableData();
        new TextListenerUtil().numberMaxMinTextFieldListener(inputPrice, 0,999999999);
        table.setOnMouseClicked((MouseEvent event) -> {
            editedRow();
        });
    }

    @FXML
    TableView table;
    @FXML
    TextField inputPurveyor;
    @FXML
    TextField inputProduct;
    @FXML
    TextField inputPrice;
    @FXML
    Button cleanUnitPriceTextFieldButton;
    @FXML
    Button addUnitPriceButton;
    @FXML
    Button delUnitPriceButton;
    @FXML
    Button modUnitPriceButton;
    @FXML
    private TableColumn<UnitPrice, String> purveyorColumn = null;
    @FXML
    private TableColumn<UnitPrice, String> productColumn = null;
    @FXML
    private TableColumn<UnitPrice, Number> priceColumn = null;

    @FXML
    public void actionCleanUnitPriceTextField(MouseEvent event){
        clearInputBox();
        cleanUnitPriceTextFieldButton.setVisible(true);
        addUnitPriceButton.setVisible(true);
        delUnitPriceButton.setVisible(false);
        modUnitPriceButton.setVisible(false);
        selectedUnitPrice = null;
    }

    private void clearInputBox() {
        inputPurveyor.clear();
        inputPrice.clear();
        inputProduct.clear();
    }

    @FXML
    public void actionDelUnitPriceContact(MouseEvent event){
        if (selectedUnitPrice != null){
            pm.removeUnitPrice(selectedUnitPrice);
            actionCleanUnitPriceTextField(event);
            updateTableData();
        }
    }

    @FXML
    public void actionModUnitPriceContact(MouseEvent event){
        purveyor = new Purveyor();
        product = new Product();
        if (selectedUnitPrice != null){
            selectedUnitPrice.setPurveyor(purveyor.findPurveyor(Long.valueOf(inputPurveyor.getText())));
            selectedUnitPrice.setProduct(product.findProduct(Long.valueOf(inputProduct.getText())));
            selectedUnitPrice.setPrice(Integer.parseInt(inputPrice.getText().trim()));

            pm.modUnitPrice(selectedUnitPrice);
            actionCleanUnitPriceTextField(event);
            updateTableData();
        }
    }

    @FXML
    public void actionAddUnitPriceContact(MouseEvent event){
        purveyor = new Purveyor();
        product = new Product();
        if (inputPurveyor != null && inputProduct != null && inputPrice != null){
            UnitPrice newPureyor = new UnitPrice(
                    purveyor.findPurveyor(Long.valueOf(inputPurveyor.getText())),
                    product.findProduct(Long.valueOf(inputProduct.getText())),
                    Integer.parseInt(inputPrice.getText().trim())
            );
            data.add(newPureyor);
            pm.addUnitPrice(newPureyor);
            clearInputBox();
            log.info("Új egységár betőltve");
        }
    }

    private void updateTableData() {

        purveyor = new Purveyor();
        product = new Product();
        purveyorModel = new PurveyorModel();
        productModel = new ProductModel();

        table.getItems().clear();
        table.getColumns().clear();

        purveyorColumn = new TableColumn("Beszerző");
        purveyorColumn.setMinWidth(200);
        productColumn = new TableColumn("Áru");
        productColumn.setMinWidth(100);
        priceColumn = new TableColumn("Ár");
        priceColumn.setMinWidth(100);

        purveyorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        purveyorColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UnitPrice, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<UnitPrice, String> param) {
//                return new SimpleStringProperty(String.valueOf(param.getValue().getPurveyor().getId()));
//            }
//        });

        productColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        productColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UnitPrice, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<UnitPrice, String> param) {
//                return new SimpleStringProperty(param.getValue().getProduct().getId().toString());
//            }
//        });

        priceColumn.setCellFactory(TextFieldTableCell.<UnitPrice, Number>forTableColumn(new NumberStringConverter()));
        priceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UnitPrice, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<UnitPrice, Number> param) {
                return new SimpleIntegerProperty(param.getValue().getPrice());
            }
        });
        table.getColumns().addAll(purveyorColumn, productColumn, priceColumn);
        data.addAll(pm.getUnitPrice());
        table.setItems(data);
    }

    public void editedRow() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            selectedUnitPrice = (UnitPrice) table.getSelectionModel().getSelectedItem();
            cleanUnitPriceTextFieldButton.setVisible(true);
            addUnitPriceButton.setVisible(false);
            delUnitPriceButton.setVisible(true);
            modUnitPriceButton.setVisible(true);
            inputPurveyor.setText(selectedUnitPrice.getPurveyor().getId().toString());
            inputProduct.setText(selectedUnitPrice.getProduct().getId().toString());
            inputPrice.setText(String.valueOf(selectedUnitPrice.getPrice()));
        }
    }
}