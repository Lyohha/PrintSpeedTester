package ua.lyohha.page;

import javafx.scene.Parent;
import ua.lyohha.window.Navigation;

public abstract class Page {
    public Navigation navigation;

    public abstract Parent getParent();

    public abstract String getStyleClass();

    public abstract String getPage();

    public abstract void initializeComponent();
}
