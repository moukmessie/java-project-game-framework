package ulco.cardGame.common.games.components;

import ulco.cardGame.common.games.BoardGame;
import ulco.cardGame.common.games.boards.PokerBoard;
import ulco.cardGame.common.interfaces.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PokerGame extends BoardGame {

    private List<Card>cards;
    private  List<Coin>coins;
    private Integer maxRounds;
    private Integer numberOfRounds;

    /**
     * Enable constructor of pokerGame
     * - Name of the game
     * - Maximum number of players of the Game
     * - Filename with all required information to load the Game
     *
     * @param name
     * @param maxPlayers
     * @param filename
     */
    public PokerGame(String name, Integer maxPlayers, String filename) {
        super(name, maxPlayers, filename);
        this.board = new PokerBoard();
    }

    @Override
    public void initialize(String filename) {

            this.cards = new ArrayList<>();
            this.coins = new ArrayList<>();
            this.numberOfRounds = 0;

            // Here initialize the list of Cards
            try {
                File cardFile = new File(filename);
                Scanner myReader = new Scanner(cardFile);

                while (myReader.hasNextLine()) {

                    String data = myReader.nextLine();
                    String[] dataValues = data.split(";");

                    // get Card value
                    //Integer value = Integer.valueOf(dataValues[1]);
                   // this.cards.add(new Card(dataValues[0], value, true));
                if(dataValues[0].equals("Card")){
                    cards.add(new Card(dataValues[1], Integer.parseInt(dataValues[2]),true));
                }else if (dataValues[0].equals("Coin")){

                    coins.add(new Coin(dataValues[1],Integer.parseInt(dataValues[2])));
                }

                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }


    @Override
    public Player run() {
        return null;
    }

    @Override
    public boolean end() {
        if(numberOfRounds.equals(maxRounds)){
            endGame=true;
            return true;

        }
       return  false;
    }
}
