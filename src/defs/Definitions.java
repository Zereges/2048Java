package defs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Definitions class to store constants and simple constants manipulating functions. 
 */
public abstract class Definitions
{
    /** Directory, where to store player profiles */
    public static final String SAVES_DIRECTORY = "Saves";
    
    /** File extension, used to distinguish player profiles */ 
    public static final String SAVES_EXTENSION = ".sav";
    
    /** X dimension of the board */
    public static final int BLOCK_COUNT_X = 4;
    
    /** Y dimension of the board */
    public static final int BLOCK_COUNT_Y = BLOCK_COUNT_X;
    
    /** Minimal size of single block */
    public static final int MIN_BLOCK_SIZE = 100;
    
    /** Minimal size between two blocks */
    public static final int MIN_BLOCK_SPACE = 15;
    
    /** Chance of spawning {@link Blocks#BLOCK_4} instead of {@link Blocks#BLOCK_2}. */
    public static final int BLOCK_4_SPAWN_CHANCE = 15;
    
    /** Default font used for showing numbers in {@link NumberedRect}. */
    public static final Font DEFAULT_BLOCK_FONT = new Font("Courier New", 0, MIN_BLOCK_SIZE / 3);
    
    /** Default font color used for showing numbers in {@link NumberedRect} */
    public static final Color DEFAULT_BLOCK_FONT_COLOR = Color.WHITE;
    
    /** Desired frames per second during an animation. */
    public static final int FRAMES_PER_SECOND = 60;

    /** Default speed of movement during an animation in pixels per frame. */
    public static final int DEFAULT_MOVE_SPEED = 30;

    /** Default speed of {@link NumberedRect} spawn during an animation in pixels per frame. */
    public static final int DEFAULT_SPAWN_SPEED = 10;

    /** Background color of the game field. */
    public static final Color BACKGROUND_COLOR = new Color(30, 30, 30);

    /** Array of colors for {@link NumberedRect} on the game field. */
    public static final Color[] BLOCK_COLORS = new Color[]{
        new Color( 85,  85,  85), // default empty
        new Color(153, 171, 174), // BLOCK_2
        new Color( 47, 119,  80), // BLOCK_4
        new Color( 58, 133,  33), // BLOCK_8
        new Color(130, 146,  20), // BLOCK_16
        new Color(156, 105,  10), // BLOCK_32
        new Color(166,  41,   0), // BLOCK_64
        new Color(255, 172,  89), // BLOCK_128
        new Color( 91, 202, 255), // BLOCK_256
        new Color(  0, 145, 215), // BLOCK_512
        new Color(  0, 128, 192), // BLOCK_1024
        new Color(  0,  95, 140), // BLOCK_2048

        // others are same last one.
    };

    /** Count of blocks on game start. */
    public static final int DEFAULT_START_BLOCKS = 2;

    /** Window title */
    public static final String WINDOW_TITLE = "2048 Game";

    /** Required value of {@link NumberedRect} in order to win the game. */
    public static final int GAME_WIN_NUMBER = 2048;

    /**
     * Gets block color for given block.
     * @param block ID of the {@link Blocks block} for which the color is requested.
     * @return Color of the {@code block}.
     * @see #getBlockColor(Blocks)
     */
    public static Color getBlockColor(int block)
    {
        return block < BLOCK_COLORS.length ? BLOCK_COLORS[block] : BLOCK_COLORS[BLOCK_COLORS.length - 1]; 
    }
    
    /**
     * Gets block color for given block.
     * @param block {@link Blocks Block} for which the color is requested. 
     * @return Color of the {@code block}.
     * @see #getBlockColor(int)
     */
    public static Color getBlockColor(Blocks block)
    {
        return getBlockColor(block.getId()); 
    }
    
    /**
     * Calculates minimal width of the game board.
     * @return Minimal width of the game board.
     */
    public static final int getMinBoardWidth()
    {
        return BLOCK_COUNT_X * MIN_BLOCK_SIZE + (BLOCK_COUNT_X + 1) * MIN_BLOCK_SPACE;
    }

    /**
     * Calculates minimal height of the game board.
     * @return Minimal height of the game board.
     */
    public static final int getMinBoardHeight()
    { 
        return BLOCK_COUNT_Y * MIN_BLOCK_SIZE + (BLOCK_COUNT_Y + 1) * MIN_BLOCK_SPACE;
    }
    
    /**
     * Gets minimal {@link Dimension} for the game board. 
     * @return Minimal Dimension for the game board.
     */
    public static Dimension getMinDimension()
    {
        return new Dimension(getMinBoardWidth(), getMinBoardHeight());
    }

    /**
     * Gets proportional size of the font to fit in {@link NumberedRect}.
     * @param text to fit in.
     * @return Font size so that {@code text} fits in.
     */
    public static int getDefaultBlockFontSize(String text)
    {
        double RATIO = 0.1;
        return (int) ((1-RATIO + RATIO * text.length()) * (Definitions.MIN_BLOCK_SIZE) / text.length());
    }
}
