package main;

import java.io.Serializable;
import defs.Direction;
import defs.StatTypes;

/**
 * Represents saved Stats for current player.
 * @see defs.StatTypes
 * @see Player
 */
public class Stats implements Serializable
{
    /** Auto-generated for {@code Serializable} interface. */
    private static final long serialVersionUID = 231497236618423832L;
    
    /** Array containing values for current session stats. */
    private long mCurrentStats[] = new long[StatTypes.values().length];
    
    /** Array containing values for all {@link Player}'s playtroughs */
    private long mGlobalStats[] = new long[StatTypes.values().length];
    
    /** Time of the last update of timed stats. */
    private long mLastUpdate;
    
    /**
     * Getter for {@code mCurrentStats}
     * @return Array containing values of player current stats.
     */
    public long[] getCurrentStats() { return mCurrentStats; }

    /**
     * Getter for {@code mGlobalStats}
     * @return Array containing values of player global stats.
     */
    public long[] getGlobalStats() { return mGlobalStats; }
    
    /** Resets statistics of this session. */
    public void resetCurrent() { mCurrentStats = new long[StatTypes.values().length]; }
    
    /**
     * Is fired when player performs a turn.
     * @param direction {@link defs.Direction} player played to.
     */
    public void play(Direction direction)
    {
        switch (direction)
        {
            case LEFT:
                ++mCurrentStats[StatTypes.LEFT_MOVES.getId()];
                ++mGlobalStats[StatTypes.LEFT_MOVES.getId()];
                break;
            case RIGHT:
                ++mCurrentStats[StatTypes.RIGHT_MOVES.getId()];
                ++mGlobalStats[StatTypes.RIGHT_MOVES.getId()];
                break;
            case UP:
                ++mCurrentStats[StatTypes.UP_MOVES.getId()];
                ++mGlobalStats[StatTypes.UP_MOVES.getId()];
                break;
            case DOWN:
                ++mCurrentStats[StatTypes.DOWN_MOVES.getId()];
                ++mGlobalStats[StatTypes.DOWN_MOVES.getId()];
                break;
        }
        ++mCurrentStats[StatTypes.TOTAL_MOVES.getId()];
        ++mGlobalStats[StatTypes.TOTAL_MOVES.getId()];
    }
    
    /** Is fired when {@link defs.NumberedRect} is moved. */
    public void move()
    {
        ++mCurrentStats[StatTypes.BLOCKS_MOVED.getId()];
        ++mGlobalStats[StatTypes.BLOCKS_MOVED.getId()];
    }

    /** Is fired when two {@link defs.NumberedRect} are merged */
    public void merge()
    {
        ++mCurrentStats[StatTypes.BLOCKS_MERGED.getId()];
        ++mGlobalStats[StatTypes.BLOCKS_MERGED.getId()];
    }

    /** Is fired when {@link Player} restarts the game. */
    public void restart()
    {
        ++mCurrentStats[StatTypes.GAME_RESTARTS.getId()];
        ++mGlobalStats[StatTypes.GAME_RESTARTS.getId()];
    }

    /** Is fired when {@link Player} wins the game. */
    public void win()
    {
        ++mCurrentStats[StatTypes.GAME_WINS.getId()];
        ++mGlobalStats[StatTypes.GAME_WINS.getId()];
    }

    /** Is fired when {@link Player} loses the game. */
    public void lose()
    {
        ++mCurrentStats[StatTypes.GAME_LOSES.getId()];
        ++mGlobalStats[StatTypes.GAME_LOSES.getId()];
    }

    /**
     * Is fired when {@link Player} recieves score poitns.  
     * @param number Score points player recieved.
     */
    public void score(int number)
    {
        mCurrentStats[StatTypes.TOTAL_SCORE.getId()] += number;
        mGlobalStats[StatTypes.TOTAL_SCORE.getId()] += number;
    }

    /**
     * Updates {@link defs.StatTypes#HIGHEST_SCORE} statistics.
     * @param mScore Current {@link Player}'s score.
     */
    public void highestScore(int mScore)
    {
        mCurrentStats[StatTypes.HIGHEST_SCORE.getId()] = Math.max(mCurrentStats[StatTypes.HIGHEST_SCORE.getId()], mScore);
        mGlobalStats[StatTypes.HIGHEST_SCORE.getId()] = Math.max(mGlobalStats[StatTypes.HIGHEST_SCORE.getId()], mScore);
    }
    
    /**
     * Updates {@link defs.StatTypes#MAXIMAL_BLOCK} statistics.
     * @param number Value representing new merged {@link defs.Blocks block}.
     */
    public void maximalBlock(int number)
    {
        mCurrentStats[StatTypes.MAXIMAL_BLOCK.getId()] = Math.max(mCurrentStats[StatTypes.MAXIMAL_BLOCK.getId()], number);
        mGlobalStats[StatTypes.MAXIMAL_BLOCK.getId()] = Math.max(mGlobalStats[StatTypes.MAXIMAL_BLOCK.getId()], number);
    }
    
    /**
     * Updates time based statistics.
     * @param startTime Time in milliseconds when the game has started.
     */
    public void updateTime(long startTime)
    {
        long now = System.currentTimeMillis();
        mCurrentStats[StatTypes.TOTAL_TIME_PLAYED.getId()] = (now - startTime) / 1000;
        mGlobalStats[StatTypes.TOTAL_TIME_PLAYED.getId()] += (now - mLastUpdate) / 1000;
        setLastUpdate(now);
    }
    
    /**
     * Setter for {@code mLastUpdate}.
     * @param now Epocha time in millis to set to.
     */
    public void setLastUpdate(long now) { mLastUpdate = now; }
}
