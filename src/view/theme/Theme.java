package view.theme;

import java.util.Map;
import model.TetrisPiece;

/**
 * Defines a theme.
 * @param <K> What to map a tetris piece to.
 * @author Quienten Miller
 * @version 1.0
 */
public interface Theme<K> {

    /**
     * Gets a map of piece to something that a piece can use to determine how to appear.
     * @return the piece map.
     */
    Map<TetrisPiece, K> getPieceMap();

    /**
     * Get the theme information from the piece map.
     * @param thePiece thePiece to look up.
     * @return K that TetrisPiece should be represented as
     */
    K get(TetrisPiece thePiece);

    /**
     * Get the theme information from the piece map using a string represntation.
     * @param thePieceString the string representation of the piece to look up.
     * @return K that TetrisPiece should be represented as
     */
    K get(String thePieceString);

}
