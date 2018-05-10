package hu.unideb.inf.warehouse.controller.dataHandling;

import hu.unideb.inf.warehouse.model.ProductModel;
import hu.unideb.inf.warehouse.model.PurveyorModel;
import hu.unideb.inf.warehouse.model.UnitPriceModel;
import hu.unideb.inf.warehouse.pojo.Place;
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
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UnitPriceController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pm = new UnitPriceModel();
        table.getColumns().removeAll();
        updateTableData();
        new TextListenerUtil().numberMaxMinTextFieldListener(inputPrice, 0,999999999);
        table.setOnMouseClicked((MouseEvent event) -> {
            editedRow();
        });
        loadProduct();
        loadPurveyor();
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private UnitPriceModel pm;
    private ObservableList<UnitPrice> data = FXCollections.observableArrayList();
    private UnitPrice selectedUnitPrice = null;
    private Purveyor purveyor;
    private Product product;
    private PurveyorModel purveyorModel;
    private ProductModel productModel;

    @FXML
    private TableView table;

    private ObservableList<String> purveyorList = FXCollections.observableArrayList();
    @FXML
    private ComboBox comboBoxPurveyor;

    private ObservableList<String> productList = FXCollections.observableArrayList();
    @FXML
    private ComboBox comboBoxProduct;

    @FXML
    private TextField inputPrice;
    @FXML
    private Button cleanUnitPriceTextFieldButton;
    @FXML
    private Button addUnitPriceButton;
    @FXML
    private Button delUnitPriceButton;
    @FXML
    private Button modUnitPriceButton;
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
        inputPrice.clear();
        comboBoxPurveyor.setValue(null);
        comboBoxProduct.setValue(null);

    }

    private void loadPurveyor(){
        purveyorList.removeAll(purveyorList);
        List<String> actualPurveyorNameList = new PurveyorModel().getPurveyorName();
        purveyorList.addAll(actualPurveyorNameList);
        comboBoxPurveyor.getItems().addAll(purveyorList);

    }

    private void loadProduct(){
        productList.removeAll(productList);
        List<String> actualProductNameList = new ProductModel().getProductName();
        productList.addAll(actualProductNameList);
        comboBoxProduct.getItems().addAll(productList);

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

            long purveyorId = 0;
            List<Purveyor> pul = new PurveyorModel().getPurveyor();
            for (Purveyor list : pul) {
                if (list.getLabel().equals(comboBoxPurveyor.getValue())){
                    purveyorId = list.getId();
                }
            }
            long productId = 0;
            List<Product> prl = new ProductModel().getProduct();
            for (Product list : prl) {
                if (list.getLabel().equals(comboBoxProduct.getValue())){
                    productId = list.getId();
                }
            }
            selectedUnitPrice.setPurveyor(purveyor.findPurveyor(purveyorId));
            selectedUnitPrice.setProduct(product.findProduct(productId));
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
        if (comboBoxProduct != null && comboBoxPurveyor != null && inputPrice != null){
            long purveyorId = 0;
            List<Purveyor> pul = new PurveyorModel().getPurveyor();
            for (Purveyor list : pul) {
                if (list.getLabel().equals(comboBoxPurveyor.getValue())){
                    purveyorId = list.getId();
                }
            }
            long productId = 0;
            List<Product> prl = new ProductModel().getProduct();
            for (Product list : prl) {
                if (list.getLabel().equals(comboBoxProduct.getValue())){
                    productId = list.getId();
                }
            }

            UnitPrice newPureyor = new UnitPrice(
                    purveyor.findPurveyor(purveyorId),
                    product.findProduct(productId),
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
        purveyorColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UnitPrice, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<UnitPrice, String> param) {
                return new SimpleStringProperty(param.getValue().getPurveyor().getLabel());
            }
        });

        productColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        productColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UnitPrice, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<UnitPrice, String> param) {
                return new SimpleStringProperty(param.getValue().getProduct().getLabel());
            }
        });

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
            comboBoxPurveyor.setValue(selectedUnitPrice.getPurveyor().getLabel());
            comboBoxProduct.setValue(selectedUnitPrice.getProduct().getLabel());
            inputPrice.setText(String.valueOf(selectedUnitPrice.getPrice()));
        }
    }
}