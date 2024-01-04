package view.components;

/**
 * The Score interface that distacts the ScorePanel.
 * @author Arthur Fornia
 * @version 1.0
 */
public interface Score {
    /**
     * Empties the text for the panel and then runs updateText.
     */
    void reset();

    /**
     * Increases the displayed score based on int parameter and then runs updateText.
     */
    void addScore(int theIncrease);

    /**
     * Sets the displayed score based on int parameter and then runs updateText.
     */
    void setScore(int theScore);

    /**
     * Sets the number of lines cleared based on int parameter and then runs updateText.
     */
    void setLinesCleared(int theLinesCleared);

    /**
     * Sets the level based on int parameter and then runs updateText.
     */
    void setLevel(int theLevel);

    /**
     * Gets the displayed score.
     */
    int getMyScore();
}
