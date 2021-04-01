package ulco.cardGame.common.games.components;

public class Card extends Component {
    protected boolean hidden;//allows you to know if the card is returned or not

    /**
     *
     * @param name
     * @param value
     *
     */
    public Card(String name, Integer value) {

        super(name, value);
        hidden=true;
    }

}
