package poolgame.models;

import javafx.scene.paint.Color;
import poolgame.helpers.Model;

public class Ball implements Model {
    // Properties
    private int radius = 8;

    private Color color;

    private double x;
    private double y;
    private double alpha;
    private double velocity;
    private double dx;
    private double dy;

    private boolean isCueBall;
    private boolean isPocketed;

    /**
     * New Ball instance that is not a cue ball
     *
     * @param x center x coordinate
     * @param y center y coordinate
     * @param color ball color
     */
    Ball(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        dx = dy = 0;
        alpha = 0;
        velocity = 0;
        isCueBall = false;
    }

    /**
     * New Ball instance with option to make it the cue ball
     *
     * @param x center x coordinate
     * @param y center y coordinate
     * @param color ball color
     * @param isCueBall boolean to set if cue ball
     */
    Ball(int x, int y, Color color, boolean isCueBall) {
        this.x = x;
        this.y = y;
        this.color = color;
        dx = dy = 0;
        alpha = 0;
        velocity = 0;
        this.isCueBall = isCueBall;
    }

    /**
     * Calculates the balls future move
     */
    public void calculateTrajectory() {
        if(velocity > 0) {
            this.dx = Math.cos(alpha) * velocity;
            this.dy = Math.sin(alpha) * velocity;
        }
    }

    /**
     * Executes the queued moves
     */
    public void tick() {
        if(isPocketed) return;
        double dV = 0.2 * 9.81 * (velocity/velocity+0.3)*0.01;
        velocity -= dV;
        x += dx;
        y += dy;
        if(velocity < 0) {
            velocity = 0;
        }
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
    public int getRadius() {

        return radius;
    }

    /**
     * Sets radius
     *
     * @param radius the radius to set
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Gets color
     *
     * @return value of color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets color
     *
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets alpha
     *
     * @return value of alpha
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * Sets alpha
     *
     * @param alpha the alpha to set
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    /**
     * Gets velocity
     *
     * @return value of velocity
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * Sets velocity
     *
     * @param velocity the velocity to set
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets dx
     *
     * @return value of dx
     */
    public double getDx() {
        return dx;
    }

    /**
     * Sets dx
     *
     * @param dx the dx to set
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Gets dy
     *
     * @return value of dy
     */
    public double getDy() {
        return dy;
    }

    /**
     * Sets dy
     *
     * @param dy the dy to set
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Gets isCueBall
     *
     * @return value of isCueBall
     */
    public boolean isCueBall() {
        return isCueBall;
    }

    /**
     * Sets isCueBall
     *
     * @param cueBall the cueBall to set
     */
    public void setCueBall(boolean cueBall) {
        isCueBall = cueBall;
    }

    /**
     * Gets isPocketed
     *
     * @return value of isPocketed
     */
    public boolean isPocketed() {
        return isPocketed;
    }

    /**
     * Sets isPocketed
     *
     * @param pocketed the pocketed to set
     */
    public void setPocketed(boolean pocketed) {
        isPocketed = pocketed;
    }

    /**
     * Pockets the ball
     */
    public void pocket(int pocketIndex) {
        velocity = 0;
        dx = 0;
        dy = 0;
        x = -100 + radius * pocketIndex * 2.5;
        y = -100;
        isPocketed = true;
    }
}

