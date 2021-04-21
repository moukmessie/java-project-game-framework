package ulco.cardGame.common.interfaces;

import ulco.cardGame.common.games.components.Component;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

public interface Player extends Serializable {

    /**
     * Get name of the current Player
     * @return
     */
    String getName();

    /**
     * Get the score of the current Player
     * @return
     */
    Integer getScore();

    /**
     * Player do an action into a Game
     */
    void play(Socket socket) throws IOException;

    /**
     * Depending of game rules, specify if the player can play currently
     * @return
     */
    boolean isPlaying();

    /**
     * Specify if current user can play or not
     * @param playing
     */
    void canPlay(boolean playing);

    /**
     * Add new component linked to Player
     */
    void addComponent(Component component);

    /**
     * Remove component from Player hand
     */
    void removeComponent(Component component);

    /**
     * Get all component in Player hand
     * @return list of components
     */
    List<Component> getComponents();

    /**
     * Return specific expected components depending of class name
     * @param classType
     * @return
     */
    List<Component> getSpecificComponents(Class classType);

    /**
     * Shuffle components hand of current Player
     */
    void shuffleHand();

    /**
     * Remove all components from Player hand
     */
    void clearHand();

    /**
     * Display some expected component of player
     */
    void displayHand();
}
