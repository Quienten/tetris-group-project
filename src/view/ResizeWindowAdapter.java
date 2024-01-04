package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import view.components.BoardPanel;
import view.components.ControlPanel;
import view.components.NextPiecePanel;
import view.components.ScorePanel;

/**
 * Resize the main components of the tetris game.
 * @author Quienten Miller
 * @version 1.0
 */
public class ResizeWindowAdapter extends ComponentAdapter {

    /** Used to cut up the the window panel into tenths */
    private static final int TENTHS = 10;

    /** Used to make the board panel take up eight tenths */
    private static final int EIGHT = 8;

    /** The board should be a multiple of 20 to ensure pixel-perfect block scaling. */
    private static final int BLOCK_SIZE_MULTIPLE = 20;

    /** The height of the score panel */
    private static final int SCORE_HEIGHT = 240;

    /** The board panel */
    private final BoardPanel myBoardPanel;

    /** The control panel */
    private final ControlPanel myControlPanel;

    /** The next piece panel */
    private final NextPiecePanel myNextPiecePanel;

    /** The score panel */
    private final ScorePanel myScorePanel;

    /**
     * Creata resize window adapter to handle the components in the parameters.
     * @param theBoardPanel the board panel
     * @param theControlPanel the control panel
     * @param theNextPiecePanel the next piece panel
     * @param theScorePanel the score panel
     */
    public ResizeWindowAdapter(final BoardPanel theBoardPanel,
                               final ControlPanel theControlPanel,
                               final NextPiecePanel theNextPiecePanel,
                               final ScorePanel theScorePanel) {
        super();
        myBoardPanel = theBoardPanel;
        myControlPanel = theControlPanel;
        myNextPiecePanel = theNextPiecePanel;
        myScorePanel = theScorePanel;
    }


    // Using local variables that correspond to the component being resized
    // increases readability even if those variables are relative to other
    // variables.
    @SuppressWarnings("UnnecessaryLocalVariable")
    @Override
    public void componentResized(final ComponentEvent theEvent) {
        super.componentResized(theEvent);
        final Component comp = theEvent.getComponent();
        final int width = comp.getWidth();
        final int height = comp.getHeight();

        int boardHeight = (height / TENTHS) * EIGHT;
        boardHeight -= boardHeight % BLOCK_SIZE_MULTIPLE;
        final int boardWidth = boardHeight / 2;
        myBoardPanel.setPreferredSize(new Dimension(boardWidth, boardHeight));

        final int npSize = boardWidth;
        myNextPiecePanel.setPreferredSize(new Dimension(npSize, npSize));

        final int sWidth = npSize;
        final int sHeight = SCORE_HEIGHT;
        myScorePanel.setPreferredSize(new Dimension(sWidth, sHeight));

        final int cHeight = height - boardHeight - BLOCK_SIZE_MULTIPLE;
        final int cWidth = boardWidth + npSize;
        myControlPanel.setPreferredSize(new Dimension(cWidth, cHeight));

        //Set info panel parent to fit new size.
        myNextPiecePanel.getParent().setPreferredSize(
            new Dimension(npSize, npSize + sHeight));
    }
}
