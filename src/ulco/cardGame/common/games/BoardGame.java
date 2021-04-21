package ulco.cardGame.common.games;

import ulco.cardGame.common.interfaces.Board;
import ulco.cardGame.common.interfaces.Game;
import ulco.cardGame.common.interfaces.Player;
import ulco.cardGame.common.players.BoardPlayer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class BoardGame implements Game {

    protected String name;
    protected Integer maxPlayers;
    protected List<Player> players;
    protected boolean endGame;
    protected boolean started;
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
        this.endGame = false;
        this.players = new ArrayList<>();

        // initialize the Board Game using
        this.initialize(filename);
    }



    /**
     * Add new players inside the game only if possible
     * @param player
     */
    @Override
    public boolean addPlayer(Player player, Socket socket) throws IOException {

        // check number of authorized players in the game
        if (this.players.size() < maxPlayers) {

            // check if username already exists
            long identical = this.players.stream().filter(x -> x.getName().equals(player.getName())).count();

            if (identical > 0) {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
              oos.writeObject("assigned");

                return false;
            }

            // enable the fact player is now playing (playing state)
            player.canPlay(true);

            this.players.add((BoardPlayer) player);
            System.out.println("Player added into players game list");

        }
        else {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("Maximum");
            System.out.println("Maximum number of players already reached (max: " + maxPlayers.toString() + ")");
            return false;
        }

        if (players.size() >= maxPlayers) {
            started = true;
        }

        return true;
    }

    /**
     * Remove player from the game using the reference
     * @param player
     */
    @Override
    public void removePlayer(Player player) {
        // not forget to disconnect the player
        this.players.remove(player);
    }

    /**
     * Remove players from the game
     */
    @Override
    public void removePlayers(){
        // not forget to disconnect the player
        this.players.clear();
    }
    public Player getCurrentPlayer(String username){
        for (Player player : players) {
            if (player.getName().equals(username)) {
                return player;
            }
        }
        return null;
    }
    @Override
    public void displayState() {

        // Display Game state
        System.out.println("-------------------------------------------");
        System.out.println("--------------- Game State ----------------");
        System.out.println("-------------------------------------------");
        for (Player currentPlayer : players) {
            System.out.println(currentPlayer);
        }
        System.out.println("-------------------------------------------");
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public Integer maxNumberOfPlayers() {
        return maxPlayers;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public Board getBoard() {
        return board;
    }
}
