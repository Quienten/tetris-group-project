package view.theme;

import java.awt.image.BufferedImage;
import java.util.Map;
import model.TetrisPiece;

/**
 * ImageTheme.
 * @author Quienten Miller
 * @version 1.0
 */
public class ImageTheme implements Theme<BufferedImage> {

    /** Map of the pieces to their BufferedImages */
    private final Map<TetrisPiece, BufferedImage> myPieceMap;

    /**
     * Create a image theme.
     * @param thePieceMap the piece to image map.
     */
    public ImageTheme(final Map<TetrisPiece, BufferedImage> thePieceMap) {
        super();
        this.myPieceMap = thePieceMap;
    }

    @Override
    public Map<TetrisPiece, BufferedImage> getPieceMap() {
        return myPieceMap;
    }

    @Override
    public BufferedImage get(final TetrisPiece thePiece) {
        if (myPieceMap.containsKey(thePiece)) {
            return myPieceMap.get(thePiece);
        }
        throw new IllegalArgumentException("TetrisPiece not found in piece map");
    }

    @Override
    public BufferedImage get(final String thePieceString) {
        final TetrisPiece piece = TetrisPiece.valueOf(thePieceString);
        return get(piece);
    }
}
