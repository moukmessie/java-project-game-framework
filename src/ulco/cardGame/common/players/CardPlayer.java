package ulco.cardGame.common.players;

import ulco.cardGame.common.games.components.Card;
import ulco.cardGame.common.games.components.Component;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public  class CardPlayer extends BoardPlayer {

public List<Component> cards;

    /**
     * Default inherited constructor
     * @param name
     */
    public CardPlayer(String name) {
        super(name);
        cards = new ArrayList<>();
    }

    @Override
    public Integer getScore() {
        return this.score;
    }

    @Override
    public Card play() {
        Card cardToPlay = (Card)cards.get(0);

        // Remove card from player hand and update score
        this.removeComponent(cardToPlay);

        return cardToPlay;

    }

    @Override
    public void addComponent(Component component) {

        this.cards.add((Card) component);
        // update current player score (cards in hand)
        this.score = cards.size();
    }

    @Override
    public void removeComponent(Component component) {

        // Remove card from hand
        cards.remove(component);
        // update current player score (cards in hand)
        this.score = cards.size();
    }

    @Override
    public List<Component> getComponents() {

     return this.cards;
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
        for (Component card : cards) {
            card.setPlayer(null);
        }

        this.cards = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "CardPlayer{" +
                "name='" + getName() + '\'' +
                ", score=" + getScore() +
                '}';
    }


}
