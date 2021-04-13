package ulco.cardGame.client;

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

            Scanner scanner = new Scanner(System.in);

            System.out.println("Please select your username");
            username = scanner.nextLine();

            socket = new Socket(host.getHostName(), SocketServer.PORT);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(username);

            do {
                // Read and display the response message sent by server application
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                answer = ois.readObject();

                // depending of object type, we can manage its data
                if (answer instanceof String){
                    //System.out.println(answer.toString());
                    if ((answer.toString()).equals("maxNumber")){

                    }
                }

                if (answer instanceof Game)
                    ((Game)answer).displayState();

            } while (!answer.equals("END"));

            // close the socket instance connection
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
