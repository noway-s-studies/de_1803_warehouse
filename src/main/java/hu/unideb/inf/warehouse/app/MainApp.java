package hu.unideb.inf.warehouse.app;

import hu.unideb.inf.warehouse.utility.EntityManagerFactoryUtil;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Scanner;

/**
 * Alkalmazás indító osztály.
 */
public class MainApp extends Application {
    /**
     * Adatbázis hozzáférés jelszava.
     */
    public static String DATABASE_PASSWORD;
    private Stage primaryStage;
    private double xCoordinate, yCoordinate;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        try {
            BorderPane rootView = loadFXML(new BorderPane(), "/view/MasterView.fxml");
            Scene scene = new Scene(rootView);
            scene.setFill(Color.TRANSPARENT);
            positionMod(stage, rootView);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.getStylesheets().add(getClass().getResource("/styles/masterStyle.css").toExternalForm());
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            super.stop();
            EntityManagerFactoryUtil.getInstance().close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private <T> T loadFXML(T identity, String file) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        return (T)loader.load(MainApp.class.getResource(file));
    }

    /**
     * Alkalmazás belépési pontja.
     *
     * @param args paraméter
     *             adatbázis hozzáférési jelszavának megadása kötelező
     */
    public static void main(String[] args) {
        if(args.length == 0){
            Scanner bill = new Scanner(System.in);
            System.out.print("Kérem adja meg az adatbázis jelszavát: ");
            DATABASE_PASSWORD = bill.nextLine();
            System.out.println(DATABASE_PASSWORD);
        } else if(args.length == 1){
            DATABASE_PASSWORD = args[0];
        } else{
            System.exit(1);
        }

        launch(args);
    }

    private void positionMod(Stage stage, Parent root) {
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xCoordinate = event.getSceneX();
                yCoordinate = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX()- xCoordinate);
                stage.setY(event.getScreenY()- yCoordinate);
            }
        });
    }
}
