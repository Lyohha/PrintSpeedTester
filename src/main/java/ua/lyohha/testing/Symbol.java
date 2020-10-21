package ua.lyohha.testing;

import javafx.scene.control.Label;

public class Symbol {

    private char symbol;
    private Label item = null;

    public Symbol(char symbol)
    {
        this.symbol = symbol;
        item = new Label();
        item.setText("" + symbol);
    }

    public void setActive()
    {
        if(item == null)
            return;
        item.getStyleClass().removeAll();
        item.getStyleClass().add("test-selected");
    }

    public void setError()
    {
        if(item == null)
            return;
        item.getStyleClass().removeAll();
        item.getStyleClass().add("test-error");
    }

    public void setAccepted()
    {
        if(item == null)
            return;
        item.getStyleClass().removeAll();
        item.getStyleClass().add("test-accepted");
    }

    public void resetStyle()
    {
        if(item == null)
            return;
        item.getStyleClass().removeAll();
    }

    public char getSymbol()
    {
        return symbol;
    }

    public Label getControl()
    {
        return item;
    }

}
