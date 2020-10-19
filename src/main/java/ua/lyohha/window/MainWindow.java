package ua.lyohha.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainWindow extends Application {
    public GridPane mainGridPane;
    public GridPane frameGridPane;
    public Label universityNameLabel;
    public Label authorNameLabel;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader mainWindowLoader = new FXMLLoader();
        Parent root = mainWindowLoader.load(getClass().getResource("/assets/window/MainWindow.fxml").openStream());

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(500);
        primaryStage.setTitle("Print Speed Tester");
        primaryStage.show();
    }
}
