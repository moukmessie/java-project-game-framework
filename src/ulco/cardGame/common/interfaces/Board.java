package ulco.cardGame.common.interfaces;

import ulco.cardGame.common.games.components.Component;

import java.util.List;

public interface Board {

    /**
     * Clear Board game
     */
     void clear();

    /**
     * Add new component linked to Board
     */
     void addComponent(Component component);

    /**
     * Get  component in Board
     * @return list of components
     */
    List<Component> getComponents();

    /**
     *
     * @param classType
     * @return cards
     */
    List<Component> getSpecificComponents(Class classType);

    /**
     * Display current Game state
     */
    void displayState();


}
