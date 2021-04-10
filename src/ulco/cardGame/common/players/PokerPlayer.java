package ulco.cardGame.common.players;

import ulco.cardGame.common.games.components.Card;
import ulco.cardGame.common.games.components.Coin;
import ulco.cardGame.common.games.components.Component;

import java.util.*;

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

       // List<String>listcoins = new ArrayList<>();
        HashMap<String, Integer> listcoins = new HashMap<>();

        for (Coin coin : coins){
            if(!listcoins.containsKey(coin.getName())){
                listcoins.put(coin.getName(), 1);
            }else {
                listcoins.replace(coin.getName(), listcoins.get(coin.getName())+1);
            }

        }

        for (Map.Entry<String, Integer>entry : listcoins.entrySet() ){
            String color = entry.getKey();
            Integer number = entry.getValue();

            System.out.println("- Coin "+ color + " x "+number);
        }

        System.out.println(getName() + " Coins sum  : " +getScore());
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
