package poolgame.models;

import javafx.scene.paint.Color;
import poolgame.helpers.Model;

import java.util.ArrayList;
import java.util.Arrays;

public class Table implements Model {
    // Properties
    public static int HEIGHT = 500;
    public static int WIDTH = 392;

    private ArrayList<Ball> balls;
    private ArrayList<Pocket> pockets;
    private int xt;
    private int yt;

    private int pocketedBalls;

    private Color[] colors = {Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW, Color.ORANGE, Color.BROWN, Color.GRAY, Color.PURPLE, Color.PINK, Color.LIGHTSALMON, Color.DARKGOLDENROD, Color.STEELBLUE, Color.CYAN, Color.CHOCOLATE};
    private ArrayList<Color> colorPool;

    private int[] rack = {1,2};
    private int[][] colorScheme = {{0}, {0,0}, {0,1,0}, {0,0,0,0}, {0,0,0,0,0}}; // 0 = random color from colorpool; 1 = black

    /**
     * New instance of Table
     */
    public Table() {
        xt = WIDTH / 2;
        yt = HEIGHT / 3 * 2;
        pocketedBalls = 0;
        initialize();
    }

    /**
     * Initializes the table
     * Generates the balls in the correct position
     * Adds pockets
     */
    private void initialize() {
        balls = new ArrayList<>();
        pockets = new ArrayList<>();
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
                balls.add(new Ball(xt - (i*ballRadius) + (j * ballRadius * 2), yt + (i * ballRadius * 2 - 2), ballColor));
            }
        }

        pockets.add(new Pocket(20, 20, 16));
        pockets.add(new Pocket(Table.WIDTH - 20, 20, 16));
        pockets.add(new Pocket(20, Table.HEIGHT - 20, 16));
        pockets.add(new Pocket(Table.WIDTH - 20, Table.HEIGHT - 20, 16));

        pockets.add(new Pocket(20, Table.HEIGHT / 2, 16));
        pockets.add(new Pocket(Table.WIDTH - 20, Table.HEIGHT / 2, 16));
    }

    /**
     * Gets balls
     *
     * @return value of balls
     */
    public ArrayList<Ball> getBalls() {
        return balls;
    }

    /**
     * Gets pockets
     *
     * @return value of pockets
     */
    public ArrayList<Pocket> getPockets() {
        return pockets;
    }

    /**
     * Gets pocketedBalls
     *
     * @return value of pocketedBalls
     */
    public int getPocketedBalls() {
        return pocketedBalls;
    }

    /**
     * Sets pocketedBalls
     *
     * @param pocketedBalls the pocketedBalls to set
     */
    public void setPocketedBalls(int pocketedBalls) {
        this.pocketedBalls = pocketedBalls;
    }
}
