package poolgame.models;

import poolgame.helpers.Model;

public class Pocket implements Model {
    //Properties
    private double x;
    private double y;

    private double radius;

    /**
     * New instance of Pocket
     *
     * @param x coordinate of the center pocket
     * @param y coordinate of the center of the pocket
     * @param radius of the pocket
     */
    public Pocket(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public boolean isInPocket(Ball ball) {
        double distance = Math.sqrt(Math.pow(ball.getX() - x, 2) + Math.pow(ball.getY() - y, 2));
        if(distance <= ball.getRadius() + radius) {
            return true;
        }
        return false;
    }

    /**
     * Gets x
     *
     * @return value of x
     */
    public double getX() {
        return x;
    }

    /**
     * Sets x
     *
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets y
     *
     * @return value of y
     */
    public double getY() {
        return y;
    }

    /**
     * Sets y
     *
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets radius
     *
     * @return value of radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets radius
     *
     * @param radius the radius to set
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }
}
