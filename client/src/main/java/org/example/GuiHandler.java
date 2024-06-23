package org.example;

import org.example.gui.GameWindow;
import org.example.gui.MainMenu;
import org.example.gui.Sidebar;

public class GuiHandler {

    private Sidebar sidebar;
    private MainMenu mainMenu;
    private GameWindow gameWindow;


    public GuiHandler(){

    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public void toggleMainMenu(){
        mainMenu.setVisible(!mainMenu.isVisible());
    }

    public void setSidebar(Sidebar sidebar) {
        this.sidebar = sidebar;
    }

    public Sidebar getSidebar() {
        return sidebar;
    }

    public void setGameFrame(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public GameWindow getGameFrame() {
        return gameWindow;
    }

    public void closeGameFrame(){
        this.gameWindow.dispose();
        this.gameWindow = null;
    }
}
