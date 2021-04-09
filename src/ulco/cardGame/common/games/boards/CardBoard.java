package ulco.cardGame.common.games.boards;

import ulco.cardGame.common.games.components.Card;
import ulco.cardGame.common.games.components.Component;
import ulco.cardGame.common.interfaces.Board;

import java.util.ArrayList;
import java.util.List;

public class CardBoard implements Board {
    List<Card>cards;

    /**
     * CardBoard constructor
     */
    public CardBoard() {
        this.cards=new ArrayList<>();
    }

    /**
     * clear cardBoard
     */
    @Override
    public void clear() {
        cards.clear();
    }

    /**
     * add component to cards list
     * @param component
     */
    @Override
    public void addComponent(Component component) {
        this.cards.add((Card)component);
    }

    /**
     * Get all component in Player hand
     * @return
     */
    @Override
    public List<Component> getComponents() {
        return new ArrayList<>(cards);
    }

    /**
     * get different component list cards or coins
     * @param componentClass
     * @return
     */
    @Override
    public List<Component> getSpecificComponents(Class componentClass) {
        return new ArrayList<>(cards);
    }

    @Override
    public void displayState() {
        for (Card card : cards){
            System.out.println(card.getPlayer().getName() +", played "+ card.getName());
        }
    }
}
