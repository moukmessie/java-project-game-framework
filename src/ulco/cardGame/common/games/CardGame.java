package ulco.cardGame.common.games;

import ulco.cardGame.common.games.boards.CardBoard;
import ulco.cardGame.common.games.components.Card;
import ulco.cardGame.common.games.components.Component;
import ulco.cardGame.common.interfaces.Player;

import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * CardGame which extend BoardGame
 * BoardGame need to implement Game interface methods
 */
public class CardGame extends BoardGame {

    private List<Card> cards;
    private Integer numberOfRounds;

    /**
     * Enable constructor of CardGame
     * - Name of the game
     * - Maximum number of players of the Game
     *  - Filename of current Game
     * @param name game name
     * @param maxPlayers max number of players
     * @param filename file data
     */
    public CardGame(String name, Integer maxPlayers, String filename) {
        super(name, maxPlayers, filename);

        this.board = new CardBoard();
        this.numberOfRounds = 0;
    }

    @Override
    public void initialize(String filename) {

        this.cards = new ArrayList<>();

        // Here initialize the list of Cards
        try {
            File cardFile = new File(filename);
            Scanner myReader = new Scanner(cardFile);

            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                String[] dataValues = data.split(";");

                // get Card value
                Integer value = Integer.valueOf(dataValues[1]);
                this.cards.add(new Card(dataValues[0], value, true));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Player run(Map <Player, Socket>playerSocketMap ) throws IOException, ClassNotFoundException {
        Player gameWinner = null;
        Object answer ;
        Card cardSend = null;

        // prepare to distribute card to each player
        Collections.shuffle(cards);

        int playerIndex = 0;

        for (Card card : cards) {

            players.get(playerIndex).addComponent(card);
            card.setPlayer(players.get(playerIndex));

            playerIndex++;

            if (playerIndex >= players.size()) {
                playerIndex = 0;
            }
        }
        for(Socket socketPlayer : playerSocketMap.values() ){
            ObjectOutputStream gamePlayer = new ObjectOutputStream(socketPlayer.getOutputStream());
            gamePlayer.writeObject(this);
        }

        // Send update of Game state for each player
        for (Player player : players) {
            System.out.println(player.getName() + " has " + player.getComponents().size() + " cards");

       for (Map.Entry<Player,Socket>entry : playerSocketMap.entrySet()){

           ObjectOutputStream cardsPlayer = new ObjectOutputStream(entry.getValue().getOutputStream());
           cardsPlayer.writeObject(player.getName() + " has " + player.getComponents().size() + " cards");
       }
    }

        // while each player can play
        while (!this.end()) {

            Map<Player, Card> playedCard = new HashMap<>();

            for (Player player : players) {

                if (!player.isPlaying())
                    continue;

                // Get card played by current player
               // Card card = (Card) player.play();
                for (Map.Entry<Player,Socket>entry : playerSocketMap.entrySet()) {
                    String msg = "";
                    ObjectOutputStream outputStream = new ObjectOutputStream(entry.getValue().getOutputStream());
                    if (entry.getKey() == player) {
                        msg += " [" + player.getName() + "] you have to play...";
                    } else {
                        msg += "Waiting for " + player.getName() + " to play...";
                    }
                    outputStream.writeObject(msg);
                }
                player.play(playerSocketMap.get(player));

                ObjectInputStream sendIn = new ObjectInputStream(playerSocketMap.get(player).getInputStream());

                do {
                    //send and read message
                    answer = sendIn.readObject();

                    if (answer instanceof Card){
                        cardSend = (Card) answer;
                    }

                }while (!(answer instanceof Card));

                    //ObjectOutputStream receveOut = new ObjectOutputStream(playerSocketMap.get(player).getOutputStream());
                  //  receveOut.writeObject(cardSend.getName() + " played by " + player.getName());

                //removing played cards from game
                for (Component c: player.getSpecificComponents(Card.class)){
                    if (c.getId().equals(cardSend.getId())){
                        player.removeComponent(c);
                    }
                }

                    // add card inside board
                board.addComponent(cardSend);

                // Keep knowledge of card played
                playedCard.put(player, cardSend);

                System.out.println(player.getName() + " has played " + cardSend.getName());

                for(Map.Entry<Player,Socket>entry : playerSocketMap.entrySet()){
                ObjectOutputStream hasPlayed = new ObjectOutputStream(entry.getValue().getOutputStream());
                hasPlayed.writeObject("Player " + player.getName() + " has played " + cardSend.getName());
                }

            }

            // display board state

            for(Map.Entry<Player,Socket>entry : playerSocketMap.entrySet()){
                ObjectOutputStream sendBoard = new ObjectOutputStream(entry.getValue().getOutputStream());
                sendBoard.writeObject(board);
            }
            //board.displayState();


            // Check which player has win
            int bestValueCard = 0;

            List<Player> possibleWinner = new ArrayList<>();
            List<Card> possibleWinnerCards = new ArrayList<>();

            for (Card card : playedCard.values()) {
                if (card.getValue() >= bestValueCard)
                    bestValueCard = card.getValue();
            }

            // check if equality
            for(Map.Entry<Player, Card> entry : playedCard.entrySet()){

                Card currentCard = entry.getValue();

                if (currentCard.getValue() >= bestValueCard) {

                    possibleWinner.add(entry.getKey());
                    possibleWinnerCards.add(currentCard);
                }
            }

            // default winner index
            int winnerIndex = 0;

            // Random choice if equality is reached
            if (possibleWinner.size() > 1){
                Random random = new Random();
                winnerIndex = random.nextInt(possibleWinner.size() - 1);
            }

            Player roundWinner = possibleWinner.get(winnerIndex);
            Card winnerCard = possibleWinnerCards.get(winnerIndex);

            System.out.println("Player " + roundWinner + " won the round with " + winnerCard);
            for(Map.Entry<Player,Socket>entry : playerSocketMap.entrySet()){
                ObjectOutputStream sendWinner = new ObjectOutputStream(entry.getValue().getOutputStream());
                sendWinner.writeObject("Player " + roundWinner + " won the round with " + winnerCard);
            }


            // Update Game state
            for (Card card : playedCard.values()) {

                // remove from previous player
                roundWinner.addComponent(card);
                card.setPlayer(roundWinner);
            }

            // clear board state
            board.clear();

            // Check players State
            for (Player player : players){

                // player cannot still play if no card in hand
                if (player.getScore() == 0){
                    player.canPlay(false);
                    for(Map.Entry<Player,Socket>entry : playerSocketMap.entrySet()) {
                        ObjectOutputStream losingPlayer = new ObjectOutputStream(entry.getValue().getOutputStream());
                        losingPlayer.writeObject(player.getName() + "  has lost !!!");
                    }
                    removePlayer(player);

                }

                // player win if all cards are in his hand
                if (player.getScore() == cards.size()) {
                    player.canPlay(false);
                    gameWinner = player;
                    for(Map.Entry<Player,Socket>entry : playerSocketMap.entrySet()){
                        ObjectOutputStream winningPlayer = new ObjectOutputStream(entry.getValue().getOutputStream());
                        winningPlayer.writeObject(gameWinner.getName() + "  WON !!!");
                    }
                }
            }

            // Display Game state
            for(Map.Entry<Player,Socket>entry : playerSocketMap.entrySet()){
                ObjectOutputStream updateGame = new ObjectOutputStream(entry.getValue().getOutputStream());
                updateGame.writeObject(this);
            }

            //this.displayState();

            // shuffle player hand every n rounds
            this.numberOfRounds += 1;

            if (this.numberOfRounds % 10 == 0) {
                for (Player player : players){
                    player.shuffleHand();
                }
            }
        }

        return gameWinner;
    }

    @Override
    public boolean end() {
        // check if it's the end of the game
        endGame = true;
        for (Player player : players) {
            if (player.isPlaying()) {
                endGame = false;
            }
        }

        return endGame;
    }

    @Override
    public String toString() {
        return "CardGame{" +
                "name='" + name + '\'' +
                '}';
    }

}
