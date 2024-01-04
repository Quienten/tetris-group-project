package view.components;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import view.SpriteSheet;

/**
 * The ControlPanel shows the controls of the game.
 * @author Arthur Fornia
 * @version 1.0
 */
public final class ControlPanel extends JPanel {

    /** The width (in pixels) of the panel */
    private static final int WIDTH = 240;
    /** The width (in pixels) of the panel */
    private static final int HEIGHT = 200;

    /** The amount of rows to display controls */
    private static final int CONTROL_LIST_ROWS = 3;

    /** The default size to draw the key immages */
    private static final int KEY_SIZE_DEFAULT = 32;

    /** We only want to scale keys in multiples of 16 */
    private static final int RESIZE_KEY_FLOOR = 16;

    /** The data of the space key on the sprite sheet */
    private static final int[] SPACE_KEY_DATA = {63, 32, 34, 16, 63, 32};

    /** The data of the escape key on the sprite sheet */
    private static final int[] ESCAPE_KEY_DATA = {37, 0, 22, 16, 44, 32};

    /** Left key data */
    private static final int[][] LEFT_KEY_DATA = {{2, 0}, {0, 2}};
    /** Right key data */
    private static final int[][] RIHGT_KEY_DATA = {{3, 0}, {3, 2}};
    /** Rotate right key data */
    private static final int[][] ROTATE_RIGHT_KEY_DATA = {{0, 0}, {6, 4}};
    /** Rotate left key data */
    private static final int[][] ROTATE_LEFT_DATA = {{0, 4}};
    /** Down key data */
    private static final int[][] DOWN_KEY_DATA = {{1, 0}, {2, 4}};
    /** Pause key data */
    private static final int[][] PAUSE_KEY_DATA = {{7, 3}};

    /** Font size */
    private static final int FONT_SIZE = 14;
    /** Big font size. */
    private static final int BIG_FONT_SIZE = 16;

    /** Default width */
    private static final int DEFAULT_WIDTH = 485;
    /** Default width */
    private static final int DEFAULT_HEIGHT = 115;

    /** Rows */
    private static final int ROWS = 3;
    /** Columns */
    private static final int COLS = 3;

    /** Represents the size to draw the key images. */
    private int myKeySize = KEY_SIZE_DEFAULT;

    /** List of control images to resize later. */
    private final java.util.List<JLabel> myControls;



    /**
     * Creates the ControlsPanel.
     */
    public ControlPanel() {
        super();

        myControls = new ArrayList<>();

        setupPanel();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent theEvent) {
                super.componentResized(theEvent);
                myKeySize = getHeight() / CONTROL_LIST_ROWS; //(((getHeight() / 10) * 3) + 2);
                myKeySize -= myKeySize % RESIZE_KEY_FLOOR;
                updateControlIcons();
            }
        });
    }

    private void setupPanel() {
        setBackground(Color.decode("#151037"));
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        final SpriteSheet keysSheet = new SpriteSheet("KeyboardKeysSpriteSheet",
            16, 16, 32, 32);

        setLayout(new GridLayout(ROWS, COLS));

        makeControl("Move Left", LEFT_KEY_DATA, keysSheet);
        makeControl("Move Right", RIHGT_KEY_DATA, keysSheet);
        makeControl("Rotate Right", ROTATE_RIGHT_KEY_DATA, keysSheet);
        makeControl("Rotate Left", ROTATE_LEFT_DATA, keysSheet);
        makeControl("Move Down", DOWN_KEY_DATA, keysSheet);
        makeControl("Pause", PAUSE_KEY_DATA, keysSheet);


        setUpBigKeys();
    }

    private void makeControl(final String theText, final int[][] theKeySprites,
                             final SpriteSheet theSpriteSheet) {
        final JPanel panel = new JPanel();

        panel.setOpaque(false);

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        //panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(panel);

        for (final int[] key : theKeySprites) {
            final JLabel keyIcon = new JLabel(new ImageIcon(
                theSpriteSheet.getSprite(key[0], key[1]))
            );
            panel.add(keyIcon);
            myControls.add(keyIcon);
        }

        final JLabel text = new JLabel(theText);
        text.setFont(new Font(text.getFont().getFontName(), Font.BOLD, BIG_FONT_SIZE));
        text.setForeground(Color.WHITE);
        panel.add(text);
    }

    private void updateControlIcons() {
        for (final JLabel label : myControls) {
            if (label.getIcon() instanceof ImageIcon) {
                Image old = ((ImageIcon) label.getIcon()).getImage();
                final int keySize = Math.max(16, myKeySize);
                old = old.getScaledInstance(keySize, keySize, BufferedImage.SCALE_DEFAULT);
                label.setIcon(new ImageIcon(old));
            }

        }
    }

    private void setUpBigKeys() {
        final SpriteSheet bigKeysSheet = new SpriteSheet("BigKeyboardKeysSpriteSheet");

        //Space key
        final JPanel dropPanel = new JPanel();
        dropPanel.setOpaque(false);
        dropPanel.setLayout(new BoxLayout(dropPanel, BoxLayout.X_AXIS));
        add(dropPanel);

        dropPanel.add(new JLabel(new ImageIcon(bigKeysSheet.
            getSpriteByCoordinate(SPACE_KEY_DATA))));

        final JLabel dropText = new JLabel("Drop the piece");
        dropText.setFont(new Font(dropText.getFont().getFontName(), Font.BOLD, FONT_SIZE));
        dropText.setForeground(Color.WHITE);
        dropPanel.add(dropText);


        //Escape key
        final JPanel escPanel = new JPanel();
        escPanel.setOpaque(false);
        escPanel.setLayout(new BoxLayout(escPanel, BoxLayout.X_AXIS));
        add(escPanel);

        escPanel.add(new JLabel(new ImageIcon(bigKeysSheet.
            getSpriteByCoordinate(ESCAPE_KEY_DATA))));

        final JLabel escText = new JLabel("Quit game");
        escText.setFont(new Font(escText.getFont().getFontName(), Font.BOLD, FONT_SIZE));
        escText.setForeground(Color.WHITE);
        escPanel.add(escText);
    }
}
