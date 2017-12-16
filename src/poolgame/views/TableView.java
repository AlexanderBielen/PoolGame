package poolgame.views;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import poolgame.helpers.View;
import poolgame.models.Ball;
import poolgame.models.Table;

import javafx.scene.layout.Region;
import java.util.ArrayList;

public class TableView  extends Region implements View {


    private ArrayList<BallView> ballViews;

    public TableView(Table tableModel) {
        this.setFocusTraversable(true);

        Rectangle table = new Rectangle(0,0, Table.WIDTH, Table.HEIGHT);
        table.setFill(new Color(0, 0, 0, 1));

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
