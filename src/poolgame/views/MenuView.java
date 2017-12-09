package poolgame.views;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import poolgame.helpers.View;
import poolgame.models.MenuButton;
import poolgame.models.Menu;

import java.util.ArrayList;
import java.util.Iterator;

public class MenuView extends Region implements View {
    public static int HEIGHT = 700;
    public static int WIDTH = 550;

    private ArrayList<ButtonView> buttonViews;

    private Menu model;

    public MenuView(Menu model) {
        this.model = model;
        this.setFocusTraversable(true);
        this.model = model;
        Rectangle background = new Rectangle(0,0, WIDTH, HEIGHT);
        background.setFill(Color.LIGHTBLUE);
        buttonViews = new ArrayList<>();
        for(MenuButton menuButton : model.getButtons()) {
            buttonViews.add(new ButtonView(menuButton));
        }

        getChildren().add(background);
        getChildren().addAll(buttonViews);

        update();
    }


    @Override
    public void update() {
        for(ButtonView btn : buttonViews) {
            btn.update();
        }
    }
}
