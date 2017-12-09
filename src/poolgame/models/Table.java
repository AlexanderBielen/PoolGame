package poolgame.models;

import javafx.scene.paint.Color;
import poolgame.helpers.Model;

import java.util.ArrayList;
import java.util.Iterator;

public class Table implements Model {
    private ArrayList<Ball> balls;

    public Table() {
        initialize();
    }

    private void initialize() {
        balls = new ArrayList<>();
        balls.add(new Ball(250, 250, Color.WHITE, true));
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }
}
