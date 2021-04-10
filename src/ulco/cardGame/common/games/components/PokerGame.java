package ulco.cardGame.common.games.components;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import ulco.cardGame.common.games.BoardGame;
import ulco.cardGame.common.games.boards.PokerBoard;
import ulco.cardGame.common.interfaces.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
    public PokerGame(String name, Integer maxPlayers, String filename,Integer maxRounds) {
        super(name, maxPlayers, filename);
        this.board = new PokerBoard();
        this.maxRounds=maxRounds;
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
        Player roundWinner=null;
        Player winner=null;
        Integer maxScore=0;
        numberOfRounds=0;
        Integer maxCardBoard=3;
        List<Integer>cardsValues = new ArrayList<>();
        //equality list
        List<Player>Equal = new ArrayList<>();

        //winner frequency
        Integer winFrequency=1; Integer winValue=0;

        //prepare to distribute coin to each player
        Collections.shuffle(cards);
        Collections.shuffle(coins);



        int playerIndex = 0;
        for (Coin coin : coins){
            players.get(playerIndex).addComponent(coin);
            coin.setPlayer(players.get(playerIndex));

            playerIndex++;

            if (playerIndex>= players.size()){
                playerIndex=0;
            }
        }

        while(!end()){
            Collections.shuffle(cards);
            board.clear();
            System.out.println("---------- Rounds " + numberOfRounds+1+"------------");

            //distribute three card to each player
            for(int i=0; i<maxCardBoard;i++){
                for (Player player : players){
                    player.canPlay(true);

                    Card card = cards.get(0);
                    card.setHidden(false);//card visible
                    player.addComponent(card);
                    cards.remove(card);

                }
            }

            //hide the remaining cards
            for (Card card : cards){
                card.setHidden(true);
            }

            // coin bet
            for (Player player : players){
                player.displayHand();//view Hands to each player

               // if(player.getScore()==0) player.canPlay(false);
               // Coin coin = (Coin)player.play();
                Component coin = player.play();
                if(coin==null){
                    System.out.println(player.getName() + "fold !");
                    player.canPlay(false);

                }else{
                    board.addComponent(coin);
                }

            }


            //view cards on board to each player
            for (int i=0; i<maxCardBoard; i++){
                Card card = cards.get(0);
                card.setHidden(false);//card visible
                board.addComponent(card);
                cards.remove(card);
            }
            //view board
            board.displayState();

            // coin bet 2
            for (Player player : players){

                player.displayHand();//view Hands to each player
                if(player.getScore()==0) player.canPlay(false);
                //Coin coin = (Coin)player.play();
                Component coin = player.play();
                if(coin==null){
                    System.out.println(player.getName() + "fold !");
                    player.canPlay(false);
                }else{
                    board.addComponent(coin);
                }

            }

        }
        System.out.println("-----------------------********-----------------");

        for (Player player : players){
          if(player.isPlaying()){
              List<Component>cardPlayBoard = player.getSpecificComponents(Card.class);
              //adding the player's cards to cardPlayBoard
              cardPlayBoard.addAll(board.getSpecificComponents(Card.class));

              //adding Component values to cardsValues list
              for (Component card : cardPlayBoard){
                cardsValues.add(card.getValue());

              }
              //frequency
              Integer frequency; Integer maxFrequency = 1; Integer maxValue=0;

              for (Integer value : cardsValues){
                  frequency = Collections.frequency(cardsValues,value);
                  if (frequency>maxFrequency){
                      maxValue = value;
                      maxFrequency =frequency;
                      //High value and equality frequency
                  }else if (frequency.equals(maxFrequency) && value> maxValue){
                      maxValue=value;//update maxValue
                  }
              }




                if (maxFrequency> winFrequency){
                    roundWinner=player;
                    winFrequency=maxFrequency;
                    winValue=maxValue;

                }else if (winFrequency.equals(maxFrequency) && maxValue>winValue){
                    roundWinner=player;
                    winValue=maxValue;
                }else if (winFrequency.equals(maxFrequency)&& maxValue.equals(winValue)){
                    System.out.println("Equality !");
                    Equal.add(player);
                    //Random winner
                    Integer winnerIndex=0;
                    if (Equal.size()>1){
                        Random random = new Random();
                        winnerIndex= random.nextInt(Equal.size()-1);
                    }
                    roundWinner = Equal.get(winnerIndex);
                    winValue=maxValue;
                }

                System.out.println("Player "+player.getName() + " has "+ maxFrequency +" card(s) of value "+ maxValue );

                cardsValues.clear();

          }


        }

        System.out.println("Player " + roundWinner.getName() + " won the round  with " + winFrequency + " same card(s) of value " + winValue);
        System.out.println("----------------------------------------------------------------");
        System.out.println("----------End round N° "+ numberOfRounds+ " of "+ maxRounds);
        displayState();
        // roundWinner get coins from the board
        for (Component coin : board.getSpecificComponents(Coin.class))
        {
            roundWinner.addComponent(coin);
        }
        //cards are removed from each player's hands
        for (Player player : players){
            List<Component>componentList = player.getSpecificComponents(Card.class);
            for (Component component : componentList){
                cards.add((Card)component);
            }
            player.clearHand();

        }

        //search winner game
        for (Player player : players){

            if (player.getScore()>maxScore){
                winner=player;
                maxScore=player.getScore();
            }

        }
        displayState();
        System.out.println(winner.getName()+" won the Game !! ");

        return winner;
    }

    @Override
    public boolean end() {
        if(this.numberOfRounds.equals(this.maxRounds)){
            endGame=true;
            return true;

        }
       return  false;
    }
}
