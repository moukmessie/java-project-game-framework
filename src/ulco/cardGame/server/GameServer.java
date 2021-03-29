package ulco.cardGame.server;

import ulco.cardGame.common.games.CardGame;
import ulco.cardGame.common.games.components.components.CardPlayer;
import ulco.cardGame.common.interfaces.Game;
import ulco.cardGame.common.interfaces.Player;

/**
 * Server Game management with use of Singleton instance creation
 */
public class GameServer {

    public static void main(String[] args) {

        System.out.println("Here the created games will be launched!");

        // - Create Game Instance
        Game game = new CardGame("Battle",3,"resources/games/cardGame.txt");

        // - Add players to game
        Player player1 = new CardPlayer("user1");
        Player player2 = new CardPlayer("user2");

        // - Run the Game loop
        game.addPlayer(player1);
        game.addPlayer(player2);

        // - Display the winner player

        game.displayState();
    }
}

