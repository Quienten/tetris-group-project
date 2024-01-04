package view.theme;

/**
 * Class used to control what piece is what color.
 * @author Quienten Miller
 * @version 1
 */
public final class TetrisThemeController implements ThemeController {

    /** Tetris theme controller */
    private static final TetrisThemeController INSTANCE = new TetrisThemeController();

    /** Map of the pieces to their colors */
    private Theme<?> myTheme;


    private TetrisThemeController() {
        super();
        myTheme = Themes.BLOX;
    }

    /**
     * Get instance of this singleton.
     * @return instance of class.
     */
    public static TetrisThemeController getInstance() {
        return INSTANCE;
    }


    @Override
    public Theme<?> getTheme() {
        return myTheme;
    }

    @Override
    public void setTheme(final Theme<?> theTheme) {
        myTheme = theTheme;
    }


}
