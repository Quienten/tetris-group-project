package view.components;

/**
 * The Board interface for use with the BoardPanel.
 * @author Kaely Willhite
 * @version 1.0
 */
@FunctionalInterface
public interface Board {

    /**
     * Resets the Display.
     */
    void setDisplaySplash(boolean theNewDisplaySplash);
}
