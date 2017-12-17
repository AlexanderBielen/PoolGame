package poolgame.models;

import poolgame.helpers.Model;

public class Cue implements Model  {
    // Properties
    private double x;
    private double y;
    private double alpha;

    private double cueBallX;
    private double cueBallY;

    private double length = 350;
    private double width = 10;

    private boolean isVisible;

    //Source of the cue image: https://pngtree.com/freepng/hand-painted-cue_909840.html
    private String imageLocation = "/poolgame/img/cue.png";

    private double pullBack = 5;

    /**
     * New Cue instance
     */
    public Cue() {
        x = y = 0;
        isVisible = true;
        pullBack = 155;
    }

    /**
     * Sets the top left x and y coordinate
     *
     * @param x top left x coordinate
     * @param y top left y coordinate
     */
    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
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
     * Gets cueBallX
     *
     * @return value of cueBallX
     */
    public double getCueBallX() {
        return cueBallX;
    }

    /**
     * Sets cueBallX
     *
     * @param cueBallX the cueBallX to set
     */
    public void setCueBallX(double cueBallX) {
        this.cueBallX = cueBallX;
    }

    /**
     * Gets cueBallY
     *
     * @return value of cueBallY
     */
    public double getCueBallY() {
        return cueBallY;
    }

    /**
     * Sets cueBallY
     *
     * @param cueBallY the cueBallY to set
     */
    public void setCueBallY(double cueBallY) {
        this.cueBallY = cueBallY;
    }

    /**
     * Gets length
     *
     * @return value of length
     */
    public double getLength() {
        return length;
    }

    /**
     * Sets length
     *
     * @param length the length to set
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Gets width
     *
     * @return value of width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets width
     *
     * @param width the width to set
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Gets isVisible
     *
     * @return value of isVisible
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Sets isVisible
     *
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /**
     * Gets imageLocation
     *
     * @return value of imageLocation
     */
    public String getImageLocation() {
        return imageLocation;
    }

    /**
     * Sets imageLocation
     *
     * @param imageLocation the imageLocation to set
     */
    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    /**
     * Gets pullBack
     *
     * @return value of pullBack
     */
    public double getPullBack() {
        return pullBack;
    }

    /**
     * Sets pullBack
     *
     * @param pullBack the pullBack to set
     */
    public void setPullBack(double pullBack) {
        if(pullBack > 155) { pullBack = 155; }
        if(pullBack < 55) { pullBack = 55; }
        this.pullBack = pullBack;
    }

}
