package ulco.cardGame.common.games;

import ulco.cardGame.common.interfaces.Board;
import ulco.cardGame.common.interfaces.Game;
import ulco.cardGame.common.interfaces.Player;
import ulco.cardGame.common.players.BoardPlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class BoardGame implements Game {
    protected String name; //name associate with the game
    protected Integer maxPlayers; //max player number in the game

    protected List<Player> players; //list players
    protected boolean endGame; //endgame's status
    protected boolean started; //started's game status
    protected Board board;


    /**
     * Enable constructor of Game
     * - Name of the game
     * - Maximum number of players of the Game
     * - Filename with all required information to load the Game
     * @param name
     * @param maxPlayers
     * @param filename
     */
    public BoardGame(String name, Integer maxPlayers, String filename) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.endGame=false;
        this.players = new ArrayList<>();

        // initialize the Board Game using
        initialize(filename);

    }

    /**
     *add new  player inside the game only if possible
     * @param player
     */
    public boolean addPlayer(Player player){

        // check number of authorized players in the game
        if (this.players.size() < maxPlayers) {

            // check if username already exists
            long identical = this.players.stream().filter(x -> x.getName().equals(player.getName())).count();

            if (identical > 0) {
                return false;
            }

            // enable the fact player is now playing (playing state)
            player.canPlay(true);

            this.players.add((BoardPlayer) player);
            System.out.println("Player added into players game list");
        }
        else {
            System.out.println("Maximum number of players already reached (max: " + maxPlayers.toString() + ")");
            return false;
        }

        if (players.size() >= maxPlayers) {
            started = true;
        }

        return true;

    }

    /**
     * remove player from the game using the reference
     * @param player
     */
    public void removePlayer(Player player) {
        // not forget to disconnect the player
        this.players.remove(player);
    }

    /**
     * remove players from the game
     */
    public void removePlayers(){
        // not forget to disconnect the player
        this.players.clear();
    }

    /**
     * view the status game/ view the game players
     */
    public void displayState(){
        // Display Game state
        System.out.println("-------------------------------------------");
        System.out.println("--------------- Game State ----------------");
        System.out.println("-------------------------------------------");

        for(Player player : players){
            System.out.println(player);
        }
        System.out.println("-------------------------------------------");

    }

    /***
     * view the status game if is started or not
     * @return
     */
    public boolean isStarted(){
        return started;
    }

    /**
     * return the max number of players
     */
    public Integer maxNumberOfPlayers(){
        return this.maxPlayers;
    }

    /**
     * get the players Link game
     */
    public List<Player> getPlayers(){
      return this.players;
    }

    /**
     *
     * @return the board game
     */
    public Board getBoard() {
        return this.board;
    }
}
