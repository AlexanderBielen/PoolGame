package poolgame.models;


import javafx.scene.paint.Color;
import poolgame.helpers.Model;
import poolgame.views.TableView;

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

    public Ball(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        dx = dy = 0;
        alpha = 0;
        velocity = 0;
        isCueBall = false;
    }

    public Ball(int x, int y, Color color, boolean isCueBall) {
        this.x = x;
        this.y = y;
        this.color = color;
        dx = dy = 0;
        alpha = 0;
        velocity = 0;
        this.isCueBall = isCueBall;
    }

    public void tick() {
        if(velocity > 0) {
            dx = Math.cos(alpha) * velocity;
            dy = Math.sin(alpha) * velocity;
            calculateCollision(dx, dy);
            double dV = 0.5 * 9.81 * (velocity/velocity+0.5)*0.01;
//            System.out.println("Delta v " + dV);
//            System.out.println(dx + " " + dy + " || " + velocity + " " + alpha);

            velocity -= dV;
            x += dx;
            y += dy;
            if(velocity < 0) {
                velocity = 0;
            }
        }
    }

    private void calculateCollision(double dx, double dy) {
        if(Math.signum(dx) < 0 && (x+=dx) < 30) {
            alpha = Math.PI - alpha;
             // Making sure that the ball hits the edge
            velocity = 0;
            this.dx = 0;
            this.dy = 0;
        } else if(Math.signum(dx) > 0 && (x+=dx) > TableView.WIDTH-30) {
            alpha = Math.PI - alpha;
            velocity = 0;
            this.dx = 0;
            this.dy = 0;
        }
        if(Math.signum(dy) < 0 && (y+=dy) < 30) {
            alpha = Math.PI + (Math.PI - alpha);
            velocity = 0;
            this.dx = 0;
            this.dy = 0;
        }
        else if(Math.signum(dy) > 0 && (y+=dy) > TableView.HEIGHT - 30) {
            alpha = Math.PI + (Math.PI - alpha);
            velocity = 0;
            this.dx = 0;
            this.dy = 0;
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
}

