package poolgame.models;

import poolgame.helpers.Model;
import poolgame.helpers.Navigation;

import java.util.ArrayList;
import java.util.Iterator;

public class Menu implements Model {
    // Properties
    public static int HEIGHT = Table.HEIGHT;
    public static int WIDTH = Table.WIDTH;

    private ArrayList<MenuButton> menuButtonList;
    public Menu() {
        Initialize();
    }

    /**
     * Initializes the Menu
     * Generates the buttons and adds it to the button list
     */
    private void Initialize() {
        menuButtonList = new ArrayList<>();
        MenuButton beginGame = new MenuButton(50, 250, 1, 1, "Start game", Navigation.IN_GAME);
        MenuButton exit = new MenuButton(50, 250, 1, 2, "Exit game", Navigation.EXIT);

        menuButtonList.add(beginGame);
        menuButtonList.add(exit);
    }

    /**
     * Gets menuButtonList
     *
     * @return value of menuButtonList
     */
    public ArrayList<MenuButton> getMenuButtonList() {
        return menuButtonList;
    }

    /**
     * Sets menuButtonList
     *
     * @param menuButtonList the menuButtonList to set
     */
    public void setMenuButtonList(ArrayList<MenuButton> menuButtonList) {
        this.menuButtonList = menuButtonList;
    }
}
