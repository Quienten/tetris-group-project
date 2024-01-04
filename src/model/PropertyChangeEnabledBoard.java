package model;

import java.beans.PropertyChangeListener;

/**
 * Property change interface.
 *
 * @author Quienten Miller
 * @version 1.0
 */
public interface PropertyChangeEnabledBoard extends Board {
    /*
     * Add your own constant Property values here.
     */

    /**
     * A property name for the frozen blocks on the board.
     */
    String PROPERTY_FROZEN_BLOCKS = "FROZEN_BLOCKS";
    /**
     * A property name for the current piece in play.
     */
    String PROPERTY_CURRENT_PIECE = "CURRENT_PIECE";
    /**
     * A property name for the current piece in play.
     */
    String PROPERTY_GHOST_PIECE = "GHOST_PIECE";
    /**
     * A property name for the next piece to play.
     */
    String PROPERTY_NEXT_PIECE = "NEXT_PIECE";
    /**
     * A property name for game over.
     */
    String PROPERTY_GAME_OVER = "GAME_OVER";
    /**
     * A property name for lines cleared.
     */
    String PROPERTY_LINES_CLEARED = "LINES_CLEARED";

    /**
     * A property name for pieces locked.
     */
    String PROPERTY_PIECE_LOCK = "PIECE_LOCK";

    /**
     * Add a PropertyChangeListener to the listener list. The listener is registered for
     * all properties. The same listener object may be added more than once, and will be
     * called as many times as it is added. If listener is null, no exception is thrown and
     * no action is taken.
     *
     * @param theListener The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(PropertyChangeListener theListener);


    /**
     * Add a PropertyChangeListener for a specific property. The listener will be invoked only
     * when a call on firePropertyChange names that specific property. The same listener object
     * may be added more than once. For each property, the listener will be invoked the number
     * of times it was added for that property. If propertyName or listener is null, no
     * exception is thrown and no action is taken.
     *
     * @param thePropertyName The name of the property to listen on.
     * @param theListener The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(String thePropertyName, PropertyChangeListener theListener);

    /**
     * Remove a PropertyChangeListener from the listener list. This removes a
     * PropertyChangeListener that was registered for all properties. If listener was added
     * more than once to the same event source, it will be notified one less time after being
     * removed. If listener is null, or was never added, no exception is thrown and no action
     * is taken.
     *
     * @param theListener The PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(PropertyChangeListener theListener);

    /**
     * Remove a PropertyChangeListener for a specific property. If listener was added more than
     * once to the same event source for the specified property, it will be notified one less
     * time after being removed. If propertyName is null, no exception is thrown and no action
     * is taken. If listener is null, or was never added for the specified property, no
     * exception is thrown and no action is taken.
     *
     * @param thePropertyName The name of the property that was listened on.
     * @param theListener The PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(String thePropertyName,
                                      PropertyChangeListener theListener);
}
