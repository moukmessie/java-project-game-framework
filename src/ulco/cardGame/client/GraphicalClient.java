package ulco.cardGame.client;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ulco.cardGame.common.interfaces.Game;
import ulco.cardGame.server.SocketServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GraphicalClient extends Application {

    private Socket socket;

    private Object answer;
    private String username;

    // enable to interact with Text using Socket part
    private Text gameState;
    private Button setBtn;

    public static void main(String[] args) {
        launch(args);
    }

    private void socketExchange() {

        try {
            do {
                // Read and display the response message sent by server application
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                answer = ois.readObject();

                // Get current Text
                String text = gameState.getText();

                // depending of object type, we can manage its data
                if (answer instanceof String) {
                    gameState.setText(text + "\n" + answer.toString());
                }
                if (answer instanceof Game) {
                    gameState.setText(text + "\n" + answer.getClass());
                }

            } while (!answer.equals("END"));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) {

        // Default game state
        gameState = new Text();
        gameState.setText("Waiting connection");

        //set scene and title
        Scene scene = new Scene(new Group(), 500, 250);
        stage.setTitle("Socket Serveur connection example");

        //instantiate TextFields
        TextField nameTxt = new TextField();
        nameTxt.setFont(Font.font("Arial", FontWeight.BOLD, 26));

        nameTxt.setText("Select your username");

        //instantiate buttons
        setBtn = new Button("Connect");

        setBtn.setText("Click me!");
        setBtn.setOnAction((event) -> {

            try {

                // Create a connection to the server socket on the server application
                InetAddress host = InetAddress.getLocalHost();

                // get username using name Txt
                username = nameTxt.getText();

                // Create new socket
                socket = new Socket(host.getHostName(), SocketServer.PORT);

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(username);

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                gameState.setText("Waiting for connection...");

                answer = ois.readObject();

                System.out.println(answer instanceof Game);
                // depending of answer type, launch Socket interaction, or enable new username submission
                if (answer instanceof Game) {
                    // Important ! Run new thread which manages server interaction (avoid freeze of JavaFX window)
                    new Thread(() -> {
                        socketExchange();
                    }).start();
                    // disable click on button
                    setBtn.setDisable(true);
                }
                else
                    gameState.setText(answer.toString());

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        //instantiate gridpane, setVgap,Hgap,padding and add children
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Name: "), 0, 0);
        grid.add(nameTxt, 1, 0);
        grid.add(new Label("Button: "), 0, 1);
        grid.add(setBtn, 1, 1);
        grid.add(new Label("Game state: "), 0, 2);
        grid.add(gameState, 1, 2);

        //add gridpane to group
        Group root = (Group) scene.getRoot();
        root.getChildren().add(grid);

        //set scene and show stage
        stage.setScene(scene);
        stage.show();
    }
}
