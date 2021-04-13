package ulco.cardGame.server;

import ulco.cardGame.common.games.CardGame;
import ulco.cardGame.common.interfaces.Game;
import ulco.cardGame.common.interfaces.Player;
import ulco.cardGame.common.players.CardPlayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Server Game management with use of Singleton instance creation
 */
public class SocketServer {

    private ServerSocket server;
    protected Game game; // game will be instantiate later
    protected Map<Player, Socket> playerSockets;
    protected Constructor playerConstructor;

    public static final int PORT = 7777;

    private SocketServer() {
        try {
            server = new ServerSocket(PORT);
            playerSockets = new HashMap<>();
            playerConstructor = null;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        server.run();
    }

    private void run() {

        try {

            // Need to specify your current cardGame.txt file
            game = new CardGame("Battle", 3, "resources/games/cardGame.txt");

            // Game Loop
            System.out.println("Waiting for new player for " + game.toString());

            // add each player until game is ready (or number of max expected player is reached)
            // Waiting for the socket entrance
            do {

                Socket socket = server.accept();

                // read socket Data
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                String playerUsername = (String) ois.readObject();

                //write socket
                ObjectOutputStream sendData = new ObjectOutputStream(socket.getOutputStream());

                // Create player instance
                Player player = new CardPlayer(playerUsername);

                // TODO : Add player in game
                //player is adding
               if (game.getPlayers().size()<game.maxNumberOfPlayers()){

                   game.addPlayer(player,socket);
                   ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                   oos.writeObject(game);

                   // Tell to other players that new player is connected
                   for (Socket playerSocket : playerSockets.values()) {

                       ObjectOutputStream playerOos = new ObjectOutputStream(playerSocket.getOutputStream());
                       playerOos.writeObject("Now connected: " + player.getName());
                   }

                   // Store player's socket in dictionary (Map)
                   playerSockets.put(player, socket);

                   System.out.println(player.getName() + " is now connected...");

               }else {
                 //  System.out.println("max number player error connection !");
                   sendData.writeObject("maxNumber");
               }


            } while (!game.isStarted());

            // run the whole game using sockets
            // TODO : Player gameWinner = game.run(playerSockets);

            // Tells to player that server will be closed (just as example)
            for (Socket playerSocket : playerSockets.values()) {
                ObjectOutputStream playerOos = new ObjectOutputStream(playerSocket.getOutputStream());
                playerOos.writeObject("END");
            }

            // Close each socket when game is finished
            for (Socket socket : playerSockets.values()) {
                socket.close();
            }

            game.removePlayers();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

