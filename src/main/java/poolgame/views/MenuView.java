package poolgame.views;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import poolgame.helpers.View;
import poolgame.models.Menu;
import poolgame.models.MenuButton;

import java.util.ArrayList;

public class MenuView extends Region implements View {
    // Properties
    private ArrayList<ButtonView> buttonViews;

    /**
     * New instance of MenuView
     *
     * @param menu the model to take data from
     */
    public MenuView(Menu menu) {
        this.setFocusTraversable(true);
        Rectangle background = new Rectangle(0,0, Menu.WIDTH, Menu.HEIGHT);
        background.setFill(Color.LIGHTBLUE);
        buttonViews = new ArrayList<>();
        for(MenuButton menuButton : menu.getMenuButtonList()) {
            buttonViews.add(new ButtonView(menuButton));
        }

        getChildren().add(background);
        getChildren().addAll(buttonViews);

        update();
    }

    /**
     * Updates the menu buttons
     */
    @Override
    public void update() {
        for(ButtonView btn : buttonViews) {
            btn.update();
        }
    }
}
