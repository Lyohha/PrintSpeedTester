package ua.lyohha.testing;

import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private String line = null;
    private List<Symbol> items;
    private HBox hBox = null;

    public Line(String line)
    {
        this.line = line;
        this.hBox = new HBox();
        this.items = new ArrayList<>();
        initLine();
    }

    private void initLine()
    {
        for (int i = 0; i < line.length(); i++)
        {
            Symbol symbol = new Symbol(line.charAt(i));
            hBox.getChildren().add(symbol.getControl());
        }
    }

    public HBox getLine()
    {
        return hBox;
    }

    public int getCount()
    {
        return items.size();
    }

    public boolean compareSymbol(char symbol, int item)
    {
        return items.get(item).getSymbol() == symbol;
    }
}
