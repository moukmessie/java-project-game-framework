package ulco.cardGame.common.games.boards;

import ulco.cardGame.common.games.components.Card;
import ulco.cardGame.common.games.components.Coin;
import ulco.cardGame.common.games.components.Component;
import ulco.cardGame.common.interfaces.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokerBoard implements Board {

    private List<Card> cards;
    private List<Coin> coins;

    public PokerBoard() {
        this.cards = new ArrayList<>();
        this.coins = new ArrayList<>();
    }

    @Override
    public void clear() {
        this.cards.clear();
        this.coins.clear();
    }

    @Override
    public void addComponent(Component component) {

        if (component instanceof Card)
            cards.add((Card)component);
        if (component instanceof Coin)
            coins.add((Coin)component);
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

    /**
     * Display the current board state
     * - Enable card displayed
     * - Sum of coin placed
     */
    @Override
    public void displayState() {

        System.out.println("-------------- Board state -------------");
        for (Card card : cards) {
            System.out.println(" - Card: " + card.getName());
        }

        Integer coinSum = 0;
        Map<String, Integer> coinsNumber = new HashMap<>();

        for (Coin coin : coins) {
            coinSum += coin.getValue();

            coinsNumber.merge(coin.getName(), 1, Integer::sum);
        }

        System.out.println("               ---------                ");

        // Display coin occurrences
        for (Map.Entry<String, Integer> entry : coinsNumber.entrySet()) {
            System.out.println(" - Coin " + entry.getKey() + " x " + entry.getValue());
        }

        System.out.println("Your Coins sum placed is about: " + coinSum);
        System.out.println("----------------------------------------");
    }
}
