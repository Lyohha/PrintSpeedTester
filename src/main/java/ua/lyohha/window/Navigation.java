package ua.lyohha.window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import ua.lyohha.page.Page;
import ua.lyohha.themes.ThemeChangeEvent;
import ua.lyohha.themes.Themes;

import java.util.ArrayList;
import java.util.List;

public class Navigation implements ThemeChangeEvent {

    private List<Parent> pages = new ArrayList<>();
    private List<Page> oPages = new ArrayList<>();
    private Pane pane;

    public Navigation(Pane pane) {

        Themes.addEvent(this);
        this.pane = pane;
    }

    public Object navigateTo(Class c) {
        Object o = null;
        try {
            o = c.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        FXMLLoader loader = new FXMLLoader();

        Parent pageContent = null;
        if (o != null) {
            try {
                if (((Page) o).getPage() != null)
                    pageContent = loader.load(c.getResource(((Page) o).getPage()).openStream());
                else
                    pageContent = ((Page) o).getParent();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (pageContent != null) {
            if (((Page) o).getStyleClass() != null)
                pageContent.getStylesheets().add(c.getResource(((Page) o).getStyleClass()).toExternalForm());
            GridPane.setRowIndex(pageContent, 0);
            pages.add(pageContent);
            oPages.add((Page) o);
            ((Page) loader.getController()).navigation = this;
            ((Page) loader.getController()).initializeComponent();
            updateView();
            return loader.getController();
        }
        return null;
    }

    private void updateView() {
        if (pages.size() != 0) {
            pane.getChildren().clear();
            pane.getChildren().add(pages.get(pages.size() - 1));
        }
    }

    public void navigateBack() {
        if (pages.size() > 1) {
            pages.remove(pages.size() - 1);
            oPages.remove(oPages.size() - 1);
            updateView();
        }
    }

    public void clearNavigationStack() {
        if (pages.size() != 0) {
            Parent parent = pages.get(pages.size() - 1);
            Page page = oPages.get(oPages.size() - 1);
            pages.clear();
            oPages.clear();
            pages.add(parent);
            oPages.add(page);
        }
    }

    @Override
    public void onThemeChange() {
        for (int i = 0; i < pages.size(); i++) {
            pages.get(i).getStylesheets().clear();
            if (oPages.get(i).getStyleClass() != null)
                pages.get(i).getStylesheets()
                        .add(oPages.get(i).getClass()
                                .getResource(oPages.get(i).getStyleClass()).toExternalForm());
        }
    }
}
