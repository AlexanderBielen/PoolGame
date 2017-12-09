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

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public int getBeginX() { return beginX; }

    public int getBeginY() { return beginY; }

    public String getText() { return text; }

    public boolean isActive() { return isActive; }

    public void setActive(boolean state) { this.isActive = state; }

    public Navigation getClickLocation() { return clickLocation; }
}
