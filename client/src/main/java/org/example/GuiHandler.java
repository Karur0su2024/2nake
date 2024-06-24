package org.example;

import org.example.gui.*;

public class GuiHandler {

    private Sidebar sidebar;
    private MainMenu mainMenu;
    private GameWindow gameWindow;
    private SettingsFrame settingsFrame;
    private JoinServerFrame joinServerFrame;
    private GameOverScreen gameOverScreen;

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

    public void setSettingsFrame(SettingsFrame settingsFrame) {
        this.settingsFrame = settingsFrame;
    }

    public SettingsFrame getSettingsFrame() {
        return settingsFrame;
    }

    public void setJoinServerFrame(JoinServerFrame joinServerFrame) {
        this.joinServerFrame = joinServerFrame;
    }

    public JoinServerFrame getJoinServerFrame() {
        return joinServerFrame;
    }


    public void closeGameFrame(){
        this.gameWindow.dispose();
        this.gameWindow = null;
        this.settingsFrame.setVisible(true);
    }

    public void closeGameWindowToMainMenu(){
        if(gameOverScreen != null){
            gameOverScreen.dispose();
            gameOverScreen = null;
        }
        this.gameWindow.dispose();
        this.gameWindow = null;
        this.mainMenu.setVisible(true);
    }

    public GameOverScreen getGameOverScreen() {
        return gameOverScreen;
    }

    public void setGameOverScreen(GameOverScreen gameOverScreen) {
        this.gameOverScreen = gameOverScreen;
    }
}
