package ulco.cardGame.server;

import ulco.cardGame.common.games.CardGame;
import ulco.cardGame.common.games.components.PokerGame;
import ulco.cardGame.common.players.CardPlayer;
import ulco.cardGame.common.interfaces.Game;
import ulco.cardGame.common.interfaces.Player;

import java.util.Scanner;

/**
 * Server Game management
 */
public class GameServer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game=null;
        Integer maxPlayer = 3;
        System.out.println("Which game do you want to play?");

        String gameName = scanner.nextLine();
// add each player until game is ready (or number of max expected player is reached
        int userIndex = 0;
       if (gameName.equals("Poker")){
           game = new PokerGame("Poker Game",maxPlayer,"resources/games/pokerGame.txt",5);


       }else if (gameName.equals("Battle")) {
           game = new CardGame("Battle", 3, "resources/games/cardGame.txt");
       }


        do {
            Player player = new CardPlayer("user" + userIndex);
            game.addPlayer(player);
           // game.displayState();

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

