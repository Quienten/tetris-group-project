package model;

/**
 * Audio Interface.
 *
 * @author Mel Harvey
 * @version 1.0
 */
public interface Audio {

    /**
     * Plays clear line audio.
     */
    void playClearLine();

    /**
     * Play background music.
     */
    void playBackgroundMusic();

    /**
     * Play background music.
     */
    void stopBackgroundMusic();

    /**
     * Play level up audio.
     */
    void playLevelUp();

    /**
     * Plays game over audio.
     */
    void playGameOver();

}
