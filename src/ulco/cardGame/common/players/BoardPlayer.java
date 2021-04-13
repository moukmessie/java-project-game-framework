package ulco.cardGame.common.players;

import ulco.cardGame.common.interfaces.Player;

public abstract class BoardPlayer implements Player {

    protected String name;
    protected boolean playing;
    protected Integer score;

    public BoardPlayer(String name) {
        this.name = name;
        this.score = 0;

        // currently player is not playing...
        this.playing = false;
    }

    @Override
    public void canPlay(boolean playing) {
        this.playing = playing;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isPlaying() {
        return playing;
    }

    @Override
    public String toString() {
        return "BoardPlayer{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
