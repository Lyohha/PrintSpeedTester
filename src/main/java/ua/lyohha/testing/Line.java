package ua.lyohha.testing;

import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private String line = null;
    private List<Symbol> items;
    private HBox hBox = null;

    public Line(String line) {
        this.line = line;
        this.hBox = new HBox();
        this.items = new ArrayList<>();
        initLine();
    }

    private void initLine() {
        for (int i = 0; i < line.length(); i++) {
            Symbol symbol = null;
            if (line.charAt(i) == ' ' || line.charAt(i) == '\n')
                symbol = new Symbol(line.charAt(i), true);
            else
                symbol = new Symbol(line.charAt(i), false);
            hBox.getChildren().add(symbol.getControl());
            items.add(symbol);
        }
    }

    public HBox getLine() {
        return hBox;
    }

    public int getCount() {
        return items.size();
    }

    public boolean compareSymbol(char symbol, int item) {
        return items.get(item).getSymbol() == symbol;
    }

    public void setActive(int index) {
        items.get(index).setActive();
    }

    public void setError(int index) {
        items.get(index).setError();
    }
    public void setErrorSelected(int index) {
        items.get(index).setErrorSelected();
    }


    public void setAccepted(int index) {
        items.get(index).setAccepted();
    }

    public void resetStyle(int index) {
        items.get(index).resetStyle();
    }

    public int length() {
        return items.size();
    }

    public boolean isWordEnd(int index) {
        return items.get(index).isWordEnd();
    }

}


