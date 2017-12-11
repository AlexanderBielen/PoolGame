package poolgame.models;

import poolgame.helpers.Model;

public class Cue implements Model  {
    //https://pngtree.com/freepng/hand-painted-cue_909840.html

    private double x;
    private double y;
    private double alpha;

    private double cueBallX;
    private double cueBallY;

    private double length = 100;
    private double width = 5;

    private boolean isVisible;

    public Cue() {
        x = y = 0;
        isVisible = true;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }
    public void setCueBallX(double x) {
        this.cueBallX = x;
    }
    public void setCueBallY(double y) {
        this.cueBallY = y;
    }

    public double getX() {
        return x-(length/2);
    }

    public double getY() {
        return y-(width/2);
    }

    public double getWidth() { return width; }
    public double getLength() { return length; }
    public double getAlpha() {
        return alpha;
    }
    public double getCueBallX() { return cueBallX; }
    public double getCueBallY() { return cueBallY; }

    public void isVisible(boolean isVis) {
        this.isVisible = isVis;
    }
    public boolean isVisible() { return isVisible; }
}
