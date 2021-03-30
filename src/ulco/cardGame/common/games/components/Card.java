package ulco.cardGame.common.games.components;

public class Card extends Component {
    protected boolean hidden;//

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
