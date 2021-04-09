package ulco.cardGame.common.players;

import ulco.cardGame.common.games.components.Card;
import ulco.cardGame.common.games.components.Coin;
import ulco.cardGame.common.games.components.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PokerPlayer extends BoardPlayer{
        List<Card>cards;
        List<Coin>coins;

    /**
     * constructor
     *
     * @param name
     */
    public PokerPlayer(String name) {
        super(name);
        cards = new ArrayList<>();
        coins = new ArrayList<>();
    }

    @Override
    public Integer getScore() {
        return this.getScore();
    }

    @Override
    public Component play() {
        Scanner scanner = new Scanner(System.in);
        boolean scan = false;
        Coin coin =null;

        while (!scan){
            System.out.println("["+getName()+"]"+ ", please select a valid Coin to play (Coin color)");
            String color = scanner.nextLine();
            for (Coin c : coins){
                if (coin.getName().equals(color)){
                    scan=true;
                    coin = c;
                    break;
                }
            }

        }
        this.removeComponent(coin);
        return coin;
    }

    @Override
    public void addComponent(Component component) {
        if (component instanceof Card){
            cards.add((Card)component);
        }else if (component instanceof Coin){
            coins.add((Coin)component);
            score+=component.getValue();
        }
    }

    @Override
    public void removeComponent(Component component) {
        if (component instanceof Card){
            cards.remove((Card)component);
        }else if (component instanceof Coin){
            coins.remove((Coin)component);
            score-=component.getValue();
        }

    }

    @Override
    public List<Component> getComponents() {
        List<Component>componentList = new ArrayList<>();
        componentList.addAll(cards);
        componentList.addAll(coins);
        return componentList;
    }

    public List<Component> getSpecificComponents(Class classType){
        if (classType == Card.class){
            return new ArrayList<>(cards);
        }else if (classType == Coin.class){
            return new ArrayList<>(coins);
        }
        return null;
    }

    @Override
    public void shuffleHand() {
        Collections.shuffle(cards);

    }

    @Override
    public void clearHand() {
        this.cards.clear();
    }
    public void displayHand(){
        Integer sum=0;
        System.out.println("------------------------------------------");
        System.out.println("-------- Hand of [" +getName() +"] --------");
        System.out.println("-------------------------------------------");
        for (Component card : cards){
            System.out.println("Card: "+card.getName());
        }
        System.out.println("-----------------*********-----------------");

        List<String>listcoins = new ArrayList<>();

        for (Coin coin : coins){
            listcoins.add(coin.getName());
            sum+=coin.getValue();
        }

        for(int i=0; i<listcoins.size();i++){
            System.out.println("- Coin "+ listcoins.get(i)+ " x "+ Collections.frequency(listcoins,listcoins.get(i)));
        }

        System.out.println("Your Coins sum placed is about : "+sum);
        System.out.println("---------------***********-----------------");


    }

    @Override
    public String toString() {
        return "PokerPlayer{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
