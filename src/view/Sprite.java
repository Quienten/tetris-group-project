package view;

import java.awt.Image;

/**
 * Represents a sprite sheet.
 * @author Quienten Miller
 * @version 1.0
 */
public interface Sprite {

    /**
     * Get a sprite from the top left origin of the paremeters.
     * @param theX the top left x-coordinate.
     * @param theY the top left y-coordinate.
     * @return Image of the sprite within the spritesheet.
     */
    Image getSprite(int theX, int theY);

    /**
     * Get a sprite with exact coordinates.
     * @param theSpriteCoordianteData 0 - the top left x-coordinate.
     *                                1 - the top left y-coordinate.
     *                                2 - the width of the sprite.
     *                                3 - the height of the sprite.
     *                                4 - the output width of the sprite.
     *                                5 - the output height of the sprite.
     * @return Image of the sprite within the spritesheet.
     */
    Image getSpriteByCoordinate(int[] theSpriteCoordianteData);
}
