package model;

/**
 * TetrisPoint Interface.
 *
 * @author Mel Harvey
 * @version Autumn 2023
 */
public interface TetrisPoint {

    /**
     * Returns the X coordinate.
     *
     * @return the X coordinate of the Point.
     */
    int x();

    /**
     * Returns the Y coordinate.
     *
     * @return the Y coordinate of the Point.
     */
    int y();

    /**
     * Creates a new point transformed by x and y.
     *
     * @param theX the X factor to transform by.
     * @param theY the Y factor to transform by.
     * @return the new transformed Point.
     */
    TetrisPoint transform(int theX, int theY);

    /**
     * Creates a new point transformed by another Point.
     *
     * @param theTetrisPoint the Point to transform with.
     * @return the new transformed Point.
     */
    TetrisPoint transform(Point theTetrisPoint);
}
