package ulco.cardGame.client;

import ulco.cardGame.common.games.components.Card;
import ulco.cardGame.common.interfaces.Board;
import ulco.cardGame.common.interfaces.Game;
import ulco.cardGame.server.SocketServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) {

        // Current use full variables
        try {

            // Create a connection to the server socket on the server application
            InetAddress host = InetAddress.getLocalHost();
            Socket socket;

            Object answer;
            String username;
            Game game=null;

            do {

                socket = new Socket(host.getHostName(), SocketServer.PORT);

                Scanner scanner=new Scanner(System.in);

                System.out.println("Please select your username");
                username = scanner.nextLine();

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());  //lecture puis envoie du username au serveur
                oos.writeObject(username);

                // Read and display the response message sent by server application
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                answer = ois.readObject();
                System.out.println(answer.toString());

            } while (!(answer instanceof Game));

            ((Game)answer).displayState();
            game=(Game)answer;

            Board board;

            do {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                answer = ois.readObject();

                if (answer instanceof Game) {
                    game = (Game)answer;
                    game.displayState();
                }

                if (answer instanceof Board) {
                    board = (Board)answer;
                    board.displayState();
                }

                if (answer instanceof String) {
                    System.out.println(answer);

                    if (((String) answer).contains("you have to play")){
                        game.getCurrentPlayer(username).play(socket);
                    }
                   /* if (((String) answer).contains("please select a valid Coin to play (coin color)")){
                        game.getCurrentPlayer(username).play(socket);
                    }*/
                }

            }while (!answer.equals("END"));

            // close the socket instance connection
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
