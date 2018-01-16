package poolgame.views;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import poolgame.helpers.View;
import poolgame.models.Pocket;


public class PocketView extends Region implements View{
    // Properties
    private Pocket pocket;
    private Circle pocketView;

    /**
     * New instance of PocketView
     *
     * @param pocket the model to take data from
     */
    public PocketView(Pocket pocket) {
        this.pocket = pocket;
        pocketView = new Circle(pocket.getX(), pocket.getY(), pocket.getRadius());
        pocketView.setFill(Color.BLACK);

        getChildren().add(pocketView);
    }

    /**
     * Does nothing
     */
    @Override
    public void update() {

    }
}
