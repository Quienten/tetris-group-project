package view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The ScorePanel shows the score of the game so far.
 * @author Arthur Fornia
 * @version 1.1
 */
public final class ScorePanel extends JPanel implements Score {
    /** The width (in pixels) of the panel */
    private static final int WIDTH = 240;
    /** The width (in pixels) of the panel */
    private static final int HEIGHT = 240;

    /** The font size for the labels */
    private static final int FONT_SIZE = 20;

    /** The number of lines cleared */
    private int myLinesCleared;

    /** The current score */
    private int myScore;

    /** The current level */
    private int myLevel;

    /** The label for the current score */
    private JLabel myScoreLabel;

    /** The label for the number of lines cleared */
    private JLabel myLinesLabel;

    /** The label for the current level */
    private JLabel myLevelLabel;

    /**
     * Creates the ControlsPanel.
     */
    public ScorePanel() {
        super();
        setupPanel();
    }

    /**
     * Sets up the data in the ControlsPanel.
     */
    private void setupPanel() {
        setBackground(Color.decode("#151037"));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalGlue());
        myScoreLabel = makeLabel();
        myLinesLabel = makeLabel();
        myLevelLabel = makeLabel();
        add(Box.createVerticalGlue());

        reset();
    }

    private JLabel makeLabel() {
        final JLabel label = new JLabel();
        label.setFont(new Font(label.getFont().getFontName(), Font.PLAIN, FONT_SIZE));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        add(label);
        return label;
    }

    /**
     * Fills the text for the panel.
     */
    private void updateText() {
        myScoreLabel.setText("Score: " + myScore);
        myLinesLabel.setText("Lines: " + myLinesCleared);
        myLevelLabel.setText("Level: " + myLevel);
    }

    /**
     * Empties the text for the panel and then runs updateText.
     */
    @Override
    public void reset() {
        myScore = 0;
        myLinesCleared = 0;
        myLevel = 1;
        updateText();
    }

    /**
     * Increases the displayed score based on int parameter and then runs updateText.
     */
    @Override
    public void addScore(final int theIncrease) {
        myScore += theIncrease;
        updateText();
    }

    /**
     * Sets the displayed score based on int parameter and then runs updateText.
     */
    @Override
    public void setScore(final int theScore) {
        myScore = theScore;
        updateText();
    }

    /**
     * Sets the number of lines cleared based on int parameter and then runs updateText.
     */
    @Override
    public void setLinesCleared(final int theLinesCleared) {
        myLinesCleared = theLinesCleared;
        updateText();
    }

    /**
     * Sets the level based on int parameter and then runs updateText.
     */
    @Override
    public void setLevel(final int theLevel) {
        myLevel = theLevel;
        updateText();
    }

    /**
     * Gets the displayed score.
     * @return returns the score
     */
    @Override
    public int getMyScore() {
        return myScore;
    }
}
