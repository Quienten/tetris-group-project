package view.theme;

import java.awt.Color;
import java.util.Map;
import model.TetrisPiece;

/**
 * Color theme class.
 * @author Quienten Miller
 * @version 1.0
 */
public class ColorTheme implements Theme<Color> {

    /** Map of the pieces to their colors */
    private final Map<TetrisPiece, Color> myPieceMap;

    /**
     * Color theme.
     * @param thePieceMap the piece to color map.
     */
    public ColorTheme(final Map<TetrisPiece, Color> thePieceMap) {
        super();
        myPieceMap = thePieceMap;
    }

    @Override
    public Map<TetrisPiece, Color> getPieceMap() {
        return myPieceMap;
    }

    @Override
    public Color get(final TetrisPiece thePiece) {
        return myPieceMap.getOrDefault(thePiece, Color.WHITE);
    }

    @Override
    public Color get(final String thePieceString) {
        final TetrisPiece piece = TetrisPiece.valueOf(thePieceString);
        return get(piece);
    }
}
