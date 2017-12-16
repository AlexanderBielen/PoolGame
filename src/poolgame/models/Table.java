package poolgame.models;

import javafx.scene.paint.Color;
import poolgame.helpers.Model;

import java.util.ArrayList;

public class Table implements Model {
    public static int HEIGHT = 700;
    public static int WIDTH = 550;

    private ArrayList<Ball> balls;
    private int xt;
    private int yt;

    public Table() {
        xt = WIDTH / 2;
        yt = HEIGHT / 3 * 2;
        initialize();
    }

    private void initialize() {
        balls = new ArrayList<>();
        balls.add(new Ball(xt, yt - 350, Color.WHITE, true));

        balls.add(new Ball(xt, yt, Color.BLUE));

        balls.add(new Ball(xt-10, yt+20, Color.GREEN));
        balls.add(new Ball(xt+10, yt+20, Color.GREEN));

//        balls.add(new Ball(xt-20, yt+40, Color.RED));
//        balls.add(new Ball(xt, yt+40, Color.BLACK));
//        balls.add(new Ball(xt+20, yt+40, Color.YELLOW));
//
//        balls.add(new Ball(xt-30, yt+60, Color.GREEN));
//        balls.add(new Ball(xt-10, yt+60, Color.BROWN));
//        balls.add(new Ball(xt+10, yt+60, Color.ORANGE));
//        balls.add(new Ball(xt+30, yt+60, Color.AQUA));
//
//        balls.add(new Ball(xt-40, yt+80, Color.PURPLE));
//        balls.add(new Ball(xt-20, yt+80, Color.GRAY));
//        balls.add(new Ball(xt, yt+80, Color.GREEN));
//        balls.add(new Ball(xt+20, yt+80, Color.GREEN));
//        balls.add(new Ball(xt+40, yt+80, Color.GREEN));


    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public int getWidth() { return WIDTH; }
    public int getHeight() { return HEIGHT; }
}
