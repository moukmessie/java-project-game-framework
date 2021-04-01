package ulco.cardGame.common.games;

import ulco.cardGame.common.games.components.Card;
import ulco.cardGame.common.interfaces.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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


        //Store player and associate his played card
        Map <Player , Card>playerCard = new HashMap<>();
        Player winner = null;//winner attribute
        int count = 0;//count attribute
        int hightValue = 0;//valeur de carte la plus elevé
        numberOfRounds=0;
        //Shuffler cards
        Collections.shuffle(cards);

        //Cards distrbution
        for (Card card : cards){
            getPlayers().get(count%getPlayers().size()).addComponent(card);
            count++;
        }
        //players can play
        for (Player player : players) {
            player.canPlay(true);
        }


        while (!this.end()) {
            //starting game players
            numberOfRounds += 1;
            System.out.println("***** ROUND " + numberOfRounds + " *****");
            displayState();//view state of players
            //shuffleHand
            if (numberOfRounds % 10 == 0) {
                System.out.println("Card shuffle....");
                for (Player player : players) {

                        player.shuffleHand();//si le joueur possède encore des cartes mélanger
                }

            }

            for (Player player : this.getPlayers()) {
                // Get played card by current player
                 if (player.isPlaying()){
                    Card card = (Card) player.play();

                // Keep knowledge of card played
                playerCard.put(player, card);
                System.out.println(player.getName() + " has played " + card.getName());
               }
              else {
                     removePlayer(player);

              }

            }

            List<Player>equal = new ArrayList<>();
            for (Map.Entry<Player, Card> entry : playerCard.entrySet()) {
                // get player (key of entry map )
                Player player = entry.getKey();
                // get played card of player
                // Same as: Card card = playedCard .get ( player );
                Card card =entry.getValue();

                if (card.getValue() > hightValue ) {
                    hightValue = card.getValue();//stockage de la plus grande valeur dans l'attribut hightValue
                    winner = player;
                }else if (card.getValue().equals(hightValue) ) {
                    equal.add(player);
                    if (equal.size() !=0){
                        int alea= new Random().nextInt(equal.size());

                        winner=equal.get(alea);
                    }
                }
                winner.addComponent(entry.getValue());


            }System.out.println(winner.getName() + " win ROUND "+ numberOfRounds +" score: "+winner.getScore());
            playerCard.clear();


        }
        winner.clearHand();
        // playerCard.clear();
        endGame=true;
        return winner;

    }

    /**
     * méthode permettant de vérifier si l’on est dans un état de fin de jeu
     * ou non
     * et vérifie s’il y a un gagnant ou non
     * @return
     */
    @Override
    public boolean end() {
        for(Player player : getPlayers()){
            if(player.getScore() == cards.size()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "CardGame{" +
                "name='" + name + '\'' +
                '}';
    }
}
