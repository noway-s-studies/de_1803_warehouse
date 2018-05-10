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

public class ProductController implements Initializable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private ProductModel pm;
    private ObservableList<Product> data = FXCollections.observableArrayList();
    private Product selectedProduct = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pm = new ProductModel();
        table.getColumns().removeAll();
        updateTableData();
        table.setOnMouseClicked((MouseEvent event) -> {
            editedRow();
        });
    }

    @FXML
    TableView table;
    @FXML
    TextField inputLabel;
    @FXML
    TextField inputUnitLabel;
    @FXML
    Button cleanProductTextFieldButton;
    @FXML
    Button addProductButton;
    @FXML
    Button delProductButton;
    @FXML
    Button modProductButton;
    @FXML
    private TableColumn<Product, String> labelColumn = null;
    @FXML
    private TableColumn<Product, String> unitLabelColumn = null;

    @FXML
    public void actionCleanProductTextField(MouseEvent event){
        clearInputBox();
        cleanProductTextFieldButton.setVisible(true);
        addProductButton.setVisible(true);
        delProductButton.setVisible(false);
        modProductButton.setVisible(false);
        selectedProduct = null;
    }

    private void clearInputBox() {
        inputLabel.clear();
        inputUnitLabel.clear();
    }

    @FXML
    public void actionDelProductContact(MouseEvent event){
        if (selectedProduct != null){
            pm.removeProduct(selectedProduct);
            actionCleanProductTextField(event);
            updateTableData();
        }
    }

    @FXML
    public void actionModProductContact(MouseEvent event){
        if (selectedProduct != null){
            selectedProduct.setLabel(inputLabel.getText());
            selectedProduct.setUnitLabel(inputUnitLabel.getText());

            pm.modProduct(selectedProduct);
            actionCleanProductTextField(event);
            updateTableData();
        }
    }

    @FXML
    public void actionAddProductContact(MouseEvent event){
        if (inputLabel != null && inputUnitLabel != null){
            Product newPureyor = new Product(
                    inputLabel.getText(),
                    inputUnitLabel.getText()
            );
            data.add(newPureyor);
            pm.addProduct(newPureyor);
            clearInputBox();
            log.info("Új beszerzőt betőltve");
        }
    }

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
    }

    public void editedRow() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            selectedProduct = (Product) table.getSelectionModel().getSelectedItem();
            cleanProductTextFieldButton.setVisible(true);
            addProductButton.setVisible(false);
            delProductButton.setVisible(true);
            modProductButton.setVisible(true);
            inputLabel.setText(selectedProduct.getLabel());
            inputUnitLabel.setText(selectedProduct.getUnitLabel());
        }
    }
}
