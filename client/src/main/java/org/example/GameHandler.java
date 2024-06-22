package org.example;

/**
 * Rozhraní pro správu hry, které definuje metody pro inicializaci hry, odesílání
 * akcí hráčů, přijímání stavu hry a aktualizaci hry.
 */
public interface GameHandler {

    /**
     * Inicializuje hru s konkrétní instancí {@link Game}.
     *
     * @param game Instance hry, která má být inicializována
     */
    void initializeGame(Game game);

    /**
     * Odesílá akci určenou konkrétnímu hráči identifikovanému indexem hráče.
     *
     * @param player Index hráče, který vykonává akci
     * @param key    Kód klávesy reprezentující akci hráče
     */
    void sendPlayerAction(int player, int key);

    /**
     * Přijímá aktuální stav hry ve formě řetězce.
     *
     * @param gameState Řetězcová reprezentace stavu hry
     */
    void receiveGameState(String gameState);

    /**
     * Aktualizuje stav hry na straně klienta.
     */
    void updateGame();
}
