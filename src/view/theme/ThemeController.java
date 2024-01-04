package view.theme;

/**
 * Theme used for coloring blocks on BoardPanel.
 *
 * @author Quienten Miller
 * @version 1.0
 */
public interface ThemeController {

    /**
     * Gets the current theme.
     * @return the current theme.
     */
    Theme<?> getTheme();

    /**
     * Override a new theme.
     * @param theTheme the new theme
     */
    void setTheme(Theme<?> theTheme);

}
