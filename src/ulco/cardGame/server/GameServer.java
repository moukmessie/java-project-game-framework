package ulco.cardGame.server;

import ulco.cardGame.common.games.CardGame;
import ulco.cardGame.common.interfaces.Game;

/**
 * Server Game management with use of Singleton instance creation
 */
public class GameServer {

    public static void main(String[] args) {

        System.out.println("Here the created games will be launched!");
        // TODO
        // - Create Game Instance
        Game game = new CardGame("Battle",3,"resources/games/cardGame.txt");

        // - Add players to game
        // - Run the Game loop
        // - Display the winner player
    }
}

