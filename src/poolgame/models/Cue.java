package poolgame.models;

import poolgame.helpers.Model;

public class Cue implements Model  {
    //https://pngtree.com/freepng/hand-painted-cue_909840.html

    private double x;
    private double y;

    public Cue() {
        x = y = 0;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
