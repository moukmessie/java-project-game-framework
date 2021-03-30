package ulco.cardGame.common.interfaces;

import ulco.cardGame.common.games.components.Component;

import java.util.List;

public interface Player {

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
    Component play();

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
     * Shuffle components hand of current Player
     */
    void shuffleHand();

    /**
     * Remove all components from Player hand
     */
    void clearHand();
}
