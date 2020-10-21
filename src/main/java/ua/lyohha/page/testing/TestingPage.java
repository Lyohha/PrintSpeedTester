package ua.lyohha.page.testing;

import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ua.lyohha.page.Page;
import ua.lyohha.testing.Line;

import java.util.ArrayList;
import java.util.List;

public class TestingPage extends Page {
    public GridPane mainGridPane;
    public ScrollPane textPane;
    public VBox textVBox;

    private String styleFile = "/assets/styles/testing.css";
    private String page = "/assets/page/TestingPage.fxml";

    private String filePath = null;
    private List<Line> lines = null;

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
        /*if (filePath == null)
        {
            this.navigation.navigateBack();
            return;
        }*/

        lines = new ArrayList<>();
        String[] lines = getStringLines();
        this.fillLines(lines);
    }

    @Override
    public void setParams(Object... params) {
        if (params.length > 0) {
            filePath = (String) params[0];
        }
    }

    private String[] getStringLines() {
        List<String> lines = new ArrayList<>();

        //TODO: File load

        String text = "Some interesting text\nWith symbol of new Line, that show how need move this text on new line!\nAnd some additional line?\"";

        text = text.replaceAll("\r", "");

        String[] paragraphs = text.split("\n");

        for (String paragraph : paragraphs) {
            String[] words = paragraph.split(" ");

            int length = 0;

            StringBuilder builder = new StringBuilder();

            for (String word : words) {
                if (word.length() == 0)
                    continue;

                if (length + word.length() + 1 > 50) {
                    builder.append(' ');
                    String line = builder.toString();
                    lines.add(line);

                    builder = new StringBuilder();

                    length = word.length();

                    builder.append(word);
                    builder.append(' ');
                } else {

                    builder.append(word);
                    builder.append(' ');
                    length += word.length() + 1;
                }
            }

            if (builder.length() != 0) {
                builder.append('\n');
                String line = builder.toString();
                lines.add(line);
            }


        }

        String[] Lines = new String[lines.size()];
        lines.toArray(Lines);
        return Lines;
    }

    private void fillLines(String[] lines) {
        for (String line : lines) {
            Line l = new Line(line);
            this.lines.add(l);

            textVBox.getChildren().add(l.getLine());
        }
    }
}
