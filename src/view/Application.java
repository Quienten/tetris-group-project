package view;

import static model.PropertyChangeEnabledBoard.PROPERTY_CURRENT_PIECE;
import static model.PropertyChangeEnabledBoard.PROPERTY_FROZEN_BLOCKS;
import static model.PropertyChangeEnabledBoard.PROPERTY_GAME_OVER;
import static model.PropertyChangeEnabledBoard.PROPERTY_GHOST_PIECE;
import static model.PropertyChangeEnabledBoard.PROPERTY_LINES_CLEARED;
import static model.PropertyChangeEnabledBoard.PROPERTY_PIECE_LOCK;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import model.AudioPlayer;
import model.PropertyChangeEnabledBoard;
import model.TetrisBoard;
import model.TetrisPiece;
import view.components.BoardPanel;
import view.components.ControlPanel;
import view.components.LeaderboardPanel;
import view.components.NextPiecePanel;
import view.components.ScorePanel;
import view.theme.TetrisThemeController;
import view.theme.Theme;
import view.theme.Themes;

/**
 * The main panel. Also creates the JFrame.
 *
 * @author Quienten Miller, Mel Harvey, Kaely Willhite, Bhavneet Bhargarva, Arthur Fornia
 * @version 1.0
 */
// This class probably does contain too many fields and too many methods.
// We were unable to solve these issues within the deadline due to the
// The technical debt we incured by ignoring in at the start.
@SuppressWarnings({"ClassWithTooManyFields", "ClassWithTooManyMethods"})
public final class Application extends JPanel implements PropertyChangeListener {

    /**
     * The default width of the panel.
     */
    public static final int DEFAULT_WIDTH = 500;

    /**
     * The height of the panel.
     */
    public static final int DEFAULT_HEIGHT = 600;

    /** The number of lines cleared needed for level up */
    private static final int LEVEL_UP = 5;

    /** The score a player gets for locking a piece into place */
    private static final int PIECE_LOCK_BONUS = 4;

    /**
     * The score bonuses for the amount of lines cleared by a player.
     * index 0 is 1 line (single), index 1 is 2 lines (double),
     * index 2 is 3 lines (triple), index 4 is 4 lines (tetris).
     */
    private static final int[] LINE_CLEAR_BONUS_FIELD = new int[] {40, 100, 300, 1200};

    /** The audio player **/
    private static final AudioPlayer AUDIO_PLAYER = new AudioPlayer();

    /** The name of the leaderboard window */
    private static final String LEADERBOARD_STRING = "Leaderboard";
    /** The width of the leaderboard frame */
    private static final int LEADERBOARD_FRAME_WIDTH = 500;
    /** The height of the leaderboard frame */
    private static final int LEADERBOARD_FRAME_HEIGHT = 300;

    /** Description for scoring system */
    private static final String SCORING_DESCRIPTION = """
        Scoring is implemented as follows: \n
        4 Points are earned for each piece added to the frozen block.  \n
        For one line cleared, + 40 * (level) points. \n
        For two lines cleared, + 100 * (level) points. \n
        For three lines cleared, + 300 * (level) points. \n
        For four lines cleared, + 1200 * (level) points. \n
        """;

    /** The board model */
    private PropertyChangeEnabledBoard myBoard;

    /** The timer used to tick the board drop */
    private Timer myTimer;

    /** The theme controller */
    private final TetrisThemeController myThemeController;

    /** The currently selected theme */
    private Theme<?> myCurrentlySelectedTheme;

    /** True if a game is currently in progress, false otherwise */
    private boolean myGameInProgress;

    /** The number of lines cleared */
    private int myLinesCleared;

    /** The number of lines cleared */
    private int myLinesClearedInLevel;

    /** The current level of the board */
    private int myCurrentLevel = 1;

    /** The score panel used to display score, lines, and level **/
    private ScorePanel myScorePanel;

    /** The board panel used to draw the game **/
    private BoardPanel myBoardPanel;

    /** The next piece panel used to draw the next piece **/
    private NextPiecePanel myNextPiecePanel;

    /** The leaderboard panel used to display leaderboard **/
    private final JFrame myLeaderBoard;

    /** The leaderboard panel */
    private LeaderboardPanel myLeaderboardPanel;

    /** New game button */
    private JMenuItem myNewGameButton;

    /** Background image */
    private BufferedImage myBackground;


    /**
     * Create the Tetris panel.
     */
    public Application() {
        super();
        myThemeController = TetrisThemeController.getInstance();
        myCurrentlySelectedTheme = myThemeController.getTheme();

        setupWindow();

        setupTimer();

        setUpPropertyChangeListeners();
        askForFocus();
        setUpKeyListener();

        // The leaderboard table used to display leaderboard
        myLeaderBoard = buildLeaderboard(myLeaderboardPanel.getMyTable());

        try {
            loadResources();
        } catch (final IOException ignored) {

        }
    }

    private void setupTimer() {
        myBoard = TetrisBoard.getInstance();
        myTimer = new Timer(getDelayForLevel(1), e -> myBoard.down());
    }

    private void loadResources() throws IOException {
        final String imagesFolder = "images" + File.separator;
        myBackground = ImageIO.read(
            Objects.requireNonNull(getClass().getResource("/ApplicationBackground.png"))
        );
    }

    private void setUpPropertyChangeListeners() {
        myBoard.addPropertyChangeListener(PROPERTY_GAME_OVER, this);
        myBoard.addPropertyChangeListener(PROPERTY_LINES_CLEARED, this);
        myBoard.addPropertyChangeListener(PROPERTY_PIECE_LOCK, this);
    }

    private void askForFocus() {
        setFocusable(true);
        requestFocus();
        requestFocusInWindow();
    }

    //We believe this method does similar things (creating children)
    // and does not need to be broken up.
    @SuppressWarnings("OverlyLongMethod")
    private void setupWindow() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        setLayout(new BorderLayout());
        //setLayout(new GridBagLayout());
        //final GridBagConstraints c = new GridBagConstraints();

        //Board panel that displays game board.
        myBoardPanel = new BoardPanel();
        add(myBoardPanel, BorderLayout.CENTER);


        TetrisBoard.getInstance().
            addPropertyChangeListener(PROPERTY_FROZEN_BLOCKS, myBoardPanel);
        TetrisBoard.getInstance().
            addPropertyChangeListener(PROPERTY_CURRENT_PIECE, myBoardPanel);
        TetrisBoard.getInstance().
            addPropertyChangeListener(PROPERTY_GHOST_PIECE, myBoardPanel);
        myBoardPanel.setDisplaySplash(true);

        //Info panel that holds next piece and score panels.
        final JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.decode("#151037"));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        add(infoPanel, BorderLayout.LINE_END);



        //Next piece display
        myNextPiecePanel = new NextPiecePanel();
        infoPanel.add(myNextPiecePanel);
        TetrisBoard.getInstance().addPropertyChangeListener(myNextPiecePanel);



        //Score display
        myScorePanel = new ScorePanel();
        infoPanel.add(myScorePanel);


        //Controls display
        final ControlPanel cPanel = new ControlPanel();
        add(cPanel, BorderLayout.PAGE_END);

        addComponentListener(new ResizeWindowAdapter(myBoardPanel, cPanel,
            myNextPiecePanel, myScorePanel));

        //Leaderboard display
        myLeaderboardPanel = new LeaderboardPanel();

    }


    /**
     * This sets up the JFrame.
     */
    public static void createAndShowGUI() {
        final Application mainPanel = new Application();
        mainPanel.setBackground(Color.GRAY);

        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        final JFrame window = new JFrame("Tetris");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setContentPane(mainPanel);
        window.setJMenuBar(mainPanel.createMenu(window)); //creates Menu Bar

        window.pack();

        window.setVisible(true);
    }

    private JMenuBar createMenu(final JFrame theFrame) {
        final JMenuBar menuBar = new JMenuBar();

        menuBar.add(buildGameMenu(theFrame));
        menuBar.add(buildFeauturesMenu(theFrame));

        return menuBar;
    }

    /**
     * Builds Game Menu including New Game, About, and Exit Game.
     * @param theFrame the containing JFrame of this menu bar.
     * @return a "file" menu with some menu items.
     */
    // We believe this method does similar things (makes the JMenu) and does not need
    // to be broken apart.
    @SuppressWarnings("OverlyLongMethod")
    private JMenu buildGameMenu(final JFrame theFrame) {
        final JMenu menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_G);

        myNewGameButton = new JMenuItem("New Game");
        myNewGameButton.setMnemonic(KeyEvent.VK_N);
        myNewGameButton.addActionListener(theEvent -> onNewGame());
        menu.add(myNewGameButton);
        menu.addSeparator();

        menu.add(buildLeaderboardMenuItem(theFrame));
        menu.addSeparator();


        final JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.setMnemonic(KeyEvent.VK_A);
        aboutItem.addActionListener(theEvent ->
                JOptionPane.showMessageDialog(Application.this,
                        "This Tetris clone game was created in Autumn "
                        + "2023 by Quienten Miller, Kaely Willhite, Bhavneet Bhargava, "
                        + "Arthur Fornia, and Mel Harvey "));
        menu.add(aboutItem);
        menu.addSeparator();

        final JMenuItem scoringItem = new JMenuItem("Scoring");
        scoringItem.setMnemonic(KeyEvent.VK_S);
        scoringItem.addActionListener(theEvent ->
                JOptionPane.showMessageDialog(Application.this,
                    SCORING_DESCRIPTION));
        menu.add(scoringItem);
        menu.addSeparator();

        final JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic(KeyEvent.VK_E);
        exitItem.addActionListener(theEvent ->
                theFrame.dispatchEvent(new WindowEvent(theFrame, WindowEvent.WINDOW_CLOSING)));

        menu.add(exitItem);

        return menu;
    }

    private void onNewGame() {
        if (!myGameInProgress) {
            if (myThemeController.getTheme().equals(Themes.GAME_OVER)) {
                myThemeController.setTheme(myCurrentlySelectedTheme);
            }
            myBoard.newGame();
            myTimer.start();
            myScorePanel.reset();
            myGameInProgress = true;
            myNewGameButton.setEnabled(false);

            myBoardPanel.setDisplaySplash(false);
            AUDIO_PLAYER.playBackgroundMusic();
        }
    }

    private JMenuItem buildLeaderboardMenuItem(final JFrame theFrame) {
        final JMenuItem menuItem = new JMenuItem(LEADERBOARD_STRING);
        menuItem.setMnemonic(KeyEvent.VK_L);

        menuItem.addActionListener(theEvent -> myLeaderBoard.setVisible(true));
        //add action listener to take user to leaderboard

        return menuItem;
    }

    private JFrame buildLeaderboard(final JTable theLP) {
        final JScrollPane scrollPane = new JScrollPane(theLP);

        final JFrame window = new JFrame(LEADERBOARD_STRING);
        window.add(scrollPane);
        window.setSize(LEADERBOARD_FRAME_WIDTH, LEADERBOARD_FRAME_HEIGHT);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.pack();

        return window;
    }

    private JMenu buildFeauturesMenu(final JFrame theFrame) {
        final JMenu menu = new JMenu("Features");
        menu.setMnemonic(KeyEvent.VK_F);

        menu.add(buildSubThemeMenu());
        return menu;
    }

    private JMenu buildSubThemeMenu() {
        final JMenu themeMenu = new JMenu("Themes");
        themeMenu.setMnemonic(KeyEvent.VK_T);

        final ButtonGroup group = new ButtonGroup();



        final JRadioButtonMenuItem defaultTheme = createThemeItems("Blox", group, Themes.BLOX);
        themeMenu.add(defaultTheme);
        defaultTheme.setSelected(true);

        themeMenu.add(createThemeItems("Classic", group, Themes.CLASSIC));
        themeMenu.add(createThemeItems("UW Husky", group, Themes.HUSKY));
        themeMenu.add(createThemeItems("Christmas", group, Themes.CHRISTMAS));

        return themeMenu;
    }

    private JRadioButtonMenuItem createThemeItems(final String theName,
                                                  final ButtonGroup theGroup,
                                                  final Theme<?> theTheme) {
        final JRadioButtonMenuItem themeItem = new JRadioButtonMenuItem(theName);
        theGroup.add(themeItem);
        themeItem.addActionListener(e -> onChangeTheme(theTheme));
        return themeItem;
    }

    private void onChangeTheme(final Theme<?> theTheme) {
        myThemeController.setTheme(theTheme);
        myBoardPanel.repaint();
        myNextPiecePanel.repaint();
    }


    private void setUpKeyListener() {
        addKeyListener(new MyKeyAdapter());
    }

    /**
     * Main method to start the project.
     * @param theArgs the arguments.
     */
    public static void main(final String... theArgs) {
        javax.swing.SwingUtilities.invokeLater(Application::createAndShowGUI);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals(PROPERTY_GAME_OVER)) {
            final boolean gameOver = (boolean) theEvent.getNewValue();
            if (gameOver) {
                onGameOver();
            }
        }
        if (theEvent.getPropertyName().equals(PROPERTY_LINES_CLEARED)) {
            final int linesJustCleared = (int) theEvent.getNewValue();
            onLineCleared(linesJustCleared);
        }
        if (theEvent.getPropertyName().equals(PROPERTY_PIECE_LOCK)) {
            onPieceLock();
        }
    }

    private void onGameOver() {
        myTimer.stop();
        final Map<TetrisPiece, Color> newTheme = new HashMap<>();
        myCurrentlySelectedTheme = myThemeController.getTheme();
        myThemeController.setTheme(Themes.GAME_OVER);
        myGameInProgress = false;
        myNewGameButton.setEnabled(true);

        AUDIO_PLAYER.stopBackgroundMusic(); //stops music
        AUDIO_PLAYER.playGameOver(); //play audio (plays when new game starts)

        //popup for user to put in name for leaderboard
        final String playerName = JOptionPane.showInputDialog(
            "Game Over!\nPlease enter your name."
        );
        myLeaderboardPanel.addData(playerName, myScorePanel.getMyScore(),
            myLeaderboardPanel.getMyTableModel());
        myLeaderBoard.setVisible(true);

    }

    private void onLineCleared(final int theAmountOfLinesCleared) {
        //This edits the total lines cleared.
        myLinesCleared += theAmountOfLinesCleared;
        myScorePanel.setLinesCleared(myLinesCleared);

        //This edits the counter for leveling up.
        myLinesClearedInLevel += theAmountOfLinesCleared;
        myScorePanel.addScore(getScoreLinesCleared(theAmountOfLinesCleared));

        if (myLinesClearedInLevel < LEVEL_UP) {
            AUDIO_PLAYER.playClearLine(); //play audio
        }

        //If the counter is above our threshold, level up.
        if (myLinesClearedInLevel >= LEVEL_UP) {
            onLevelUp();
        }
    }

    private void onLevelUp() {
        myLinesClearedInLevel -= LEVEL_UP;
        myCurrentLevel++;
        myScorePanel.setLevel(myCurrentLevel);

        myTimer.setDelay(getDelayForLevel(myCurrentLevel));

        AUDIO_PLAYER.playLevelUp(); //play audio
    }

    /**
     * Calculate gravity based on the Tetris Worlds formula.
     * (0.8-((Level-1)*0.007))^(Level-1)
     * <a href="https://tetris.fandom.com/wiki/Tetris_Worlds">Tetris Worlds formula</a>
     * @param theLevel the level to calculate timer delay for
     * @return the timer delay for this level
     */
    private int getDelayForLevel(final int theLevel) {
        final BigDecimal a = new BigDecimal("0.8");

        final BigDecimal b = new BigDecimal("0.007");
        BigDecimal result = b.multiply(new BigDecimal(theLevel - 1));
        result = a.subtract(result);
        result = result.pow(theLevel - 1);
        result = result.multiply(new BigDecimal("1000"));
        return result.intValue();
    }

    private void onPieceLock() {
        myScorePanel.addScore(PIECE_LOCK_BONUS);
    }

    private int getScoreLinesCleared(final int theLinesCleared) {
        return LINE_CLEAR_BONUS_FIELD[theLinesCleared - 1] * myCurrentLevel;
    }

    private void quitGame() {
        myBoardPanel.setDisplaySplash(true);
        myBoard.newGame();
        onGameOver();
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
        g2d.drawImage(myBackground, 0, 0, getWidth(), getHeight(), null);
    }

    private final class MyKeyAdapter extends KeyAdapter {

        /**
         * A map from a key mapping to a runnable action.
         */
        private final Map<Integer, Runnable> myKeyMappings;

        MyKeyAdapter() {
            super();
            myKeyMappings = new HashMap<>();
            mapKeys();
        }

        private void mapKeys() {
            myKeyMappings.put(KeyEvent.VK_LEFT, myBoard::left);
            myKeyMappings.put(KeyEvent.VK_A, myBoard::left);
            myKeyMappings.put(KeyEvent.VK_RIGHT, myBoard::right);
            myKeyMappings.put(KeyEvent.VK_D, myBoard::right);
            myKeyMappings.put(KeyEvent.VK_DOWN, myBoard::down);
            myKeyMappings.put(KeyEvent.VK_S, myBoard::down);
            myKeyMappings.put(KeyEvent.VK_W, myBoard::rotateCW);
            myKeyMappings.put(KeyEvent.VK_UP, myBoard::rotateCW);
            myKeyMappings.put(KeyEvent.VK_Q, myBoard::rotateCCW);
            myKeyMappings.put(KeyEvent.VK_SPACE, myBoard::drop);
            myKeyMappings.put(KeyEvent.VK_ESCAPE, Application.this::quitGame);
        }

        @Override
        public void keyTyped(final KeyEvent theEvent) {
            super.keyTyped(theEvent);
        }

        @Override
        public void keyPressed(final KeyEvent theEvent) {
            super.keyPressed(theEvent);
            //Handle pause
            if (theEvent.getKeyCode() == KeyEvent.VK_P) {
                if (myTimer.isRunning()) {
                    myTimer.stop();
                    AUDIO_PLAYER.stopBackgroundMusic();
                } else {
                    myTimer.start();
                    AUDIO_PLAYER.playBackgroundMusic();
                }
            }
            //Handles the rest of the game controls.
            if (myTimer.isRunning()) {
                if (myKeyMappings.containsKey(theEvent.getKeyCode())) {
                    myKeyMappings.get(theEvent.getKeyCode()).run();
                }
            }
        }

        @Override
        public void keyReleased(final KeyEvent theEvent) {
            super.keyReleased(theEvent);
        }
    }
}
