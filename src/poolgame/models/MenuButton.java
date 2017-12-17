package poolgame.models;

import poolgame.helpers.Model;
import poolgame.helpers.Navigation;

public class MenuButton implements Model {
    private int height;
    private int width;

    private int beginX;
    private int beginY;

    private String text;

    private boolean isActive;

    private Navigation clickLocation;

    MenuButton(int height, int width, int beginX, int beginY, String text, Navigation clickLocation) {
        this.height = height;
        this.width = width;

        this.beginX = beginX;
        this.beginY = beginY;

        this.text = text;

        this.clickLocation = clickLocation;
    }

    public double getXPosition() {
        return ((beginX * Menu.WIDTH )/ 2 ) - width/2;
    }

    public double getYPosition() {
        return beginY * Menu.HEIGHT / 7;
    }

    /**
     * Gets height
     *
     * @return value of height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets height
     *
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets width
     *
     * @return value of width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets width
     *
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets beginX
     *
     * @return value of beginX
     */
    public int getBeginX() {
        return beginX;
    }

    /**
     * Sets beginX
     *
     * @param beginX the beginX to set
     */
    public void setBeginX(int beginX) {
        this.beginX = beginX;
    }

    /**
     * Gets beginY
     *
     * @return value of beginY
     */
    public int getBeginY() {
        return beginY;
    }

    /**
     * Sets beginY
     *
     * @param beginY the beginY to set
     */
    public void setBeginY(int beginY) {
        this.beginY = beginY;
    }

    /**
     * Gets text
     *
     * @return value of text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text
     *
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets isActive
     *
     * @return value of isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets isActive
     *
     * @param active the active to set
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Gets clickLocation
     *
     * @return value of clickLocation
     */
    public Navigation getClickLocation() {
        return clickLocation;
    }

    /**
     * Sets clickLocation
     *
     * @param clickLocation the clickLocation to set
     */
    public void setClickLocation(Navigation clickLocation) {
        this.clickLocation = clickLocation;
    }
}
