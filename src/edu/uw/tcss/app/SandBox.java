package edu.uw.tcss.app;

import model.TetrisBoard;

/**
 * Sandbox Class.
 *
 * @author Bhavneet Bhargava, Mel Harvey, Kaely Willhite
 * @version Autumn 2023
 */
public final class SandBox {

    private SandBox() {
        super();
    }

    /**
     * Main method for the SandBox.
     *
     * @param theArgs string that comes into this method
     */
    public static void main(final String[] theArgs) {
        final TetrisBoard b = TetrisBoard.getInstance();
        b.newGame();

        b.step();

        b.rotateCW();
        b.rotateCW();
        b.rotateCW();
        b.rotateCW();
        b.drop();


    }

}
