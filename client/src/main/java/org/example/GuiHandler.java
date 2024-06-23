package org.example;

import org.example.gui.GameFrame;
import org.example.gui.MainMenu;
import org.example.gui.Sidebar;

public class GuiHandler {

    private Sidebar sidebar;
    private MainMenu mainMenu;
    private GameFrame gameFrame;


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

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public void closeGameFrame(){
        this.gameFrame.dispose();
        this.gameFrame = null;
    }
}
