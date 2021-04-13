/*package ulco.cardGame.server;

import ulco.cardGame.common.games.CardGame;
import ulco.cardGame.common.games.PokerGame;
import ulco.cardGame.common.interfaces.Game;
import ulco.cardGame.common.interfaces.Player;
import ulco.cardGame.common.players.CardPlayer;
import ulco.cardGame.common.players.PokerPlayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

/**
 * Server Game management with use of Singleton instance creation
 */
/*
public class GameServer {

    public static void main(String[] args) {

        Game game = null;
        Constructor playerConstructor = null;

        // Get expected game name of user
        System.out.println("Which game do you want to play ?");
        Scanner scanner = new Scanner(System.in);

        String gameName = scanner.nextLine();

        try {
            if (gameName.equals("Battle")) {
                // Need to specify your current cardGame.txt file
                game = new CardGame("Battle", 3, "resources/games/cardGame.txt");
                playerConstructor = CardPlayer.class.getConstructor(String.class);
            }
            else if (gameName.equals("Poker")) {
                game = new PokerGame("Texas Poker", 3, 3, "resources/games/pokerGame.txt");
                playerConstructor = PokerPlayer.class.getConstructor(String.class);
            }

            if (game == null) {
                System.out.println("Unknown Game...");
                return;
            }

            // add each player until game is ready (or number of max expected player is reached
            int userIndex = 0;

            do {
                // use of dynamic constructor (reflection concept) in order to instance expected user
                Player player = (Player) playerConstructor.newInstance("user" + userIndex);
                game.addPlayer(player);

                userIndex++;

            } while (!game.isStarted());

            // Game Loop
            // run the whole game using sockets
            Player winner = game.run();

            System.out.println("Winner of the game is " + winner + " !!");

            // remove all players from the game
            game.removePlayers();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

*/