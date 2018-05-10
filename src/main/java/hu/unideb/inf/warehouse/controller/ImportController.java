package hu.unideb.inf.warehouse.controller;

import hu.unideb.inf.warehouse.model.ProductModel;
import hu.unideb.inf.warehouse.pojo.Product;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.Scanner;

public class ImportController {
    private ProductModel productModel = null;

    @FXML
    TextField importFileName;

    @FXML
    public void importButtonClicked(MouseEvent event) {

        if (importFileName.getText() != null && importFileName.getText().length() > 0) {
            productModel = new ProductModel();
            try {
                Scanner fajl = new Scanner(new File("src/main/resources/import/"+importFileName.getText()+".txt"));
                while (fajl.hasNextLine()) {
                    Scanner sor = new Scanner(fajl.nextLine());
                    sor.useDelimiter(";");
                    String label = sor.next();
                    String unitLabel = sor.next();
                    productModel.addProduct(new Product(label,unitLabel));
                }
            } catch (Exception ex){
                System.out.println("Fálj beolvasási hiba:\n"+ex);
            }
        }
    }
}

