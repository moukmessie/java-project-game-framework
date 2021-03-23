package ulco.cardGame.common.games.components.components;

import ulco.cardGame.common.games.BoardPlayer;
import ulco.cardGame.common.interfaces.Player;

import java.util.ArrayList;
import java.util.List;

public  class CardPlayer extends BoardPlayer {

public List<Card> cards;

    public CardPlayer(String name) {
        super(name);

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Integer getScore() {
        return this.score;
    }

    @Override
    public Component play() {
         cards.get(0);
        return  cards.remove(0);
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void canPlay(boolean playing) {

        playing=true;

    }

    @Override
    public void addComponent(Component component) {
        this.cards= new ArrayList<>();
       //this.cards.add(component.getId(),component.getValue());
        this.score+=component.getValue();
    }

    @Override
    public void removeComponent(Component component) {

        this.cards= new ArrayList<>();
      // this.cards.remove(component.getId(),component.getValue());
        this.score-=component.getValue();
    }

    @Override
    public List<Component> getComponents() {


       /* List<Component>TotalCards = new ArrayList<>();

            for (int i=0; i<getComponents().size();i++){
                TotalCards= cards.get(i);
            }

        return TotalCards;*/
        return null;

    }

    @Override
    public void shuffleHand() {

    }

    @Override
    public void clearHand() {

    }
}
