package poolgame.models;

import javafx.scene.paint.Color;
import poolgame.helpers.Model;

import java.util.ArrayList;
import java.util.Arrays;

public class Table implements Model {
    public static int HEIGHT = 500;
    public static int WIDTH = 392;

    private ArrayList<Ball> balls;
    private int xt;
    private int yt;

    private Color[] colors = {Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW, Color.ORANGE, Color.BROWN, Color.GRAY, Color.PURPLE, Color.PINK, Color.LIGHTSALMON, Color.DARKGOLDENROD, Color.STEELBLUE, Color.CYAN, Color.CHOCOLATE};
    private ArrayList<Color> colorPool;

    private int[] rack = {1,2,3,4,5};
    private int[][] colorScheme = {{0}, {0,0}, {0,1,0}, {0,0,0,0}, {0,0,0,0,0}}; // 0 = random color from colorpool; 1 = black

    public Table() {
        xt = WIDTH / 2;
        yt = HEIGHT / 3 * 2;
        initialize();
    }

    private void initialize() {
        balls = new ArrayList<>();
        colorPool = new ArrayList<>();
        colorPool.addAll(Arrays.asList(colors));
        balls.add(new Ball(xt, yt - 200, Color.WHITE, true));

        int ballRadius = balls.get(0).getRadius();

        //dynamically generate rack
        for(int i = 0; i < rack.length; i ++) {
            for(int j = 0; j < rack[i]; j ++) {
                Color ballColor = Color.BLACK;
                if(colorScheme[i][j] == 0) {
                    int r = (int)(Math.random() * colorPool.size());
                    ballColor = colorPool.get(r);
                    colorPool.remove(r);
                }
                balls.add(new Ball(xt + ballRadius - (i*ballRadius) + (j * ballRadius * 2), yt + (i * ballRadius * 2 - 2), ballColor));
            }
        }
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }
}
