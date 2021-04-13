package ulco.cardGame.common.interfaces;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public interface Game extends Serializable {

    /**
     * Initialize the whole game using a parameter file
     * @param filename
     */
    void initialize(String filename);

    /**
     *
     * @return
     */
    Player run();
    //Player run(Map<Player, Socket>);
    /**
     * Add player to the current Game
     * @param player
     */
    boolean addPlayer(Player player,Socket soket) throws IOException;

    /**
     * Remove player from the game using the reference
     * @param player
     */
    void removePlayer(Player player);

    /**
     * Remove players from the game
     */
    void removePlayers();

    /**
     * Return player from current Game
     * @return
     */
    List<Player> getPlayers();

    /**
     * Specify if game is started or not
     * @return
     */
    boolean isStarted();

    /**
     * current number of players inside the Game
     * @return
     */
    Integer maxNumberOfPlayers();

    /**
     * Specify if the Game has end or not
     */
    boolean end();

    /**
     * Display current Game state
     * Such as user state (name and score)
     */
    void displayState();

    /**
     * Get access to the current board of the Game
     */
    Board getBoard();
}
