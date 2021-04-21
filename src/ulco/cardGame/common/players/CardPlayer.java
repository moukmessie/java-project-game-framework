package ulco.cardGame.common.players;

import ulco.cardGame.common.games.components.Card;
import ulco.cardGame.common.games.components.Component;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardPlayer extends BoardPlayer {

    private List<Component> cards;

    /**
     * Default inherited constructor
     * @param name
     */
    public CardPlayer(String name) {
        super(name);
        this.cards = new ArrayList<>();
    }

    @Override
    public Integer getScore() {
        return this.score;
    }

    @Override
    public void play(Socket socket) throws IOException {

        Card cardToPlay = (Card)cards.get(0);


        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(cardToPlay);

        // Remove card from  player hand
       // this.removeComponent(cardToPlay);

        //return cardToPlay;
    }

    @Override
    public void addComponent(Component component) {
        cards.add((Card) component);
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
    public List<Component> getSpecificComponents(Class classType) {
        // By default
        return new ArrayList<>(this.cards);
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

    /**
     * Display some expected components of player
     *  - number of Cards
     */
    @Override
    public void displayHand() {

        System.out.println("------------- Your hand -------------");
        System.out.println("Cards: " + cards.size());
        System.out.println("-------------------------------------");
    }

    @Override
    public String toString() {
        return "CardPlayer{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
