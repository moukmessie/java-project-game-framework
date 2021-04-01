package ulco.cardGame.common.games.components;

import ulco.cardGame.common.games.BoardPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public  class CardPlayer extends BoardPlayer {

public List<Card> cards;

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
        //return this.cards.remove(0) ;
        Card cardRemove = null;
        if(cards.size()>0){
            cardRemove = cards.get(0);
            cards.remove(cardRemove);
        }
        return cardRemove ;

    }

    @Override
    public void addComponent(Component component) {

        this.cards.add((Card) component);
        this.score++;
    }

    @Override
    public void removeComponent(Component component) {

        this.cards.remove(component);
       this.score--;
    }

    @Override
    public List<Component> getComponents() {

     return new ArrayList<>(this.cards);
    }

    @Override
    public void shuffleHand() {

        Collections.shuffle(cards);
    }

    @Override
    public void clearHand() {
      cards.clear();
    }

    @Override
    public String toString() {
        return "CardPlayer{" +
                "name='" + getName() + '\'' +
                ", score=" + getScore() +
                '}';
    }


}
