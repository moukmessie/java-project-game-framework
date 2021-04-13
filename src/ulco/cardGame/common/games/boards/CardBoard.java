package ulco.cardGame.common.games.boards;

import ulco.cardGame.common.games.components.Card;
import ulco.cardGame.common.games.components.Component;
import ulco.cardGame.common.interfaces.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Specific board for Card Game
 */
public class CardBoard  implements Board {

    private List<Card> cards;

    public CardBoard() {

        cards = new ArrayList<>();
    }

    @Override
    public void clear() {
        this.cards.clear();
    }

    @Override
    public void addComponent(Component component) {
        this.cards.add((Card)component);
    }

    @Override
    public List<Component> getComponents() {
        return new ArrayList<>(this.cards);
    }

    @Override
    public List<Component> getSpecificComponents(Class classType) {
        // By default
        return new ArrayList<>(this.cards);
    }

    /**
     * Display the current board state
     * - Current card enable in game
     */
    public void displayState() {

        System.out.println("-------------- Board state -------------");
        for (Card card : cards) {
            System.out.println(card.getName() + " played by " + card.getPlayer().getName());
        }
        System.out.println("----------------------------------------");
    }
}
