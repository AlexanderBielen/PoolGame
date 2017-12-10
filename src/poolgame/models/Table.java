package poolgame.models;

import javafx.scene.paint.Color;
import poolgame.helpers.Model;

import java.util.ArrayList;

public class Table implements Model {
    private ArrayList<Ball> balls;

    public Table() {
        initialize();
    }

    private void initialize() {
        balls = new ArrayList<>();
        balls.add(new Ball(250, 250, Color.WHITE, true));
        balls.add(new Ball(250, 550, Color.BLUE));
        balls.add(new Ball(250, 500, Color.RED));
        balls.add(new Ball(250, 520, Color.ORANGE));
        balls.add(new Ball(200, 550, Color.YELLOW));
        balls.add(new Ball(200, 500, Color.GREEN));
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }
}
