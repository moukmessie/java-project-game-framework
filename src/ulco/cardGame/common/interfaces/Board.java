package ulco.cardGame.common.interfaces;

import ulco.cardGame.common.games.components.Component;

import java.io.Serializable;
import java.util.List;

public interface Board extends Serializable {
    /**
     * Remove all components from the board (possible new round)
     */
    void clear();

    /**
     * Add component into board Game
     * @param component
     */
    void addComponent(Component component);

    /**
     * Get all components of the board Game
     * @return
     */
    List<Component> getComponents();

    /**
     * Return specific expected components depending of class name
     * @param classType
     * @return
     */
    List<Component> getSpecificComponents(Class classType);

    /**
     * Display the current board state
     */
    void displayState();
}
