package ulco.cardGame.common.games;

import ulco.cardGame.common.games.boards.PokerBoard;
import ulco.cardGame.common.games.components.Card;
import ulco.cardGame.common.games.components.Coin;
import ulco.cardGame.common.games.components.Component;
import ulco.cardGame.common.interfaces.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PokerGame extends BoardGame {

    private List<Card> cards;
    private List<Coin> coins;
    private Integer maxRounds;
    private Integer numberOfRounds;

    /**
     * Enable constructor of Game
     * - Name of the game
     * - Maximum number of players of the Game
     * - Expected number of rounds until end
     * - Filename with all required information to load the Game
     *
     * @param name
     * @param maxPlayers
     * @param maxRounds
     * @param filename
     */
    public PokerGame(String name, Integer maxPlayers, Integer maxRounds, String filename) {
        super(name, maxPlayers, filename);

        this.maxRounds = maxRounds;
        this.numberOfRounds = 0;
        this.board = new PokerBoard();
    }

    @Override
    public void initialize(String filename) {

        this.cards = new ArrayList<>();
        this.coins = new ArrayList<>();

        // Here initialize the list of Cards
        try {
            File cardFile = new File(filename);
            Scanner myReader = new Scanner(cardFile);

            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                String[] dataValues = data.split(";");

                // Extract information from file
                String className = dataValues[0];
                String componentName = dataValues[1];
                Integer componentValue = Integer.valueOf(dataValues[2]);

                // get Card value and add it into the Game
                if (className.equals(Card.class.getSimpleName())) {

                    this.cards.add(new Card(componentName, componentValue, true));
                }
                else if (className.equals(Coin.class.getSimpleName())){

                    // Add this coin for each possible player in Poker Game
                    for (int i = 0; i < this.maxNumberOfPlayers(); i++) {

                        this.coins.add(new Coin(componentName, componentValue));
                    }
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

        Player gameWinner = null;

        // Distribute each coins
        // each coin has been duplicate in initialize in order to have expected same number at beginning
        int playerIndex = 0;

        for (Coin coin : coins) {

            players.get(playerIndex).addComponent(coin);
            coin.setPlayer(players.get(playerIndex));

            playerIndex++;

            if (playerIndex >= players.size()) {
                playerIndex = 0;
            }
        }

        do {
            // prepare to distribute card to each player for current round
            Collections.shuffle(cards);

            // Distribute 3 cards to each player
            int cardIndex = 0;

            for (int i = 0; i < 3; i++) {

                for (Player player : players) {
                    player.addComponent(cards.get(cardIndex));
                    cards.get(cardIndex).setPlayer(player);
                    cardIndex++;
                }
            }

            // Now initiate the Game principle

            // Depending of player hand, they can choose a Coin to play
            for (Player player : players) {
                board.addComponent(player.play());
            }

            // Board will manage the Poker game state
            // First we need to display 3 cards
            for (int i = 0; i < 3; i++) {
                board.addComponent(cards.get(cardIndex));
                cardIndex++;
            }

            board.displayState();

            // Depending of player hand and board state they can choose a Coin to play
            for (Player player : players) {
                board.addComponent(player.play());
            }

            // now we can get the winner
            // winner the won with the most number of same card with same value (and higher ones)
            // including cards in hand and board

            // Store best user card values
            Map<Player, Map.Entry<Integer, Integer>> usersCardValues = new HashMap<>();

            for (Player player : players) {

                // Retrieve all cards
                List<Component> allCards = new ArrayList<>();
                allCards.addAll(player.getSpecificComponents(Card.class));
                allCards.addAll(board.getSpecificComponents(Card.class));

                // Store combinations of same card of current user
                // <cardValue, Occurrences>
                Map<Integer, Integer> counts = new HashMap<>();


                for (Component e : allCards) {
                    counts.merge(e.getValue(), 1, Integer::sum);
                }

                // Get maximum occurrences obtained
                Optional<Integer> maxOccurrences = counts.entrySet().stream() // Get stream of all counts
                        .max((e1, e2) -> e1.getValue().compareTo(e2.getValue())) // compare value of each counts (number of same cards)
                        .map(e -> e.getValue()); // Return the value (number of counts) of the max found occurrences

                // Find the key value of the card (card value)
                // Depending of maximum occurrences obtained get the best highest card with same occurrences
                Optional<Integer> maxKey = counts.entrySet().stream()
                        .filter(e -> e.getValue().equals(maxOccurrences.get()))
                        .max((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
                        .map(e -> e.getKey());

                // add entry into players map with the max card value found and its occurrences
                usersCardValues.put(player, new AbstractMap.SimpleEntry<>(maxKey.get(), maxOccurrences.get()));
            }

            // Get maximum occurrences obtained from players
            Optional<Integer> maxPlayerOccurrences = usersCardValues.entrySet().stream()
                    .max((e1, e2) -> e1.getValue().getValue().compareTo(e2.getValue().getValue()))
                    .map(e -> e.getValue().getValue());

            // Critical equality
            // Get max key known of max occurrences player obtained
            Optional<Integer> maxPlayerKey = usersCardValues.entrySet().stream()
                    .filter(e -> e.getValue().getValue().equals(maxPlayerOccurrences.get()))
                    .max((e1, e2) -> e1.getValue().getKey().compareTo(e2.getValue().getKey()))
                    .map(e -> e.getValue().getKey());

            List<Player> possibleWinner = new ArrayList<>();

            // Check if multiple players have same kind of cards and occurrences
            for (Player player : players) {

                Integer occurrences = usersCardValues.get(player).getValue();
                Integer cardValue = usersCardValues.get(player).getKey();

                if (cardValue.equals(maxPlayerKey.get()) && occurrences.equals(maxPlayerOccurrences.get())) {
                    possibleWinner.add(player);
                }
            }

            // Coins have to be removed from current players hand
            List<Component> boardCoins = board.getSpecificComponents(Coin.class);

            for (Component coin : boardCoins) {
                coin.setPlayer(null);
            }

            System.out.println("-----------");
            for (Player player : players) {
                Integer occurrences = usersCardValues.get(player).getValue();
                Integer cardValue = usersCardValues.get(player).getKey();

                System.out.println("Player " + player.getName() + " has " + occurrences + " same card(s) of value " + cardValue);
            }
            System.out.println("-----------");
            for (Player player : possibleWinner) {
                Integer occurrences = usersCardValues.get(player).getValue();
                Integer cardValue = usersCardValues.get(player).getKey();

                System.out.println("Player " + player.getName() + " won the game with " + occurrences + " same card(s) of value " + cardValue);
            }
            System.out.println("-----------");

            // Coins are now in hand of the winner
            if (possibleWinner.size() > 1) {

                System.out.println("Equality found between " + possibleWinner.size() + " players. The gains will be randomly distributed");

                Map<Player, Integer> coinSum = new HashMap<>();

                // Randomly split coins between each player
                for (Component coin : boardCoins) {

                    Random random = new Random();

                    int userIndex = random.nextInt(possibleWinner.size() - 1);

                    Player selectedPlayer = possibleWinner.get(userIndex);

                    coin.setPlayer(selectedPlayer);
                    selectedPlayer.addComponent(coin);

                    coinSum.merge(selectedPlayer, coin.getValue(), Integer::sum);
                }

                // Display player gain using random way
                for (Map.Entry<Player, Integer> entry : coinSum.entrySet()) {

                    System.out.println("Player [" + entry.getKey().getName() + "] gains: " + entry.getValue());
                }

            } else {

                for (Component coin : boardCoins) {
                    Player selectedPlayer = possibleWinner.get(0);

                    coin.setPlayer(selectedPlayer);
                    selectedPlayer.addComponent(coin);
                }
            }

            // Clear hand of each user
            // Card will be now linked to no one
            for (Player player : players) {
                player.clearHand();
            }

            // Need to clear the board
            board.clear();

            System.out.println("-----------");
            System.out.println("End of the round n°" + (numberOfRounds + 1) + " of " + maxRounds);
            this.displayState();

            numberOfRounds++;

        } while(!this.end());

        // find Winner Player depending of coins sum in his hand
        Integer bestCoinSum = 0;

        for (Player player : players) {

            Integer coinSum = 0;

            List<Component> playerCoins = player.getSpecificComponents(Coin.class);

            for (Component coin : playerCoins) {
                coinSum += coin.getValue();
            }

            if (coinSum > bestCoinSum) {
                bestCoinSum = coinSum;
                gameWinner = player;
            }
        }
        return gameWinner;
    }

    @Override
    public boolean end() {
        return numberOfRounds >= maxRounds;
    }
}
