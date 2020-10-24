package ua.lyohha.page.result;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ua.lyohha.page.Page;
import ua.lyohha.page.main.MainPage;

public class ResultPage extends Page {
    public VBox mainVBox;
    public Button backInMenu;
    public Label timeField;
    public Label spmField;
    public Label fileField;
    public Label wpmField;
    public Label accuracyField;
    public Label countSymbolsField;
    public Label countWordsField;

    private String styleFile = "/assets/styles/result.css";
    private String page = "/assets/page/ResultPage.fxml";

    private String filename;
    private int countSymbols;
    private int countWords;
    private int countErrors;
    private int time;

    public void backInMenuButtonClick(ActionEvent actionEvent) {

        navigation.clearNavigationStack();
        navigation.navigateTo(MainPage.class);

    }

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

    private int mathSPM() {
        float perSec = (float) countSymbols / time;

        float perMin = perSec * 60;

        return (int) perMin;

    }

    private int mathWPM() {
        float perSec = (float) countWords / time;

        float perMin = perSec * 60;

        return (int) perMin;
    }

    private int mathAccuracy()
    {
        float perPersent = 100 / (float)countSymbols;

        int accuracy = (int)(perPersent * (countSymbols - countErrors));

        return accuracy;
    }

    @Override
    public void initializeComponent() {
        fileField.setText(filename);

        int minutes = time / 60;
        int sec = time % 60;

        String timeFromStart = String.format("%02d:%02d", minutes, sec);

        timeField.setText(timeFromStart);

        countSymbolsField.setText(Integer.toString(countSymbols));
        countWordsField.setText(Integer.toString(countWords));

        spmField.setText(Integer.toString(mathSPM()));
        wpmField.setText(Integer.toString(mathWPM()));
        accuracyField.setText(Integer.toString(mathAccuracy()));


    }

    @Override
    public void setParams(Object... params) {
        /*
            params[0] - filename
            params[1] - count symbols
            params[2] - count words
            params[3] - count errors
            params[4] - time
         */
        filename = (String)params[0];
        countSymbols = (Integer)params[1];
        countWords = (Integer)params[2];
        countErrors = (Integer)params[3];
        time = (Integer)params[4];
    }
}
