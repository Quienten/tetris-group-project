package view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Represents a spritesheet. An image that contains many sprites within it of
 * (hopefully) equal sizes.
 * @author Quienten Miller
 * @version 1.0
 */
public class SpriteSheet implements Sprite {

    /**
     * The image that contains the entire spritesheet.
     */
    private BufferedImage mySpriteSheet;

    /** The tile size width within the image spritesheet. */
    private final int myTileSizeX;

    /** The tile size height within the image spritesheet. */
    private final int myTileSizeY;

    /** The width to scale to on output of the sprite image. */
    private final int myScaleToX;

    /** The height to scale to on output of the sprite image. */
    private final int myScaleToY;

    /**
     * Create a sprite sheet.
     * @param theFileName the filename to look for. must be a png file in the images folder.
     * @param theTileSizeX the width of the tiles.
     * @param theTileSizeY the height of the tiles.
     * @param theScaleToX the width to scale to when outputting sprite.
     * @param theScaleToY the height to scale to when outputting spirte.
     */
    public SpriteSheet(final String theFileName, final int theTileSizeX,
                       final int theTileSizeY, final int theScaleToX, final int theScaleToY) {
        super();

        myTileSizeX = theTileSizeX;
        myTileSizeY = theTileSizeY;

        myScaleToX = theScaleToX;
        myScaleToY = theScaleToY;

        final String imagesFolder = "images" + File.separator;

        try {
            mySpriteSheet = ImageIO.read(new File(imagesFolder + theFileName + ".png"));
        } catch (final IOException ignored) {

        }
    }

    /**
     * Create a sprite sheet.
     * @param theFileName the filename to look for. must be a png file in the images folder.
     * @param theTileSizeX the width of the tiles.
     * @param theTileSizeY the height of the tiles.
     */
    public SpriteSheet(final String theFileName,
                       final int theTileSizeX, final int theTileSizeY) {
        this(theFileName, theTileSizeX, theTileSizeY, theTileSizeX, theTileSizeY);
    }

    /**
     * Create a sprite sheet, used for nonuniform sprite sheets.
     * @param theFileName the filename to look for. must be a png file in the images folder.
     */
    public SpriteSheet(final String theFileName) {
        this(theFileName, 0, 0, 0, 0);
    }

    @Override
    public Image getSprite(final int theX, final int theY) {
        return mySpriteSheet.getSubimage(
            theX * myTileSizeX,
            theY * myTileSizeY,
            myTileSizeX,
            myTileSizeY
        ).getScaledInstance(myScaleToX, myScaleToY, BufferedImage.SCALE_DEFAULT);
    }

    @SuppressWarnings("CheckStyle") //Supress because the agreed upon indexes are magic numbers.
    @Override
    public Image getSpriteByCoordinate(final int[] theSpriteCoordianteData) {
        return mySpriteSheet.getSubimage(
            theSpriteCoordianteData[0],
            theSpriteCoordianteData[1],
            theSpriteCoordianteData[2],
            theSpriteCoordianteData[3]
        ).getScaledInstance(
            theSpriteCoordianteData[4],
            theSpriteCoordianteData[5],
            BufferedImage.SCALE_DEFAULT)
        ;
    }
}
