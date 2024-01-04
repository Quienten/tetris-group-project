package model;

/**
 * MovablePiece Interface.
 *
 * @author Kaely Willhite
 * @version 1.0
 */
public interface MovablePiece {

    /**
     * Get the Movable Piece type of this Piece.
     *
     * @return The MovablesPiece describing this piece.
     */
    TetrisPiece getTetrisPiece();

    /**
     * The current board position of the MovablePiece.
     *
     * @return the board position.
     */
    Point getPosition();
    /**
     * Get the current rotation value of the MovablePiece.
     *
     * @return current rotation value.
     */
    Rotation getRotation();

    // methods overridden from class Object
    /**
     * Gets the TetrisPiece points rotated and translated to board coordinates.
     *
     * @return the board points for the TetrisPiece blocks.
     */
    Point[] getBoardPoints();

    // protected movement methods - used by the Board class
    /**
     * Rotates the TetrisPiece clockwise.
     *
     * @return A new rotated movable TetrisPiece
     */
    MovablePiece rotateCW();

    /**
     * Rotates the TetrisPiece counter clockwise.
     *
     * @return A new rotated movable TetrisPiece
     */
    MovablePiece rotateCCW();


    /**
     * Moves the TetrisPiece to the left on the game board.
     *
     * @return A new left moved movable TetrisPiece
     */
    MovablePiece left();

    /**
     * Moves the TetrisPiece to the right on the game board.
     *
     * @return A new right moved movable TetrisPiece
     */
    MovablePiece right();
    /**
     * Moves the TetrisPiece down on the game board.
     *
     * @return A new movable TetrisPiece moved down.
     */
    MovablePiece down();
    // This protected method is used by the Board class rotation methods
    // in order to support wall kicks during rotations
    /**
     * Returns a new MovablePiece of the current piece type and same Rotation
     * at the specified location.
     *
     * @param thePosition the location for the returned MovablePiece
     * @return A new movable TetrisPiece at the specified location
     */
    MovablePiece setPosition(Point thePosition);

}
