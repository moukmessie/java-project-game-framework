package ulco.cardGame.common.games.components;

public class Card extends ulco.cardGame.common.games.components.Component {

   protected boolean hidden;//allows you to know if the card is returned or not

    public Card(String name, Integer value, boolean hidden) {
        super(name, value);
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                '}';
    }
}
