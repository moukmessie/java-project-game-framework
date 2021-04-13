package ulco.cardGame.common.games;

import ulco.cardGame.common.games.boards.CardBoard;
import ulco.cardGame.common.games.components.Card;
import ulco.cardGame.common.interfaces.Player;

import java.io.File;
import java.io.FileNotFoundException;
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
     * @param name
     * @param maxPlayers
     * @param filename
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

    @Override
    public Player run() {

        Player gameWinner = null;

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


        // Send update of Game state for each player
        for (Player player : players) {
            System.out.println(player.getName() + " has " + player.getComponents().size() + " cards");
        }

        // while each player can play
        while (!this.end()) {

            Map<Player, Card> playedCard = new HashMap<>();

            for (Player player : players) {

                if (!player.isPlaying())
                    continue;

                // Get card played by current player
                Card card = (Card) player.play();

                // add card inside board
                board.addComponent(card);

                // Keep knowledge of card played
                playedCard.put(player, card);

                System.out.println(player.getName() + " has played " + card.getName());
            }

            // display board state
            board.displayState();

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
                if (player.getScore() == 0)
                    player.canPlay(false);

                // player win if all cards are in his hand
                if (player.getScore() == cards.size()) {
                    player.canPlay(false);
                    gameWinner = player;
                }
            }

            // Display Game state
            this.displayState();

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
