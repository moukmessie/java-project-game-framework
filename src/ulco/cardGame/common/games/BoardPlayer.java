package ulco.cardGame.common.games;

import ulco.cardGame.common.interfaces.Player;

public abstract class BoardPlayer implements Player {
    public String name; //le pseudo associé au joueur
    public boolean playing; //un statut du joueur permettant de savoir s’il joue (associé à un jeu),

    public Integer score; // score associé au joueur


    /**
     * constructor
     * @param name
     */
    public BoardPlayer(String name) {
        this.name = name;
        this.score=0;
        this.playing=false;
    }

    /**
     *spécifie si le joueur peut jouer ou ne peut plus jouer
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
