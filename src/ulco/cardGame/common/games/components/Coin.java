package ulco.cardGame.common.games.components;

import ulco.cardGame.common.interfaces.Player;

public class Coin extends Component{


    public Coin(Integer id, String name, Integer value) {
        super(name, value);
        this.id=id;
    }
    public Integer getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }


    @Override
    public String toString() {
        return "Coin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
