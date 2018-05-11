package hu.unideb.inf.warehouse.controller.dataHandling;

import hu.unideb.inf.warehouse.model.*;
import hu.unideb.inf.warehouse.pojo.*;
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
import java.util.List;
import java.util.ResourceBundle;

/**
 * Az árukészlet megjelenítéséért felelős osztály.
 *
 */
public class StockController implements Initializable {

    /**
     * Itt kerül inicializálásra az árukészlet panel.
     * A megjelenő táblázat adatai trölődnek majd a lekérdezett adatokkal feltöltésre kerülnek.
     * Beállításra kerül a beviteli mező korlátozásainak figyelése.
     *
     * @param location inicializálás URL objektuma
     * @param resources inicializálás ResourceBundle objektuma
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pm = new StockModel();
        table.getColumns().removeAll();
        updateTableData();
        new TextListenerUtil().numberMaxMinTextFieldListener(inputQuantity, 0,999999999);
        table.setOnMouseClicked((MouseEvent event) -> {
            log.info("Táblázat sorának megjelölése.");
            editedRow();
        });
        loadProduct();
        loadPurveyor();
        loadPlace();
        loadUnitPrice();
    }
    /**
     * Logger osztály egy példánya.
     */
    private static Logger log = LoggerFactory.getLogger(StockController.class);
    /**
     * StockModel osztály egy példánya.
     */
    private StockModel pm;
    /**
     * A Stock osztály elemeinek listája.
     */
    private ObservableList<Stock> data = FXCollections.observableArrayList();
    /**
     * A Stock osztály táblázatban megjelölt eleme.
     */
    private Stock selectedStock = null;
    /**
     * A beszerző objektum egy példánya.
     */
    private Purveyor purveyor;
    /**
     * Az áru objektum egy példánya.
     */
    private Product product;
    /**
     * A telephely objektum egy példánya.
     */
    private Place place;
    /**
     * Az egységár objektum egy példánya.
     */
    private UnitPrice unitPrice;
    /**
     * A PurveyorModel objektum egy példánya.
     */
    private PurveyorModel purveyorModel;
    /**
     * A ProductModel objektum egy példánya.
     */
    private ProductModel productModel;
    /**
     * TableView objektum egy példánya, táblázat az adatok megjelenítéséhez.
     */
    @FXML
    private TableView table;
    /**
     * A beszerző osztály elemeinek listája.
     */
    private ObservableList<String> purveyorList = FXCollections.observableArrayList();
    /**
     * ComboBox objektum egy példánya, legördülő menü a beszerző módosításához.
     */
    @FXML
    private ComboBox comboBoxPurveyor;
    /**
     * Az áru osztály elemeinek listája.
     */
    private ObservableList<String> productList = FXCollections.observableArrayList();
    /**
     * ComboBox objektum egy példánya, legördülő menü az áru módosításához.
     */
    @FXML
    private ComboBox comboBoxProduct;
    /**
     * A telephely osztály elemeinek listája.
     */
    private ObservableList<String> placeList = FXCollections.observableArrayList();
    /**
     * ComboBox objektum egy példánya, legördülő menü a telephely módosításához.
     */
    @FXML
    private ComboBox comboBoxPlace;
    /**
     * Az egységár osztály elemeinek listája.
     */
    private ObservableList<String> unitPriceList = FXCollections.observableArrayList();
    /**
     * ComboBox objektum egy példánya, legördülő menü az egységár módosításához.
     */
    @FXML
    private ComboBox comboBoxUnitPrice;
    /**
     * TextField objektum egy példánya, beviteli mező a mennyiség módosításához.
     */
    @FXML
    private TextField inputQuantity;
    /**
     * Button objektum egy példánya, gomb elem a beviteli mezők adatainak törléséhez.
     */
    @FXML
    private Button cleanStockTextFieldButton;
    /**
     * Button objektum egy példánya, gomb elem új példány betöltéséhez.
     */
    @FXML
    private Button addStockButton;
    /**
     * Button objektum egy példánya, gomb elem a kiválasztott példány törléséhez.
     */
    @FXML
    private Button delStockButton;
    /**
     * Button objektum egy példánya, gomb elem a kiválasztott példány adatainak módosításához.
     */
    @FXML
    private Button modStockButton;
    /**
     * TableColumn objektum egy példánya, táblázat oszlop a beszerző megnevezésének megjelenítéséhez.
     */
    @FXML
    private TableColumn<Stock, String> purveyorColumn = null;
    /**
     * TableColumn objektum egy példánya, táblázat oszlop az áru megnevezésének megjelenítéséhez.
     */
    @FXML
    private TableColumn<Stock, String> productColumn = null;
    /**
     * TableColumn objektum egy példánya, táblázat oszlop a telephely megnevezésének megjelenítéséhez.
     */
    @FXML
    private TableColumn<Stock, String> placeColumn = null;
    /**
     * TableColumn objektum egy példánya, táblázat oszlop az egységár értékének megjelenítéséhez.
     */
    @FXML
    private TableColumn<Stock, Number> unitPriceColumn = null;
    /**
     * TableColumn objektum egy példánya, táblázat oszlop a mennyiség megjelenítéséhez.
     */
    @FXML
    private TableColumn<Stock, Number> quantityColumn = null;

    /**
     * Egérkattintást kezelő metódus, használatával a beviteli mezők értékei
     * törlődnek és a gombok láthatósága alapállapotba kerül.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void actionCleanStockTextField(MouseEvent event){
        log.info("Gomb használva: Takarít.");
        clearInputBox();
        cleanStockTextFieldButton.setVisible(true);
        addStockButton.setVisible(true);
        delStockButton.setVisible(false);
        modStockButton.setVisible(false);
        selectedStock = null;
    }

    /**
     * Beviteli mezők értékeinek törlését végző metódus.
     */
    private void clearInputBox() {
        comboBoxPurveyor.setValue(null);
        comboBoxProduct.setValue(null);
        comboBoxPlace.setValue(null);
        comboBoxUnitPrice.setValue(null);
        inputQuantity.clear();
        log.info("Beviteli mezők törölve.");
    }

    /**
     * Beviteli mező beállítása beszerzői adatok lekérdezésével.
     */
    private void loadPurveyor(){
        purveyorList.removeAll(purveyorList);
        List<String> actualPurveyorNameList = new PurveyorModel().getPurveyorName();
        purveyorList.addAll(actualPurveyorNameList);
        comboBoxPurveyor.getItems().addAll(purveyorList);
        log.info("Beviteli mezők frissítése beszerzői adatokkal.");
    }

    /**
     * Beviteli mező beállítása áru adatok lekérdezésével.
     */
    private void loadProduct(){
        productList.removeAll(productList);
        List<String> actualProductNameList = new ProductModel().getProductName();
        purveyorList.addAll(actualProductNameList);
        comboBoxProduct.getItems().addAll(purveyorList);
        log.info("Beviteli mezők frissítése áru adatokkal.");
    }

    /**
     * Beviteli mező beállítása telephely adatok lekérdezésével.
     */
    private void loadPlace(){
        placeList.removeAll(placeList);
        List<String> actualPlaceNameList = new PlaceModel().getPlaceName();
        placeList.addAll(actualPlaceNameList);
        comboBoxPlace.getItems().addAll(placeList);
        log.info("Beviteli mezők frissítése telephely adatokkal.");
    }

    /**
     * Beviteli mező beállítása egységár adatok lekérdezésével.
     */
    private void loadUnitPrice(){
        unitPriceList.removeAll(unitPriceList);
        List<String> actualUnitPriceNameList = new UnitPriceModel().getUnitPriceName();
        unitPriceList.addAll(actualUnitPriceNameList);
        comboBoxUnitPrice.getItems().addAll(unitPriceList);
        log.info("Beviteli mezők frissítése egységár adatokkal.");
    }

    /**
     * Egérkattintást kezelő metódus, használatával törlésre kerül
     * a kiválasztott árukészlet objektum és frissül a táblázat.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void actionDelStockContact(MouseEvent event){
        log.info("Gomb használva: Töröl.");
        if (selectedStock != null){
            pm.removeStock(selectedStock);
            actionCleanStockTextField(event);
            updateTableData();
            log.info("Telephely törölve.");
        }
    }

    /**
     * Egérkattintást kezelő metódus, használatával törlésre kerül
     * a kiválasztott árukészlet objektum.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void actionModStockContact(MouseEvent event){
        log.info("Gomb használva: Módosítás.");
        purveyor = new Purveyor();
        product = new Product();
        place = new Place();
        unitPrice = new UnitPrice();
        if (selectedStock != null){
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
            long placeId = 0;
            List<Place> pll = new PlaceModel().getPlace();
            for (Place list : pll) {
                if (list.getLabel().equals(comboBoxPlace.getValue())){
                    placeId = list.getId();
                }
            }
            long unitPriceId = 0;
            List<UnitPrice> upl = new UnitPriceModel().getUnitPrice();
            for (UnitPrice list : upl) {
                if (list.getPrice() == Integer.parseInt(comboBoxUnitPrice.getValue().toString())){
                    unitPriceId = list.getId();
                }
            }
            selectedStock.setPurveyor(purveyor.findPurveyor(purveyorId));
            selectedStock.setProduct(product.findProduct(productId));
            selectedStock.setPlace(place.findPlace(placeId));
            selectedStock.setUnitPrice(unitPrice.findUnitPrice(unitPriceId));
            selectedStock.setQuantity(Integer.parseInt(inputQuantity.getText().trim()));
            pm.modStock(selectedStock);
            actionCleanStockTextField(event);
            updateTableData();
            log.info("Árukészlet adatai módosítva.");
        }
    }

    /**
     * Egérkattintást kezelő metódus, használatával tárolásra kerül
     * a mezőkben rögzített adatokból létrehozott árukészlet objektum.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void actionAddStockContact(MouseEvent event){
        log.info("Gomb használva: Hozzáadás.");
        purveyor = new Purveyor();
        product = new Product();
        place = new Place();
        unitPrice = new UnitPrice();
        if (comboBoxPurveyor.getValue() != null && comboBoxProduct.getValue() != null
                && comboBoxPlace.getValue() != null && comboBoxUnitPrice.getValue() != null
                && inputQuantity != null){
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
            long placeId = 0;
            List<Place> pll = new PlaceModel().getPlace();
            for (Place list : pll) {
                if (list.getLabel().equals(comboBoxPlace.getValue())){
                    placeId = list.getId();
                }
            }
            long unitPriceId = 0;
            List<UnitPrice> upl = new UnitPriceModel().getUnitPrice();
            for (UnitPrice list : upl) {
                if (list.getPrice() == Integer.parseInt(comboBoxUnitPrice.getValue().toString())){
                    unitPriceId = list.getId();
                }
            }
            Stock newStock = new Stock(
                    purveyor.findPurveyor(purveyorId),
                    product.findProduct(productId),
                    place.findPlace(placeId),
                    unitPrice.findUnitPrice(unitPriceId),
                    Integer.parseInt(inputQuantity.getText().trim())
            );
            data.add(newStock);
            pm.addStock(newStock);
            clearInputBox();
            log.info("Árukészlet betőltve.");
        }
    }

    /**
     * Táblázat adatait frissítő metódus.
     */
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
        purveyorColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stock, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stock, String> param) {
                return new SimpleStringProperty(param.getValue().getPurveyor().getLabel());
            }
        });
        productColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        productColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stock, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stock, String> param) {
                return new SimpleStringProperty(param.getValue().getProduct().getLabel());
            }
        });
        placeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        placeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stock, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stock, String> param) {
                return new SimpleStringProperty(param.getValue().getPlace().getLabel());
            }
        });
        unitPriceColumn.setCellFactory(TextFieldTableCell.<Stock, Number>forTableColumn(new NumberStringConverter()));
        unitPriceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stock, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Stock, Number> param) {
                return new SimpleIntegerProperty(param.getValue().getUnitPrice().getPrice());
            }
        });
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
        log.info("Táblázat adatai frissítésre kerültek.");
    }

    /**
     * Táblázat sorának kiválasztását kezelő metódis.
     * A kiválasztott sor adatait beviteli mezőbe másolja és szerkesztési gombokra vált.
     */
    public void editedRow() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            selectedStock = (Stock) table.getSelectionModel().getSelectedItem();
            cleanStockTextFieldButton.setVisible(true);
            addStockButton.setVisible(false);
            delStockButton.setVisible(true);
            modStockButton.setVisible(true);
            comboBoxPurveyor.setValue(selectedStock.getPurveyor().getLabel());
            comboBoxProduct.setValue(selectedStock.getProduct().getLabel());
            comboBoxPlace.setValue(selectedStock.getPlace().getLabel());
            comboBoxUnitPrice.setValue(selectedStock.getUnitPrice().getPrice());
            inputQuantity.setText(String.valueOf(selectedStock.getQuantity()));
            log.info("Táblázat adatai beviteli mezőkbe másolódtak.");
        }
    }
}