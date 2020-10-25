package ua.lyohha.page.testing;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ua.lyohha.page.Page;
import ua.lyohha.page.result.ResultPage;
import ua.lyohha.testing.Line;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class TestingPage extends Page {
    public GridPane mainGridPane;
    public ScrollPane textPane;
    public VBox textVBox;
    public Label fileField;
    public Label timeField;
    public Label spmField;
    public Label wpmField;
    public CheckBox ignoreErrorCB;
    public Label scrollField;

    private String styleFile = "/assets/styles/testing.css";
    private String page = "/assets/page/TestingPage.fxml";

    private String filePath = null;
    private List<Line> lines = null;

    private int posLine = 0;
    private int posSymbol = 0;
    private Timer timer = null;
    private Thread timerThread = null;

    private int countSymbols = 0;
    private int countWords = 0;
    private int countWordsShowed = 0;
    private int countErrors = 0;

    private boolean disabledCheckBox = false;
    private boolean ignoreError = false;

    private double scrollCoefficient = 0;

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
        if (filePath == null) {
            System.out.println("file");
            this.navigation.navigateBack();
            return;
        }

        lines = new ArrayList<>();
        String[] lines = getStringLines();
        if (lines != null)
            this.fillLines(lines);

        /*textPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                event.consume();
                System.out.println("on scroll");
            }
        });*/
        textPane.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.toString());
                /*if ((event.getCode() != KeyCode.UNDEFINED && event.getEventType() == KeyEvent.KEY_PRESSED) ||
                        (event.getText().length() != 0 && event.getEventType() == KeyEvent.KEY_PRESSED))*/
                if(event.getEventType() == KeyEvent.KEY_TYPED ||
                        (event.getCode().getName().equals("Backspace") && event.getEventType() == KeyEvent.KEY_PRESSED))
                    onKeyPressed(event);

                event.consume();
                //onKeyPressed(event);

            }
        });

        textPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //onKeyPressed(event);
            }
        });

        textPane.requestFocus();
        textVBox.requestFocus();


    }

    @Override
    public void setParams(Object... params) {
        if (params.length > 1) {
            filePath = (String) params[0] + (String) params[1];
            fileField.setText((String) params[1]);
        } else {
            System.out.println("params error");
        }
    }

    private String[] getStringLines() {
        List<String> lines = new ArrayList<>();

        String text;

        try {
            text = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            this.navigation.navigateBack();
            return null;
        }


        text = text.replaceAll("\r", "");

        String[] paragraphs = text.split("\n");

        for (String paragraph : paragraphs) {
            String[] words = paragraph.split(" ");

            int length = 0;

            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < words.length; i++) {
                //for (String word : words) {
                String word = words[i];

                if (word.length() == 0)
                    continue;

                if (length + word.length() + 1 > 50) {
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
                //builder.append('\n');
                String line = builder.toString();
                line = line.substring(0, line.length() - 1) + "\n";
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

    private void onKeyPressed(KeyEvent event) {
        /*System.out.println("Symbol");
        System.out.println(event.getCode());
        System.out.println(event.getText());

        String sym = event.getCharacter();

        System.out.println((int)sym.charAt(0));
        System.out.println(event.isShiftDown()?"Shift On":"Shift Off");*/

        if (timer == null) {
            timer = new Timer();
            timerThread = new Thread(timer);
            timerThread.start();

            //scrollCoefficient = (double)115 / textPane.getHeight();

            //scrollCoefficient = (double)91 / textPane.getContent().getBoundsInLocal().getHeight();
            scrollCoefficient = (lines.get(0).getHeight() - 5) / (textPane.getContent().getBoundsInLocal().getHeight() + 10 - textPane.getHeight());
            System.out.println(scrollCoefficient);
            System.out.println(textPane.getHeight());
            System.out.println(textPane.getContent().getBoundsInLocal().getHeight());
            //System.out.println(lines.get(0).getHeight());
            System.out.println("Height");
            for (Line item : lines) {
                System.out.println(item.getHeight());
            }
            System.out.println("/Height");

        } else
        {
            System.out.println("new Height");
            System.out.println(textPane.getContent().getBoundsInLocal().getHeight());
        }

        if (!disabledCheckBox) {
            disabledCheckBox = true;
            ignoreError = ignoreErrorCB.isSelected();
            ignoreErrorCB.setDisable(true);
        }

        //String symbol = event.getText();
        String symbol = event.getCharacter();

        //if (symbol.length() == 1) {
        if (!event.getCode().getName().equals("Backspace")) {
            System.out.println(symbol);
            //symbol = event.getText();
            if (symbol.equals("\r"))
                symbol = "\n";
            if (event.isShiftDown()) {
                if (symbol.charAt(0) == '\'')
                    symbol = "\"";
                else if (symbol.charAt(0) == '/')
                    symbol = "?";
                else
                    symbol = symbol.toUpperCase();
            }

            boolean isError = false;

            if (lines.get(posLine).compareSymbol(symbol.charAt(0), posSymbol)) {
                lines.get(posLine).setAccepted(posSymbol);
                if (lines.get(posLine).isWordEnd(posSymbol)) {
                    countWords++;
                    if (countWords > countWordsShowed)
                        countWordsShowed = countWords;
                }

            } else {
                countErrors++;
                if (ignoreError) {
                    lines.get(posLine).setError(posSymbol);
                } else {
                    isError = true;
                    posSymbol--;
                }

            }

            System.out.println(textPane.getVvalue());
            //scrollField.setText(Double.toString(textPane.getVvalue()));

            posSymbol++;

            if (lines.get(posLine).length() == posSymbol) {
                posSymbol = 0;
                posLine++;
                //textPane.setVvalue(0.19291 * posLine);
                textPane.setVvalue(textPane.getVvalue() + scrollCoefficient);
            }

            if (posLine != lines.size()) {
                if (isError)
                    lines.get(posLine).setErrorSelected(posSymbol);
                else
                    lines.get(posLine).setActive(posSymbol);
                countSymbols++;
            } else {
                timerThread.interrupt();

                this.navigation.navigateTo(ResultPage.class, fileField.getText(), countSymbols, countWords, countErrors, timer.time);
            }

        } else {
            System.out.println("2 ");
            System.out.println(symbol);
            System.out.println(event.getCode().getName());
            if (event.getCode().getName().equals("Backspace")) {
                if (lines.get(posLine).isWordEnd(posSymbol)) {
                    countWords--;
                }


                lines.get(posLine).resetStyle(posSymbol);
                if (posSymbol == 0) {
                    if (posLine != 0) {
                        posLine--;
                        posSymbol = lines.get(posLine).getCount() - 1;
                        textPane.setVvalue(textPane.getVvalue() - scrollCoefficient);
                    }
                } else
                    posSymbol--;
                lines.get(posLine).setActive(posSymbol);
            }
        }
    }

    private int mathSPM() {
        float perSec = (float) countSymbols / timer.time;

        float perMin = perSec * 60;

        return (int) perMin;

    }

    private int mathWPM() {
        float perSec = (float) countWordsShowed / timer.time;

        float perMin = perSec * 60;

        return (int) perMin;
    }

    class Timer implements Runnable {
        private int time = 0;

        @Override
        public void run() {
            while (true) {
                int sleep = 1000;
                long startTime, endTime;

                startTime = System.currentTimeMillis();

                int minutes = time / 60;
                int sec = time % 60;

                String timeFromStart = String.format("%02d:%02d", minutes, sec);

                //System.out.println(timeFromStart);

                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        timeField.setText(timeFromStart);
                        spmField.setText(Integer.toString(mathSPM()));
                        wpmField.setText(Integer.toString(mathWPM()));
                    }
                };

                Platform.runLater(updater);

                endTime = System.currentTimeMillis();
                try {
                    Thread.sleep(sleep - (endTime - startTime));
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                    return;
                }
                time++;
            }
        }
    }
}
