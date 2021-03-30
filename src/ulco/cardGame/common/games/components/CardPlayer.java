package ulco.cardGame.common.games.components;

import ulco.cardGame.common.games.BoardPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public  class CardPlayer extends BoardPlayer {

public List<Card> cards;

    public CardPlayer(String name) {
        super(name);
    }



    @Override
    public Integer getScore() {
        return this.score;

    }

    @Override
    public Card play() {
        Card cardRemove = null;
        if(cards.size()>0){
         cardRemove = cards.get(0);
         cards.remove(cardRemove);
        }
        return cardRemove ;
    }




    @Override
    public void addComponent(Component component) {
        //this.cards= new ArrayList<>();
       //this.cards.add(component.getId(),component.getValue());
        this.cards.add((Card) component);
        this.score+=component.getValue();
    }

    @Override
    public void removeComponent(Component component) {

        //this.cards= new ArrayList<>();
      // this.cards.remove(component.getId(),component.getValue());
        this.cards.remove(component);
        this.score-=component.getValue();
    }

    @Override
    public List<Component> getComponents() {

       List<Component> TotalCards = new ArrayList<>();

            for (Card c : cards){
                TotalCards= (List<Component>) c;
            }
        return TotalCards;
       // return null;

    }

    @Override
    public void shuffleHand() {

        Collections.shuffle(cards);
    }

    @Override
    public void clearHand() {
        for(Card card : cards){
            cards.remove(card);
        }
    }

    @Override
    public String toString() {
        return "CardPlayer{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }

    /*@Override
    public String toString() {
        return "CardPlayer{" +
                "cards=" + cards +
                '}';
    }*/
}
