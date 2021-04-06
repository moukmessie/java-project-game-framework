package ulco.cardGame.server;

import ulco.cardGame.common.games.CardGame;
import ulco.cardGame.common.players.CardPlayer;
import ulco.cardGame.common.interfaces.Game;
import ulco.cardGame.common.interfaces.Player;

/**
 * Server Game management
 */
public class GameServer {

    public static void main(String[] args) {
// Need to specify your current cardGame.txt file
        Game game = new CardGame("Battle", 3, "resources/games/cardGame.txt");

        // add each player until game is ready (or number of max expected player is reached
        int userIndex = 0;

        do {
            Player player = new CardPlayer("user" + userIndex);
            game.addPlayer(player);
            game.displayState();

            userIndex++;

        } while (!game.isStarted());

        // Game Loop
        // run the whole game using sockets
        Player winner = game.run();

        System.out.println("Winner of the game is " + winner + " !!");

        // remove all players from the game
        game.removePlayers();
    }
}

