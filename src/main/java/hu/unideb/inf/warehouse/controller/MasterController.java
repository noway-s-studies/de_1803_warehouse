package hu.unideb.inf.warehouse.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MasterController implements Initializable {

    private final String defaultButtonStyle = "-fx-background-color: transparent; -fx-text-fill: #F0F0F0;";
    private final String activeButtonStyle = "-fx-background-color: #404040; -fx-text-fill: #F0F0F0;";

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button homeButton;
    @FXML
    private Button dataHandlingViewButton;
    @FXML
    private Button importButton;
    @FXML
    private Button exportButton;

    private void defaultButtonStyle(){
        homeButton.setStyle(defaultButtonStyle);
        dataHandlingViewButton.setStyle(defaultButtonStyle);
        importButton.setStyle(defaultButtonStyle);
        exportButton.setStyle(defaultButtonStyle);
    }

    @FXML
    public void close (MouseEvent event) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);

    }

    @FXML
    void homeView(MouseEvent event) {
        defaultButtonStyle();
        homeButton.setStyle(activeButtonStyle);
        loadCenter("HomeView");
    }

    @FXML
    void importView(MouseEvent event) {
        defaultButtonStyle();
        importButton.setStyle(activeButtonStyle);
        loadCenter("ImportView");
    }

    @FXML
    void exportView(MouseEvent event) {
        defaultButtonStyle();
        exportButton.setStyle(activeButtonStyle);
        loadCenter("ExportView");
    }

    @FXML
    void DataHandlingView(MouseEvent event) {
        defaultButtonStyle();
        dataHandlingViewButton.setStyle(activeButtonStyle);
        Parent root = null;
        TabPane tp = new TabPane();
        try {
            borderPane.setCenter(tp);
            tp.getTabs().add(FXMLLoader.load(getClass().getResource("/view/dataHandling/StockView.fxml")));
            tp.getTabs().add(FXMLLoader.load(getClass().getResource("/view/dataHandling/PlaceView.fxml")));
            tp.getTabs().add(FXMLLoader.load(getClass().getResource("/view/dataHandling/ProductView.fxml")));
            tp.getTabs().add(FXMLLoader.load(getClass().getResource("/view/dataHandling/PurveyorView.fxml")));
            tp.getTabs().add(FXMLLoader.load(getClass().getResource("/view/dataHandling/UnitPriceView.fxml")));
        } catch (IOException ex) {
            System.out.println("Fxml megjelenítési hiba:\n" + ex);
        }
    }

    private void loadCenter(String ui){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/" +ui+".fxml"));

        } catch (IOException e) {

        }
        borderPane.setCenter(root);
    }

    private void loadRoot(String ui){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/" +ui+".fxml"));

        } catch (IOException e) {

        }
        borderPane.setBottom(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCenter("HomeView");
    }
}