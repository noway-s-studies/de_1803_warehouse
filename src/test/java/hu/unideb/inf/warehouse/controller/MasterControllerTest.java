package hu.unideb.inf.warehouse.controller;

import com.itextpdf.text.pdf.PdfPTable;
import hu.unideb.inf.warehouse.utility.PdfExportUtil;
import hu.unideb.inf.warehouse.utility.TextListenerUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class MasterControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(MasterController.class);
    private final String defaultButtonStyle = "-fx-background-color: transparent; -fx-text-fill: #F0F0F0;";
    private final String activeButtonStyle = "-fx-background-color: #404040; -fx-text-fill: #F0F0F0;";
    private BorderPane borderPane;
    @FXML
    private Button homeButton;
    @FXML
    private Button dataHandlingViewButton;
    @FXML
    private Button importButton;
    @FXML
    private Button exportButton;

    MasterController masterController;

    @Test
    public void defaultButtonStyleTest() {
        masterController = new MasterController();


        assertEquals( 1, 1 );
    }

}
