package ulco.cardGame.common.games;

import ulco.cardGame.common.games.components.Card;
import ulco.cardGame.common.interfaces.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CardGame extends BoardGame {

    private List <Card> cards; // a list of cards, of type Card
    private Integer numberOfRounds ; //a counter allowing to know the number of completed rounds


    /**
     * constructor calling the parent constructor of BoardGame class
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
     * method that loads the contents of the game file and
     *  initializes the card game accordingly (the card list)
     *
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
     * method associated with the game loop
     * méthode associée à la boucle de jeu
     * @return
     */
    @Override
    public Player run() {



        Player winner = null;//winner attribute
        int count = 0;//count attribute
        int hightValue = 0;//highest card value
        numberOfRounds=0;
        List<Player>equal = new ArrayList<>();//table of equal players
        //Shuffled cards
        Collections.shuffle(cards);

        //Cards distribution
        for (Card card : cards){
            getPlayers().get(count%getPlayers().size()).addComponent(card);
            count++;
        }
        //players can play
        for (Player player : players) {
            player.canPlay(true);
        }


        while (!this.end()) {
            //starting game play
            numberOfRounds += 1;
            equal.clear();
            System.out.println("***** ROUND " + numberOfRounds + " *****");
            displayState();//view state of players

            //shuffleHand
            if (numberOfRounds % 10 == 0) {
                System.out.println("Card shuffle....");
                for (Player player : players) {
                        player.shuffleHand();//players shuffle hand
                }

            }
            //Store player and associate his played card
            Map <Player , Card>playerCard = new HashMap<>();
            for (Player player : this.getPlayers()) {
                // Get played card by current player
                //if can playing
                 if (player.isPlaying()){
                    Card card = (Card) player.play();
                    if (card.getValue()> hightValue){
                        hightValue = card.getValue();//storing the largest value in the hightValue attribute
                        winner = player;
                    }
                // Keep knowledge of card played
                playerCard.put(player, card);
                System.out.println(player.getName() + " has played " + card.getName());
               }
              else {
                     removePlayer(player);//remove player if he don't have cards
              }

            }

            for (Map.Entry<Player, Card> entry : playerCard.entrySet()) {
                // get player (key of entry map )
                Player player = entry.getKey();
                // get played card of player
                Card card = entry.getValue();
                //if equal add to table equal
                if (card.getValue().equals(hightValue)) {
                    equal.add(player);
                }
            }

            if (equal.size()!=0){
                Random win =new Random();
                winner=equal.get(win.nextInt(equal.size()));//winner random
            }

            for (Map.Entry<Player, Card> entry : playerCard.entrySet()){
                //attribute cards of other player to the winner
                winner.addComponent((entry.getValue()));
                if (entry.getKey().getComponents().size()==0){
                    entry.getKey().canPlay(false);//when the player has no more cards he is excluded from game
                }
            }
            System.out.println(winner.getName() + " win ROUND "+ numberOfRounds +" score: "+winner.getScore());//view the round winner

        }

        endGame=true;
        System.out.println("EndGame ");
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
