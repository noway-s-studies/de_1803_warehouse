package hu.unideb.inf.warehouse.controller.dataHandling;

import hu.unideb.inf.warehouse.model.ProductModel;
import hu.unideb.inf.warehouse.model.PurveyorModel;
import hu.unideb.inf.warehouse.model.StockModel;
import hu.unideb.inf.warehouse.pojo.*;
import hu.unideb.inf.warehouse.utility.TextListenerUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class StockController implements Initializable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private StockModel pm;
    private ObservableList<Stock> data = FXCollections.observableArrayList();
    private Stock selectedStock = null;
    private Purveyor purveyor;
    private Product product;
    private PurveyorModel purveyorModel;
    private ProductModel productModel;
    private Place place;
    private UnitPrice unitPrice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pm = new StockModel();
        table.getColumns().removeAll();
        updateTableData();
        new TextListenerUtil().numberMaxMinTextFieldListener(inputQuantity, 0,999999999);
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
    TextField inputPlace;
    @FXML
    TextField inputUnitPrice;
    @FXML
    TextField inputQuantity;
    @FXML
    Button cleanStockTextFieldButton;
    @FXML
    Button addStockButton;
    @FXML
    Button delStockButton;
    @FXML
    Button modStockButton;
    @FXML
    private TableColumn<Stock, String> purveyorColumn = null;
    @FXML
    private TableColumn<Stock, String> productColumn = null;
    @FXML
    private TableColumn<Stock, String> placeColumn = null;
    @FXML
    private TableColumn<Stock, String> unitPriceColumn = null;
    @FXML
    private TableColumn<Stock, Number> quantityColumn = null;

    @FXML
    public void actionCleanStockTextField(MouseEvent event){
        clearInputBox();
        cleanStockTextFieldButton.setVisible(true);
        addStockButton.setVisible(true);
        delStockButton.setVisible(false);
        modStockButton.setVisible(false);
        selectedStock = null;
    }

    private void clearInputBox() {
        inputPurveyor.clear();
        inputProduct.clear();
        inputPlace.clear();
        inputUnitPrice.clear();
        inputQuantity.clear();
    }

    @FXML
    public void actionDelStockContact(MouseEvent event){
        if (selectedStock != null){
            pm.removeStock(selectedStock);
            actionCleanStockTextField(event);
            updateTableData();
        }
    }

    @FXML
    public void actionModStockContact(MouseEvent event){
        purveyor = new Purveyor();
        product = new Product();
        if (selectedStock != null){
            selectedStock.setPurveyor(purveyor.findPurveyor(Long.valueOf(inputPurveyor.getText())));
            selectedStock.setProduct(product.findProduct(Long.valueOf(inputProduct.getText())));
            selectedStock.setQuantity(Integer.parseInt(inputQuantity.getText().trim()));

            pm.modStock(selectedStock);
            actionCleanStockTextField(event);
            updateTableData();
        }
    }

    @FXML
    public void actionAddStockContact(MouseEvent event){
        purveyor = new Purveyor();
        product = new Product();
        place = new Place();
        unitPrice = new UnitPrice();

        if (inputPurveyor != null && inputProduct != null && inputPlace != null && inputUnitPrice != null && inputQuantity != null){
            Stock newPureyor = new Stock(
                    purveyor.findPurveyor(Long.valueOf(inputPurveyor.getText())),
                    product.findProduct(Long.valueOf(inputProduct.getText())),
                    place.findPlace(Long.valueOf(inputPlace.getText())),
                    unitPrice.findUnitPrice(Long.valueOf(inputUnitPrice.getText())),
                    Integer.parseInt(inputQuantity.getText().trim())
            );
            data.add(newPureyor);
            pm.addStock(newPureyor);
            clearInputBox();
            log.info("Új árukészlet betőltve");
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
        placeColumn = new TableColumn("Telephely");
        placeColumn.setMinWidth(100);
        unitPriceColumn = new TableColumn("Egységár");
        unitPriceColumn.setMinWidth(100);
        quantityColumn = new TableColumn("Mennyiség");
        quantityColumn.setMinWidth(100);

        purveyorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        purveyorColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stock, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stock, String> param) {
//                return new SimpleStringProperty(String.valueOf(param.getValue().getPurveyor().getId()));
//            }
//        });

        productColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        productColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stock, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stock, String> param) {
//                return new SimpleStringProperty(param.getValue().getProduct().getId().toString());
//            }
//        });

        quantityColumn.setCellFactory(TextFieldTableCell.<Stock, Number>forTableColumn(new NumberStringConverter()));
        quantityColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stock, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Stock, Number> param) {
                return new SimpleIntegerProperty(param.getValue().getQuantity());
            }
        });
        table.getColumns().addAll(purveyorColumn, productColumn, placeColumn, unitPriceColumn, quantityColumn);
        data.addAll(pm.getStock());
        table.setItems(data);
    }

    public void editedRow() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            selectedStock = (Stock) table.getSelectionModel().getSelectedItem();
            cleanStockTextFieldButton.setVisible(true);
            addStockButton.setVisible(false);
            delStockButton.setVisible(true);
            modStockButton.setVisible(true);
            inputPurveyor.setText(selectedStock.getPurveyor().getId().toString());
            inputProduct.setText(selectedStock.getProduct().getId().toString());
            inputQuantity.setText(String.valueOf(selectedStock.getQuantity()));
        }
    }
}