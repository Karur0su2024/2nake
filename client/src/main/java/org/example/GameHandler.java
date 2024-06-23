package org.example;

/**
 * Rozhraní pro správu hry, které definuje metody pro inicializaci hry, odesílání
 * akcí hráčů, přijímání stavu hry a aktualizaci hry.
 */
public interface GameHandler {

    /**
     * Inicializuje hru s konkrétní instancí {@link Game}.
     *
     */
    void initializeGame();

    /**
     * Odesílá akci určenou konkrétnímu hráči identifikovanému indexem hráče.
     *
     * @param key    Kód klávesy reprezentující akci hráče
     */
    void sendPlayerAction(int key);

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

    void resetTime();
}
