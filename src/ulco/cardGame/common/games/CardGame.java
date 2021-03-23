package ulco.cardGame.common.games;

import ulco.cardGame.common.games.components.components.Card;
import ulco.cardGame.common.interfaces.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardGame extends BoardGame {

    private List <Card> cards; // une liste de cartes, de type Card
    private Integer numberOfRounds ; //un compteur permettant de savoir le nombre de rounds réalisés


    /**
     *  Constructeur de
     * la classe faisant appel au constructeur de la classe mère BoardGame
     * @param name
     * @param maxPlayer
     * @param filename
     */
    public CardGame(String name, Integer maxPlayer, String filename) {
        super(name, maxPlayer, filename);
    }

    /**
     *  méthode qui charge le contenu du fichier de jeu et
     * initialise le jeu de cartes en conséquence (la liste des cartes)
     * @param filename
     */

    @Override
    public void initialize(String filename) {
        try {
            File cardFile = new File(filename);
           this.cards= new ArrayList<>();
            Scanner myReader = new Scanner(cardFile);
            while (myReader.hasNextLine()){
                String[] data = myReader.nextLine().split(";");
                //String CardV = data.split("; ");
               this.cards.add(new Card(data[0],Integer.parseInt(data[1])));
               //Card card = new Card(CardV[0],Integer.parseInt(CardV[1]));
                //this.cards.add(card);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    /**
     * méthode associée à la boucle de jeu
     * @return
     */
    @Override
    public Player run() {
        return null;
    }

    /**
     * méthode permettant de vérifier si l’on est dans un état de fin de jeu
     * ou non
     * et vérifie s’il y a un gagnant ou non
     * @return
     */
    @Override
    public boolean end() {
        return false;
    }

    @Override
    public String toString() {
        return "CardGame{" +
                "name='" + name + '\'' +
                '}';
    }
}
