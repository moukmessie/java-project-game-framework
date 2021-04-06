package ulco.cardGame.common.players;

import ulco.cardGame.common.interfaces.Player;

public abstract class BoardPlayer implements Player {
    public String name; //nickname associated whith the player
    public boolean playing; // player's status allowing them to know if they are playing

    public Integer score; // score associated with the player


    /**
     * constructor
     * @param name
     */
    public BoardPlayer(String name) {
        this.name = name;
        this.score=0;
        // currently player is not playing...
        this.playing=false;
    }

    /**
     * specifies whether the player can play or can no longer play
     * @param playing
     */
    public void canPlay(boolean playing){
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

        String status = isPlaying()? " Joue ": "Ne joue pas ";
        String msg = String.format("Score : %d Player : %s  %s",getScore(),getName());

        return msg+status;
    }
}
