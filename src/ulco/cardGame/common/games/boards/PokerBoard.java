package ulco.cardGame.common.games.boards;

import ulco.cardGame.common.games.components.Card;
import ulco.cardGame.common.games.components.Coin;
import ulco.cardGame.common.games.components.Component;
import ulco.cardGame.common.interfaces.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokerBoard implements Board {
    List<Card> cards;
    List<Coin>coins;

    /**
     *  PokerBoard constructor
     */
    public PokerBoard() {
        this.cards = new ArrayList<>();
        this.coins= new ArrayList<>();

    }

    /**
     * List cleaning
      */
    @Override
    public void clear() {
       this.cards.clear();
       this.coins.clear();
    }

    /**
     * add component in List / cards or coins
     * @param component
     */
    @Override
    public void addComponent(Component component) {
      if(component.getClass() == Card.class){
          this.cards.add((Card)component);
      }else if (component.getClass() == Coin.class) {
          this.coins.add((Coin) component);
      }

    }

    /**
     * get all component cards and coins
     * @return
     */
    @Override
    public List<Component> getComponents() {
        List<Component>componentList = new ArrayList<>();
       componentList.addAll(coins);
       componentList.addAll(cards);
        return componentList;
    }

    /**
     * get different component list cards or coins
     * @param classType
     * @return
     */
    @Override
    public List<Component> getSpecificComponents(Class classType) {

        if (classType == Card.class){
            return new ArrayList<>(cards);
        }else if (classType == Coin.class){
            return new ArrayList<>(coins);
        }
       return null;
    }

    /**
     * state Poker board view
     */

    @Override
    public void displayState() {
        Integer sum=0;
        System.out.println("-------------------------------------------");
        System.out.println("--------------- Board State ----------------");
        System.out.println("-------------------------------------------");
        //view cards
        for(Card card : cards){

           System.out.println("Card :" + card.getName());
        }

        System.out.println("-------------------------------------------");

        List<String>listcoins = new ArrayList<>();

        for (Coin coin : coins){
            listcoins.add(coin.getName());
            sum+=coin.getValue();
        }

        for(int i=0; i<listcoins.size();i++){
            System.out.println("- Coin "+ listcoins.get(i)+ " x "+ Collections.frequency(listcoins,listcoins.get(i)));
        }

        System.out.println("Your Coins sum placed is about : "+sum);
        System.out.println("-------------------------------------------");


    }
}
