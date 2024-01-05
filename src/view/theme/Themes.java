package view.theme;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.imageio.ImageIO;
import model.TetrisPiece;

/**
 * Holds all the game's themes.
 * @author Quienten Miller
 * @version 1.0
 */
public final class Themes {

    /** The classic theme. */
    public static final ColorTheme CLASSIC;

    /** The game over theme. */
    public static final ColorTheme GAME_OVER;

    /** The husky theme. */
    public static final ColorTheme HUSKY;

    /** The Christmas theme. */
    public static final ColorTheme CHRISTMAS;

    /** The blox theme. */
    public static final ImageTheme BLOX;

    /** Husky purple color. */
    private static final Color HUSKY_PURPLE = Color.decode("#4B2E83");
    /** Husky gold color. */
    private static final Color HUSKY_GOLD = Color.decode("#85754D");


    private Themes() {
        super();
    }


    static {
        final Map<TetrisPiece, Color> defaultMap = new HashMap<>();
        defaultMap.put(TetrisPiece.I, Color.CYAN);
        defaultMap.put(TetrisPiece.J, Color.BLUE);
        defaultMap.put(TetrisPiece.L, Color.ORANGE);
        defaultMap.put(TetrisPiece.O, Color.YELLOW);
        defaultMap.put(TetrisPiece.S, Color.GREEN);
        defaultMap.put(TetrisPiece.T, Color.MAGENTA);
        defaultMap.put(TetrisPiece.Z, Color.RED);

        CLASSIC = new ColorTheme(defaultMap);
    }

    static {
        final Map<TetrisPiece, Color> gameOverMap = new HashMap<>();
        gameOverMap.put(TetrisPiece.I, Color.DARK_GRAY);
        gameOverMap.put(TetrisPiece.J, Color.DARK_GRAY);
        gameOverMap.put(TetrisPiece.L, Color.DARK_GRAY);
        gameOverMap.put(TetrisPiece.O, Color.DARK_GRAY);
        gameOverMap.put(TetrisPiece.S, Color.DARK_GRAY);
        gameOverMap.put(TetrisPiece.T, Color.DARK_GRAY);
        gameOverMap.put(TetrisPiece.Z, Color.DARK_GRAY);

        GAME_OVER = new ColorTheme(gameOverMap);
    }

    static {
        final Map<TetrisPiece, Color> huskyMap = new HashMap<>();
        huskyMap.put(TetrisPiece.I, HUSKY_PURPLE);
        huskyMap.put(TetrisPiece.J, HUSKY_GOLD);
        huskyMap.put(TetrisPiece.L, HUSKY_PURPLE);
        huskyMap.put(TetrisPiece.O, HUSKY_GOLD);
        huskyMap.put(TetrisPiece.S, HUSKY_PURPLE);
        huskyMap.put(TetrisPiece.T, HUSKY_GOLD);
        huskyMap.put(TetrisPiece.Z, HUSKY_PURPLE);

        HUSKY = new ColorTheme(huskyMap);
    }

    static {
        final Map<TetrisPiece, Color> christmasMap = new HashMap<>();
        christmasMap.put(TetrisPiece.I, Color.decode("#A76E24"));
        christmasMap.put(TetrisPiece.J, Color.decode("#EDC766"));
        christmasMap.put(TetrisPiece.L, Color.decode("#EF3532"));
        christmasMap.put(TetrisPiece.O, Color.decode("#721013"));
        christmasMap.put(TetrisPiece.S, Color.decode("#58681E"));
        christmasMap.put(TetrisPiece.T, Color.decode("#303818"));
        christmasMap.put(TetrisPiece.Z, Color.WHITE);

        CHRISTMAS = new ColorTheme(christmasMap);
    }

    static {
        final Map<TetrisPiece, BufferedImage> bloxMap = new HashMap<>();

        bloxMap.put(TetrisPiece.I, loadBlockImage("Cyan"));
        bloxMap.put(TetrisPiece.J, loadBlockImage("Blue"));
        bloxMap.put(TetrisPiece.L, loadBlockImage("Orange"));
        bloxMap.put(TetrisPiece.O, loadBlockImage("Yellow"));
        bloxMap.put(TetrisPiece.S, loadBlockImage("Green"));
        bloxMap.put(TetrisPiece.T, loadBlockImage("Magenta"));
        bloxMap.put(TetrisPiece.Z, loadBlockImage("Red"));

        BLOX = new ImageTheme(bloxMap);
    }

    private static BufferedImage loadBlockImage(final String theImgPrefix) {
        final String fileName = theImgPrefix + "Block.png";

        final String imagesFolder = "images" + File.separator;

        BufferedImage blockImg = null;

        try {
            blockImg = ImageIO.read(
                Objects.requireNonNull(Themes.class.getResource("/" + fileName))
            );
        } catch (final IOException ignored) {

        }
        return blockImg;
    }


}
