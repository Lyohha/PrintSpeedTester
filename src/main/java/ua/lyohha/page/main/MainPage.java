package ua.lyohha.page.main;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ua.lyohha.page.Page;
import ua.lyohha.page.testing.TestingPage;

import java.awt.*;

public class MainPage extends Page {
    public VBox mainVBox;
    public Button openButton;
    private String styleFile = "/assets/styles/main.css";
    private String page = "/assets/page/MainPage.fxml";

    @Override
    public Parent getParent() {
        return null;
    }

    @Override
    public String getStyleClass() {
        return this.styleFile;
    }

    @Override
    public String getPage() {
        return this.page;
    }

    @Override
    public void initializeComponent() {

    }

    @Override
    public void setParams(Object... params) {

    }

    public void openButtonClick(ActionEvent actionEvent) {

        /*FileDialog fileDialog = new FileDialog((Frame)null, "Open File");
        fileDialog.setMode(FileDialog.LOAD);
        fileDialog.setVisible(true);

        String file = fileDialog.getFile();

        // file
        System.out.println(file);
        // path
        System.out.println(fileDialog.getDirectory());*/

        this.navigation.navigateTo(TestingPage.class);
    }
}
