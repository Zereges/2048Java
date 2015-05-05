package defs;

import java.awt.Dimension;

/**
 * @brief Definitions class to store constants and simple constants manipulating functions. 
 *
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
    public static final int MIN_BLOCK_SIZE = 50;
    
    /** Minimal size between two blocks */
    public static final int MIN_BLOCK_SPACE = 15;
    
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
    public static final Dimension getMinDimension()
    {
        return new Dimension(getMinBoardWidth(), getMinBoardHeight());
    }
}
