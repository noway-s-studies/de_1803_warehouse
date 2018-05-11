package hu.unideb.inf.warehouse.controller.dataHandling;

import hu.unideb.inf.warehouse.model.ProductModel;
import hu.unideb.inf.warehouse.pojo.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Az áru megjelenítéséért felelős osztály.
 *
 */
public class ProductController implements Initializable {

    /**
     * Itt kerül inicializálásra az áruk panel.
     * A megjelenő táblázat adatai trölődnek majd a lekérdezett adatokkal feltöltésre kerülnek.
     *
     * @param location inicializálás URL objektuma
     * @param resources inicializálás ResourceBundle objektuma
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pm = new ProductModel();
        table.getColumns().removeAll();
        updateTableData();
        table.setOnMouseClicked((MouseEvent event) -> {
            log.info("Táblázat sorának megjelölése.");
            editedRow();
        });
    }
    /**
     * Logger osztály egy példánya.
     */
    private static Logger log = LoggerFactory.getLogger(ProductController.class);
    /**
     * ProductModel osztály egy példánya.
     */
    private ProductModel pm;
    /**
     * A Product osztály elemeinek listája.
     */
    private ObservableList<Product> data = FXCollections.observableArrayList();
    /**
     * A Product osztály táblázatban megjelölt eleme.
     */
    private Product selectedProduct = null;
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
     * TextField objektum egy példánya, beviteli mező a mértékegység módosításához.
     */
    @FXML
    private TextField inputUnitLabel;
    /**
     * Button objektum egy példánya, gomb elem a beviteli mezők adatainak törléséhez.
     */
    @FXML
    private Button cleanProductTextFieldButton;
    /**
     * Button objektum egy példánya, gomb elem új példány betöltéséhez.
     */
    @FXML
    private Button addProductButton;
    /**
     * Button objektum egy példánya, gomb elem a kiválasztott példány törléséhez.
     */
    @FXML
    private Button delProductButton;
    /**
     * Button objektum egy példánya, gomb elem a kiválasztott példány adatainak módosításához.
     */
    @FXML
    private Button modProductButton;
    /**
     * TableColumn objektum egy példánya, táblázat oszlop a megnevezés megjelenítéséhez.
     */
    @FXML
    private TableColumn<Product, String> labelColumn = null;
    /**
     * TableColumn objektum egy példánya, táblázat oszlop a mértékegység megjelenítéséhez.
     */
    @FXML
    private TableColumn<Product, String> unitLabelColumn = null;

    /**
     * Egérkattintást kezelő metódus, használatával a beviteli mezők értékei
     * törlődnek és a gombok láthatósága alapállapotba kerül.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void actionCleanProductTextField(MouseEvent event){
        log.info("Gomb használva: Takarít.");
        clearInputBox();
        cleanProductTextFieldButton.setVisible(true);
        addProductButton.setVisible(true);
        delProductButton.setVisible(false);
        modProductButton.setVisible(false);
        selectedProduct = null;
    }

    /**
     * Beviteli mezők értékeinek törlését végző metódus.
     */
    private void clearInputBox() {
        inputLabel.clear();
        inputUnitLabel.clear();
        log.info("Beviteli mezők törölve.");
    }

    /**
     * Egérkattintást kezelő metódus, használatával törlésre kerül
     * a kiváélasztott áru objektum és frissül a táblázat.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void actionDelProductContact(MouseEvent event){
        if (selectedProduct != null){
            pm.removeProduct(selectedProduct);
            actionCleanProductTextField(event);
            updateTableData();
            log.info("Áru törölve.");
        }
    }

    /**
     * Egérkattintást kezelő metódus, használatával törlésre kerül
     * a kiválasztott áru objektum.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */

    @FXML
    public void actionModProductContact(MouseEvent event){
        log.info("Gomb használva: Módosítás.");
        if (selectedProduct != null){
            selectedProduct.setLabel(inputLabel.getText());
            selectedProduct.setUnitLabel(inputUnitLabel.getText());
            pm.modProduct(selectedProduct);
            actionCleanProductTextField(event);
            updateTableData();
            log.info("Áru adatai módosítva.");
        }
    }

    /**
     * Egérkattintást kezelő metódus, használatával tárolásra kerül
     * a mezőkben rögzített adatokból létrehozott áru objektum.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
    @FXML
    public void actionAddProductContact(MouseEvent event){
        log.info("Gomb használva: Hozzáadás.");
        if (inputLabel != null && inputUnitLabel != null){
            Product newPureyor = new Product(
                    inputLabel.getText(),
                    inputUnitLabel.getText()
            );
            data.add(newPureyor);
            pm.addProduct(newPureyor);
            clearInputBox();
            log.info("Áru betőltve.");
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
        unitLabelColumn = new TableColumn("Mértékegység");
        unitLabelColumn.setMinWidth(100);
        labelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        labelColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("label"));
        unitLabelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        unitLabelColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("unitLabel"));
        table.getColumns().addAll(labelColumn, unitLabelColumn);
        data.addAll(pm.getProduct());
        table.setItems(data);
        log.info("Táblázat adatai frissítésre kerültek.");
    }

    /**
     * Táblázat sorának kiválasztását kezelő metódis.
     * A kiválasztott sor adatait beviteli mezőbe másolja és szerkesztési gombokra vált.
     */
    public void editedRow() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            selectedProduct = (Product) table.getSelectionModel().getSelectedItem();
            cleanProductTextFieldButton.setVisible(true);
            addProductButton.setVisible(false);
            delProductButton.setVisible(true);
            modProductButton.setVisible(true);
            inputLabel.setText(selectedProduct.getLabel());
            inputUnitLabel.setText(selectedProduct.getUnitLabel());
            log.info("Táblázat adatai beviteli mezőkbe másolódtak.");
        }
    }
}
