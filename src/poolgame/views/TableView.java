package poolgame.views;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import poolgame.helpers.View;
import poolgame.models.Ball;
import poolgame.models.Table;

import javafx.scene.layout.Region;
import java.util.ArrayList;

public class TableView  extends Region implements View {
    public static int HEIGHT = 700;
    public static int WIDTH = 550;

    private ArrayList<BallView> ballViews;

    public TableView(Table tableModel) {
        this.setFocusTraversable(true);

        Rectangle table = new Rectangle(0,0, WIDTH, HEIGHT);
        table.setFill(new Color(0, 0, 0, 1));

        Rectangle clothEdge = new Rectangle(10,10,WIDTH-20,HEIGHT-20);
        clothEdge.setFill(new Color(0, 0.1765, 0.4157, 1));

        Rectangle clothField = new Rectangle(20,20,WIDTH-40,HEIGHT-40);
        clothField.setFill(new Color(0, 0.2588, 0.6, 1));

        ballViews = new ArrayList<>();
        for(Ball ball : tableModel.getBalls()) {
            ballViews.add(new BallView(ball));
        }
        getChildren().add(table);
        getChildren().add(clothEdge);
        getChildren().add(clothField);

        getChildren().addAll(ballViews);

        update();
    }

    @Override
    public void update() {
        for(BallView bv : ballViews) {
            bv.update();
        }
    }
}
