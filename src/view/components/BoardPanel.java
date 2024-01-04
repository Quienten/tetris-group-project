package view.components;

import static model.PropertyChangeEnabledBoard.PROPERTY_CURRENT_PIECE;
import static model.PropertyChangeEnabledBoard.PROPERTY_FROZEN_BLOCKS;
import static model.PropertyChangeEnabledBoard.PROPERTY_GHOST_PIECE;

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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.Block;
import model.MovableTetrisPiece;
import model.Point;
import view.theme.ColorTheme;
import view.theme.ImageTheme;
import view.theme.TetrisThemeController;
import view.theme.Theme;

/**
 * The BoardPanel draws the game board.
 * @author Quienten Miller
 * @version 1.0
 */
public final class BoardPanel extends JPanel implements PropertyChangeListener, Board {
    /** The default width and the height of the block */
    private static final int BLOCK_SIZE = 24;
    /** The stroke of the block (black outline) */
    private static final int BLOCK_STROKE = 2;
    /** The hidden "ghost" height of the board grid */
    private static final int BOARD_GHOST_HEIGHT = 4;
    /** The total height (visible and not visible) of the board grid */
    private static final int BOARD_HEIGHT = 20 + BOARD_GHOST_HEIGHT;
    /** The width of the board grid */
    private static final int BOARD_WIDTH = 10;
    /** The height of the panel (in pixels) */
    private static final int HEIGHT = 480;
    /** The total height (visible) of the board grid */
    private static final int VISIBLE_BOARD_HEIGHT = 20;
    /** The width of the panel (in pixels) */
    private static final int WIDTH = 240;
    /** The width and the height of the block */
    private int myBlockSize = BLOCK_SIZE;
    /** The currentPiece to be updated by the property change of PROPERTY_CURRENT_PIECE */
    private MovableTetrisPiece myCurrentPiece;

    /** The ghostPiece to be updated by the property change of PROPERTY_GHOST_PIECE */
    private MovableTetrisPiece myGhostPiece;

    /** The frozenBlocks to be updated by the property change of PROPERTY_FROZEN_BLOCKS */
    private List<Block[]> myFrozenBlocks;

    /** Whether or not to display the splash screen. */
    private boolean myDisplaySplash;

    /** BufferedImage used to store "TetrisTitleScreen.png" */
    private BufferedImage mySplashScreenImage;

    /** BufferedImage used to store "GameBoardBackground.png" */
    private BufferedImage myGameBackground;

    /**
     * Setup the board panel.
     */
    public BoardPanel() {
        super();
        setupPanel();
        try {
            loadResources();
        } catch (final IOException ignored) {

        }
    }


    @Override
    public void setDisplaySplash(final boolean theNewDisplaySplash) {
        myDisplaySplash = theNewDisplaySplash;
        repaint();
    }

    private void setupPanel() {
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent theEvent) {
                super.componentResized(theEvent);
                myBlockSize = getHeight() / VISIBLE_BOARD_HEIGHT;
            }
        });
    }

    private void loadResources() throws IOException {
        final String imagesFolder = "images" + File.separator;
        mySplashScreenImage = ImageIO.read(new File(imagesFolder
            + "TetrisTitleScreen.png"));

        myGameBackground = ImageIO.read(new File(imagesFolder
            + "GameBoardBackground.png"));
    }

    //Java Swing doesn't use an interface for this method.
    @SuppressWarnings("PublicMethodNotExposedInInterface")
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        //Draw background
        g2d.drawImage(myGameBackground, 0, 0, getWidth(), getHeight(), null);

        if (myDisplaySplash) {
            g2d.drawImage(mySplashScreenImage, 0, 0, getWidth(), getHeight(), null);
        }

        if (myCurrentPiece != null) {
            drawPiece(g2d, myCurrentPiece);
        }
        if (myGhostPiece != null) {
            drawGhostPiece(g2d, myGhostPiece);
        }
        if (myFrozenBlocks != null) {
            drawFrozenBlocks(g2d);
        }
    }

    private void drawPiece(final Graphics2D theGraphics2D, final MovableTetrisPiece thePiece) {
        for (final Point p : thePiece.getBoardPoints()) {
            final Theme<?> currentTheme = TetrisThemeController.getInstance().getTheme();


            if (currentTheme instanceof ColorTheme) {
                final Shape rectangle = new Rectangle2D.Double(
                        p.x() * myBlockSize,
                        (VISIBLE_BOARD_HEIGHT - p.y() - 1) * myBlockSize,
                        myBlockSize, myBlockSize);

                final Color blockColor =
                        ((ColorTheme) currentTheme).get(thePiece.getTetrisPiece());

                theGraphics2D.setPaint(blockColor);
                theGraphics2D.fill(rectangle);
                theGraphics2D.setPaint(Color.BLACK);
                theGraphics2D.setStroke(new BasicStroke(BLOCK_STROKE));
                theGraphics2D.draw(rectangle);
            } else if (currentTheme instanceof ImageTheme) {
                theGraphics2D.drawImage(
                        ((ImageTheme) currentTheme).get(thePiece.getTetrisPiece()),
                        p.x() * myBlockSize,
                        (VISIBLE_BOARD_HEIGHT - p.y() - 1) * myBlockSize,
                        myBlockSize,
                        myBlockSize,
                        null);

            }



        }
    }

    private void drawGhostPiece(final Graphics2D theGraphics2D,
                                final MovableTetrisPiece thePiece) {
        for (final Point p : thePiece.getBoardPoints()) {
            final Shape rectangle = new Rectangle2D.Double(
                    p.x() * myBlockSize,
                    (VISIBLE_BOARD_HEIGHT - p.y() - 1) * myBlockSize,
                    myBlockSize, myBlockSize);

            theGraphics2D.setPaint(Color.BLACK);
            theGraphics2D.setStroke(new BasicStroke(BLOCK_STROKE));
            theGraphics2D.draw(rectangle);
        }
    }

    private void drawFrozenBlocks(final Graphics2D theGraphics2D) {
        for (int y = 0; y < myFrozenBlocks.size(); y++) {
            for (int x = 0; x < myFrozenBlocks.get(y).length; x++) {
                if (myFrozenBlocks.get(y)[x] != null) {

                    final Block block = myFrozenBlocks.get(y)[x];

                    final Theme<?> currentTheme =
                            TetrisThemeController.getInstance().getTheme();

                    if (currentTheme instanceof ColorTheme) {
                        final Shape rectangle = new Rectangle2D.Double(
                                x * myBlockSize,
                                (VISIBLE_BOARD_HEIGHT - y - 1) * myBlockSize,
                                myBlockSize, myBlockSize);


                        final Color color = ((ColorTheme) currentTheme).get(block.toString());

                        theGraphics2D.setPaint(color);
                        theGraphics2D.fill(rectangle);
                        theGraphics2D.setPaint(Color.BLACK);
                        theGraphics2D.setStroke(new BasicStroke(BLOCK_STROKE));
                        theGraphics2D.draw(rectangle);
                    } else if (currentTheme instanceof ImageTheme) {
                        theGraphics2D.drawImage(
                                ((ImageTheme) currentTheme).get(block.toString()),
                                x * myBlockSize,
                                (VISIBLE_BOARD_HEIGHT - y - 1) * myBlockSize,
                                myBlockSize,
                                myBlockSize,
                                null);
                    }



                }
            }
        }
    }

    //suppressed as all casts are checked.
    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals(PROPERTY_FROZEN_BLOCKS)) {
            myFrozenBlocks = Collections.unmodifiableList((LinkedList<Block[]>)
                    theEvent.getNewValue());
            repaint();
        }
        if (theEvent.getPropertyName().equals(PROPERTY_CURRENT_PIECE)) {
            myCurrentPiece = (MovableTetrisPiece) theEvent.getNewValue();
            repaint();
        }
        if (theEvent.getPropertyName().equals(PROPERTY_GHOST_PIECE)) {
            myGhostPiece = (MovableTetrisPiece) theEvent.getNewValue();
            repaint();
        }

    }
}
