package ua.lyohha.testing;

import javafx.scene.control.Label;

public class Symbol {

    private char symbol;
    private Label item = null;
    private boolean wordEnd = false;

    public Symbol(char symbol, boolean wordEnd)
    {
        this.wordEnd = wordEnd;
        this.symbol = symbol;
        item = new Label();

        if (symbol == '\n')
            symbol = ' ';


        item.setText("" + symbol);
    }

    public Symbol(char symbol) {
        this.symbol = symbol;
        item = new Label();

        if (symbol == '\n')
            symbol = ' ';


        item.setText("" + symbol);
    }

    public void setActive() {
        if (item == null)
            return;
        item.getStyleClass().clear();
        item.getStyleClass().add("text-selected");
    }

    public void setError() {
        if (item == null)
            return;
        item.getStyleClass().clear();
        item.getStyleClass().add("text-error");
    }

    public void setAccepted() {

        if (item == null)
            return;
        item.getStyleClass().clear();
        item.getStyleClass().add("text-accepted");
    }

    public void setErrorSelected() {

        if (item == null)
            return;
        item.getStyleClass().clear();
        item.getStyleClass().add("text-error-selected");
    }

    public void resetStyle() {
        if (item == null)
            return;
        item.getStyleClass().clear();
    }

    public char getSymbol() {
        return symbol;
    }

    public Label getControl() {
        return item;
    }

    public boolean isWordEnd()
    {
        return wordEnd;
    }

}
