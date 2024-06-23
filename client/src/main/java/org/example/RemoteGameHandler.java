package org.example;

import org.example.objects.Snake;

/**
 * Třída RemoteGameHandler implementující rozhraní GameHandler.
 * Spravuje odesílání akcí hráčů a aktualizaci stavu hry pro vzdáleného hráče přes síť.
 */
public class RemoteGameHandler implements GameHandler {

    private final GameClient gameClient;
    private Game game;

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
     * @param player číslo hráče
     * @param key    kód stisknuté klávesy
     */
    @Override
    public void sendPlayerAction(int player, int key) {
        gameClient.sendMessage(player, key);
    }

    /**
     * Přijímá stav hry ve formátu řetězce a provádí odpovídající akce na základě přijatého příkazu.
     * Aktualizuje směr hada hráče na základě příkazu "move".
     *
     * @param message zpráva obsahující stav hry ve formátu řetězce
     */
    @Override
    public void receiveGameState(String message) {
        String[] parts = message.split(" ", 3);
        String command = parts[0];
        int playerId = Integer.parseInt(parts[1]);
        char parameter = 'A';
        if (parts.length > 2) {
            parameter = parts[2].charAt(0);
        }

        switch (command) {
            case "move":
                handleMoveCommand(playerId, parameter);
                break;
            default:
                break;
        }
    }

    /**
     * Zpracovává příkaz "move" pro změnu směru hada hráče v rámci aktuální hry.
     *
     * @param playerId  číslo hráče
     * @param direction nový směr hada
     */
    private void handleMoveCommand(int playerId, char direction) {
        if (game != null && game.getSnakes().length > playerId) {
            Snake[] snakes = game.getSnakes();
            snakes[playerId].setDirection(direction);
            game.setSnakes(snakes);
        }
    }

    /**
     * Aktualizace stavu hry není potřeba pro vzdáleného hráče, provádí se přes síť.
     */
    @Override
    public void updateGame() {
        // Aktualizace hry není potřeba pro vzdáleného hráče, provádí se přes síť.
    }
}
