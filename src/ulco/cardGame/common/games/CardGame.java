package ulco.cardGame.common.games;

import ulco.cardGame.common.interfaces.Player;

import javax.smartcardio.Card;
import java.util.List;

public class CardGame extends BoardGame{

    private List<Card>cards;

    public CardGame(String name, Integer maxPlayer, String filename) {
        super(name, maxPlayer, filename);
    }

    @Override
    public void initialize(String filename) {

    }

    @Override
    public Player run() {
        return null;
    }

    @Override
    public boolean end() {
        return false;
    }
}
