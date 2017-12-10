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
            this.dx = Math.cos(alpha) * velocity;
            this.dy = Math.sin(alpha) * velocity;
            if(calculateCollision(dx, dy)) {
                this.dx = Math.cos(alpha) * velocity;
                this.dy = Math.sin(alpha) * velocity;
            }

            double dV = 0.2 * 9.81 * (velocity/velocity+0.2)*0.01;
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

    private boolean calculateCollision(double dx, double dy) {
        boolean recalculate = false;
        if(Math.signum(dx) < 0 && (x+dx) < 20) {
             // Making sure that the ball hits the edge
            if(x <= 20) {
                alpha = Math.PI - alpha;
                recalculate = true;
            } else {
                this.dx = 20-x;
                //this.dy = dy-(dx-this.dx);
            }
        } else if(Math.signum(dx) > 0 && (x+dx) > TableView.WIDTH-40) {
            if(x >= TableView.WIDTH-40) {
                alpha = Math.PI - alpha;
                recalculate = true;
            } else {
                this.dx = TableView.WIDTH-40 - x;
                //this.dy = dy-(dx-this.dx);
            }
        }

        if(Math.signum(dy) < 0 && (y+dy) < 20) {
            if(y <= 20) {
                alpha = Math.PI + (Math.PI - alpha);
                recalculate = true;
            } else {
                this.dy = 20-y;
                //this.dx = dx-(dy-this.dx);
            }
        }
        else if(Math.signum(dy) > 0 && (y+dy) > TableView.HEIGHT - 40) {
            if(y >= TableView.HEIGHT-40) {
                alpha = Math.PI + (Math.PI - alpha);
                recalculate = true;
            } else {
                this.dy = TableView.HEIGHT-40 - y;
                //this.dx = dx-(dy-this.dy);
            }
        }

        return recalculate;
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

