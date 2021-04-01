package ulco.cardGame.common.games.components;

public class Card extends Component {
    protected boolean hidden;//permet de dire si la carte est retournée ou non

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
