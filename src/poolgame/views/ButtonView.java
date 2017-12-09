package poolgame.views;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import poolgame.helpers.View;
import poolgame.models.MenuButton;

public class ButtonView extends Group implements View {
    private MenuButton menuButton;
    private Rectangle buttonView;
    private Text buttonText;

    ButtonView(MenuButton menuButton) {
        this.menuButton = menuButton;
        buttonView = new Rectangle(0,0, menuButton.getWidth(), menuButton.getHeight());
        buttonView.setFill(Color.YELLOW);
        buttonText = new Text(40,20, menuButton.getText());

        buttonText.translateXProperty().bind(buttonView.translateXProperty());
        buttonText.translateYProperty().bind(buttonView.translateYProperty());

        getChildren().addAll(buttonView, buttonText);

        update();
    }

    @Override
    public void update() {
        buttonView.setTranslateX((menuButton.getBeginX() * MenuView.WIDTH )/ 3);
        buttonView.setTranslateY((menuButton.getBeginY() * MenuView.HEIGHT) / 10);
        buttonView.setFill(menuButton.isActive() ? Color.RED : Color.YELLOW);
    }
}
