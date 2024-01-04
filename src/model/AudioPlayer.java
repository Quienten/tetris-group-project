package model;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * AudioPlayer plays audio for Tetris game.
 *
 * @author Mel Harvey
 * @version Autumn 2023
 */
public class AudioPlayer implements Audio {

    /**
     * Line cleared clip.
     */
    private Clip myClearLineSound;

    /**
     * Bacground clip.
     */
    private Clip myBackgroundMusic;

    /**
     * Level up clip.
     */
    private Clip myLevelUpSound;

    /**
     * Game over clip.
     */
    private Clip myGameOverSound;

    /**
     * Constructor for AudioPlayer.
     */
    public AudioPlayer() {
        super();
        try {

            getClips();

            final String audioFolder = "audio" + File.separator;
            myClearLineSound.open(AudioSystem.getAudioInputStream(new File(audioFolder
                    + "LineCleared.wav").
                    getAbsoluteFile()));

            myBackgroundMusic.open(AudioSystem.getAudioInputStream(new File(audioFolder
                    + "Background.wav").
                    getAbsoluteFile()));

            myLevelUpSound.open(AudioSystem.getAudioInputStream(new File(audioFolder
                    + "LevelUp.Wav").
                    getAbsoluteFile()));


            myGameOverSound.open(AudioSystem.getAudioInputStream(new File(audioFolder
                    + "GameOver.wav").
                    getAbsoluteFile()));
        } catch (final LineUnavailableException
                       | UnsupportedAudioFileException | IOException e) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void getClips() {
        try {

            myClearLineSound = AudioSystem.getClip();
            myBackgroundMusic = AudioSystem.getClip();
            myLevelUpSound = AudioSystem.getClip();
            myGameOverSound = AudioSystem.getClip();

        } catch (final LineUnavailableException e) {

            throw new RuntimeException(e);

        }
    }

    @Override
    public void playClearLine() {
        myClearLineSound.setFramePosition(0);
        myClearLineSound.start();
    }

    @Override
    public void playBackgroundMusic() {
        myBackgroundMusic.setFramePosition(0);
        myBackgroundMusic.start();
        myBackgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void stopBackgroundMusic() {
        myBackgroundMusic.stop();
    }

    @Override
    public void playLevelUp() {
        myLevelUpSound.setFramePosition(0);
        myLevelUpSound.start();
    }

    @Override
    public void playGameOver() {
        myGameOverSound.setFramePosition(0);
        myGameOverSound.start();
    }
}
