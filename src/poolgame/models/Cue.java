package poolgame.models;

import poolgame.helpers.Model;

public class Cue implements Model  {
    //https://pngtree.com/freepng/hand-painted-cue_909840.html

    private double x;
    private double y;
    private double alpha;

    private double cueBallX;
    private double cueBallY;

    private double length = 350;
    private double width = 10;

    private boolean isVisible;

    private String imageLocation = "/poolgame/img/cue.png";

    private double pullBack = 5;

    public Cue() {
        x = y = 0;
        isVisible = true;
        pullBack = 155;
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
    public void setPullBack(double pb) {
        if(pb > 255 || pb < 155 ) { return; }
        this. pullBack = pb;
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

    public String getImageLocation() {
        return imageLocation;
    }

    public double getPullBack() {
        return pullBack;
    }

}
