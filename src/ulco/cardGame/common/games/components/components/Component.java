package ulco.cardGame.common.games.components.components;

import ulco.cardGame.common.interfaces.Player;

public abstract class Component {

    // variable which enables to count number of elements
    private static Integer nComponents = 0;
    protected Integer id;
    protected String name;
    protected Integer value;
    protected Player player;

    public Component(String name, Integer value) {
        this.name = name;
        this.value = value;
        this.id = nComponents;
        nComponents++;
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
        return "Component{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
