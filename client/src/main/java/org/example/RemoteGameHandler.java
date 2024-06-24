package org.example;

import org.example.objects.Snake;

import java.awt.event.KeyEvent;

/**
 * Třída RemoteGameHandler implementující rozhraní GameHandler.
 * Spravuje odesílání akcí hráčů a aktualizaci stavu hry pro vzdáleného hráče přes síť.
 */
public class RemoteGameHandler implements GameHandler {

    private final GameClient gameClient;

    /**
     * Konstruktor pro vytvoření RemoteGameHandler s daným GameClientem.
     *
     * @param gameClient instance GameClienta pro síťovou komunikaci
     */
    public RemoteGameHandler(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    /**
     * Inicializuje hru s daným objektem Game.
     *
     */
    @Override
    public void initializeGame() {

    }

    /**
     * Odesílá akci hráče na základě stisku klávesy pomocí instance GameClienta.
     *
     * @param key    kód stisknuté klávesy
     */
    @Override
    public void sendPlayerAction(int key) {
        String dir = "";

        switch (key) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                dir = "R";
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                dir = "L";
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                dir = "U";
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                dir = "D";
                break;
        }
        if(!dir.isEmpty()){
            gameClient.sendMessage("move " + gameClient.getName() + " " + dir);
        }


    }

    /**
     * Přijímá stav hry ve formátu řetězce a provádí odpovídající akce na základě přijatého příkazu.
     * Aktualizuje směr hada hráče na základě příkazu "move".
     *
     * @param message zpráva obsahující stav hry ve formátu řetězce
     */
    @Override
    public void receiveGameState(String message) {

    }

    /**
     * Aktualizace stavu hry není potřeba pro vzdáleného hráče, provádí se přes síť.
     */
    @Override
    public void updateGame() {
        // Aktualizace hry není potřeba pro vzdáleného hráče, provádí se přes síť.
    }

    @Override
    public void resetTime() {

    }
}
