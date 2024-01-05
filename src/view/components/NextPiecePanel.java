package view.components;

import static model.PropertyChangeEnabledBoard.PROPERTY_NEXT_PIECE;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.Point;
import model.TetrisPiece;
import view.theme.ColorTheme;
import view.theme.ImageTheme;
import view.theme.TetrisThemeController;
import view.theme.Theme;

/**
 * The NextPiecePanel shows the incoming piece to play.
 * @author Mel Harvey
 * @version 1.0
 */
public final class NextPiecePanel extends JPanel implements PropertyChangeListener {

    /** The stroke of the block */
    private static final int BLOCK_STROKE = 2;

    /** The width (in pixels) of the panel */
    private static final int WIDTH = 240;

    /** The height (in pixels) of the panel */
    private static final int HEIGHT = 240;

    /** The center x for odd pieces. */
    private static final double CENTER_X_ODD = 3.5;

    /** The center x for even pieces. */
    private static final int CENTER_X_EVEN = 3;

    /** Default block size */
    private static final int DEFAULT_BLOCK_SIZE = 24;

    /** Board height */
    private static final int BOARD_HEIGHT = 10;


    /** Represents the next piece in the panel */
    private TetrisPiece myNextPiece;

    /** The size to draw the block */
    private int myBlockSize = DEFAULT_BLOCK_SIZE;



    /** The background image to draw */
    private BufferedImage myBackground;

    /**
     * Creates the NextPiecePanel.
     */
    public NextPiecePanel() {
        super();
        try {
            loadResources();
        } catch (final IOException ignored) {

        }
        setupPanel();
    }

    private void setupPanel() {
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent theEvent) {
                super.componentResized(theEvent);
                myBlockSize = getHeight() / BOARD_HEIGHT;
            }
        });
    }

    private void loadResources() throws IOException {
        final String imagesFolder = "images" + File.separator;
        myBackground = ImageIO.read(
            Objects.requireNonNull(getClass().getResource("/GameBoardBackground.png"))
        );
    }

    //Java Swing doesn't use an interface for this method.
    @SuppressWarnings("PublicMethodNotExposedInInterface")
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(myBackground, 0, 0, getWidth(), getHeight(), null);

        if (myNextPiece != null) {
            drawNextPiece(g2d);
        }
    }

    private void drawNextPiece(final Graphics2D theGraphics2D) {
        for (final Point p : myNextPiece.getPoints()) {
            final Theme<?> currentTheme = TetrisThemeController.getInstance().getTheme();

            int x = p.x() * myBlockSize + (CENTER_X_EVEN * myBlockSize);

            if (myNextPiece.getWidth() % 2 == 1 && myNextPiece != TetrisPiece.O) {
                x = (int) (p.x() * myBlockSize + (CENTER_X_ODD * myBlockSize));
            }

            final int y = -p.y() * myBlockSize + (6 * myBlockSize);

            if (currentTheme instanceof ColorTheme) {
                drawRectangle(theGraphics2D, x, y,
                    ((ColorTheme) currentTheme).get(myNextPiece));
            } else if (currentTheme instanceof ImageTheme) {
                theGraphics2D.drawImage(((ImageTheme) currentTheme).get(myNextPiece),
                    x, y, myBlockSize, myBlockSize, null);
            }


        }
    }

    private void drawRectangle(final Graphics2D theGraphics2D, final int theX, final int theY,
                               final Color theColor) {
        final Shape rectangle = new Rectangle2D.Double(theX, theY, myBlockSize, myBlockSize);

        theGraphics2D.setPaint(theColor);
        theGraphics2D.fill(rectangle);
        theGraphics2D.setPaint(Color.BLACK);
        theGraphics2D.setStroke(new BasicStroke(BLOCK_STROKE));
        theGraphics2D.draw(rectangle);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals(PROPERTY_NEXT_PIECE)) {
            myNextPiece = (TetrisPiece) theEvent.getNewValue();
            repaint();
        }
    }
}
