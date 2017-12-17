package poolgame.views;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import poolgame.helpers.View;
import poolgame.models.Menu;
import poolgame.models.MenuButton;

public class ButtonView extends Group implements View {
    private MenuButton menuButton;
    private Rectangle buttonView;
    private Text buttonText;

    ButtonView(MenuButton menuButton) {
        this.menuButton = menuButton;
        buttonView = new Rectangle(0,0, menuButton.getWidth(), menuButton.getHeight());
        buttonView.setFill(Color.YELLOW);
        buttonView.setStroke(Color.BLACK);
        buttonView.setArcHeight(10);
        buttonView.setArcWidth(10);

        buttonText = new Text(75,30, menuButton.getText());
        buttonText.setWrappingWidth(100);
        buttonText.setTextAlignment(TextAlignment.CENTER);

        buttonText.translateXProperty().bind(buttonView.translateXProperty());
        buttonText.translateYProperty().bind(buttonView.translateYProperty());

        getChildren().addAll(buttonView, buttonText);

        update();
    }

    @Override
    public void update() {
        buttonView.setTranslateX(menuButton.getXPosition());
        buttonView.setTranslateY(menuButton.getYPosition());
        buttonView.setStrokeWidth(menuButton.isActive() ? 5 : 0);
    }
}
