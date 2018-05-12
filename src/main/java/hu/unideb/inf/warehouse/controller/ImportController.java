package hu.unideb.inf.warehouse.controller;

import hu.unideb.inf.warehouse.model.ProductModel;
import hu.unideb.inf.warehouse.pojo.Product;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Scanner;

/**
 * Az adatok betöltéséért felelős osztály.
 */
public class ImportController {
    /**
     * Logger osztály egy példánya.
     */
    private static final Logger logger = LoggerFactory.getLogger(ImportController.class);
    /**
     * A ProductModel osztály egy példánya.
     */
    private ProductModel productModel = null;

    /**
     * Adatok betöltéséhez használt fájl neve.
     */
    @FXML
    TextField importFileName;

    /**
     * Egérkattintást kezelő metódus, használatával betöltésre kerülnek a kiválasztott fájlban található adatok.
     *
     * @param event egéresemény aktuális eseményobjektuma
     */
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
                logger.error("Adatbetöltés nem sikerült, fálj beolvasási hiba.");
            }
        } else {
            logger.info("Adatbetöltés nem sikerült, nincs megadva a fájl neve.");
        }
    }
}