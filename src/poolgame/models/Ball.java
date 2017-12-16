package poolgame.models;

import javafx.scene.paint.Color;
import poolgame.helpers.Model;

public class Ball implements Model {

    private int radius = 10;

    private Color color;

    private double x;
    private double y;
    private double alpha;
    private double velocity;
    private double dx;
    private double dy;

    private boolean isCueBall;

    Ball(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        dx = dy = 0;
        alpha = 0;
        velocity = 0;
        isCueBall = false;
    }

    Ball(int x, int y, Color color, boolean isCueBall) {
        this.x = x;
        this.y = y;
        this.color = color;
        dx = dy = 0;
        alpha = 0;
        velocity = 0;
        this.isCueBall = isCueBall;
    }

    public void calculateTrajectory() {
        if(velocity > 0) {
            this.dx = Math.cos(alpha) * velocity;
            this.dy = Math.sin(alpha) * velocity;
        }
    }

    public void tick() {
        double dV = 0.2 * 9.81 * (velocity/velocity+0.2)*0.01;
        velocity -= dV;
        x += dx;
        y += dy;
        if(velocity < 0) {
            velocity = 0;
        }
    }

    public int getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }

    public double getCenterX() {
        return x;
    }

    public double getCenterY() {
        return y;
    }

    public double getVelocity() {
        return velocity;
    }

    public boolean isCueBall() {
        return isCueBall;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getAlpha() { return alpha; }

    public double getDx() { return dx; }

    public double getDy() { return dy; }

    public void setDx(double dx) {
        this.dx = dx;
    }
    public void setDy(double dy) {
        this.dy = dy;
    }
}

