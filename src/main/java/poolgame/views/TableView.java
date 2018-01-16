package poolgame.views;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import poolgame.helpers.View;
import poolgame.models.Ball;
import poolgame.models.Pocket;
import poolgame.models.Table;

import java.util.ArrayList;

public class TableView  extends Region implements View {
    // Properties
    private ArrayList<BallView> ballViews;

    private ArrayList<PocketView> pocketViews;

    /**
     * New instance of TableView that draws a pool table, balls and pockets
     *
     * @param tableModel the model to take data from
     */
    public TableView(Table tableModel) {
        this.setFocusTraversable(true);

        Rectangle table = new Rectangle(-5,-5, Table.WIDTH + 10, Table.HEIGHT + 10);
        table.setFill(Color.SADDLEBROWN);
        table.setArcWidth(30);
        table.setArcHeight(30);

        Rectangle clothEdge = new Rectangle(10,10,Table.WIDTH-20,Table.HEIGHT-20);
        clothEdge.setFill(new Color(0, 0.1765, 0.4157, 1));

        Rectangle clothField = new Rectangle(20,20,Table.WIDTH-40,Table.HEIGHT-40);
        clothField.setFill(new Color(0, 0.2588, 0.6, 1));

        getChildren().add(table);
        getChildren().add(clothEdge);
        getChildren().add(clothField);

        ballViews = new ArrayList<>();
        for(Ball ball : tableModel.getBalls()) {
            ballViews.add(new BallView(ball));
        }

        pocketViews = new ArrayList<>();
        for(Pocket pocket : tableModel.getPockets()) {
            pocketViews.add(new PocketView(pocket));
        }

        getChildren().addAll(pocketViews);
        getChildren().addAll(ballViews);

        update();
    }

    /**
     * Updates the views of all balls
     */
    @Override
    public void update() {
        for(BallView bv : ballViews) {
            bv.update();
        }
    }


}
