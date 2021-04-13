package ulco.cardGame.common.players;

import ulco.cardGame.common.games.components.Card;
import ulco.cardGame.common.games.components.Coin;
import ulco.cardGame.common.games.components.Component;

import java.util.*;

public class PokerPlayer extends BoardPlayer {

    private List<Card> cards;
    private List<Coin> coins;

    public PokerPlayer(String name) {
        super(name);

        this.cards = new ArrayList<>();
        this.coins = new ArrayList<>();
    }

    @Override
    public Integer getScore() {

        return score;
    }

    @Override
    public Component play() {
        this.displayHand();

        Scanner scanner = new Scanner(System.in);
        Component coinToPlay = null;
        boolean correctCoin = false;

        do {

            System.out.println("[" + this.getName() + "], please select a valid Coin to play (coin color)");
            String value = scanner.nextLine();

            for (Coin coin : coins) {
                if (coin.getName().equals(value)) {
                    coinToPlay = coin;
                    correctCoin = true;
                }
            }

        } while(!correctCoin);

        // Remove card from  player hand
        this.removeComponent(coinToPlay);

        return coinToPlay;
    }

    @Override
    public void addComponent(Component component) {

        if (component instanceof Card)
            cards.add((Card)component);
        if (component instanceof Coin) {
            coins.add((Coin) component);
            this.score += component.getValue();
        }
    }

    @Override
    public void removeComponent(Component component) {

        if (component instanceof Card)
            cards.remove(component);

        // if coin component, we need to update the current score
        if (component instanceof Coin) {
            coins.remove(component);
            this.score -= component.getValue();
        }
    }

    @Override
    public List<Component> getComponents() {

        List<Component> components = new ArrayList<>();

        // add all known components
        components.addAll(cards);
        components.addAll(coins);

        return components;
    }

    @Override
    public List<Component> getSpecificComponents(Class classType) {

        // create empty list
        List<Component> components = new ArrayList<>();

        // Add expected elements inside this new list
        if (classType == Card.class)
            components.addAll(cards);

        if (classType == Coin.class)
            components.addAll(coins);

        return components;
    }

    @Override
    public void shuffleHand() {
        // prepare to shuffle hand
        Collections.shuffle(cards);
    }

    @Override
    public void clearHand() {

        // by default clear player hand
        // unlink each card
        for (Card card : cards) {
            card.setPlayer(null);
        }

        // only clear cards for this round
        this.cards.clear();
    }

    /**
     * Display some expected components of player
     *  - Cards
     *  - Sum of coins in hand
     */
    @Override
    public void displayHand() {

        System.out.println("-------------------------------------");
        System.out.println("Hand of [" + this.getName() + "]");
        System.out.println("              ---------              ");
        for (Card card : cards) {
            System.out.println("Card: " + card.getName());
        }

        Integer coinSum = 0;
        Map<String, Integer> coinsNumber = new HashMap<>();

        for (Coin coin : coins) {
            coinSum += coin.getValue();

            coinsNumber.merge(coin.getName(), 1, Integer::sum);
        }

        System.out.println("              ---------              ");

        // Display coin occurrences
        for (Map.Entry<String, Integer> entry : coinsNumber.entrySet()) {
            System.out.println(" - Coin " + entry.getKey() + " x " + entry.getValue());
        }

        System.out.println("Your Coins sum is about: " + coinSum);
        System.out.println("-------------------------------------");
    }

    @Override
    public String toString() {
        return "PokerPlayer{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
