package main;

import java.io.Serializable;

import defs.Direction;
import defs.StatTypes;

public class Stats implements Serializable
{
    private static final long serialVersionUID = 231497236618423832L;
    private long mCurrentStats[] = new long[StatTypes.values().length];
    private long mGlobalStats[] = new long[StatTypes.values().length];
    private long mLastUpdate;
    
    public long[] getCurrentStats() { return mCurrentStats; }
    public long[] getGlobalStats() { return mGlobalStats; }
    
    public void play(Direction direction)
    {
        switch (direction)
        {
            case LEFT:
                ++mCurrentStats[StatTypes.LEFT_MOVES.getIndex()];
                ++mGlobalStats[StatTypes.LEFT_MOVES.getIndex()];
                break;
            case RIGHT:
                ++mCurrentStats[StatTypes.RIGHT_MOVES.getIndex()];
                ++mGlobalStats[StatTypes.RIGHT_MOVES.getIndex()];
                break;
            case UP:
                ++mCurrentStats[StatTypes.UP_MOVES.getIndex()];
                ++mGlobalStats[StatTypes.UP_MOVES.getIndex()];
                break;
            case DOWN:
                ++mCurrentStats[StatTypes.DOWN_MOVES.getIndex()];
                ++mGlobalStats[StatTypes.DOWN_MOVES.getIndex()];
                break;
        }
        ++mCurrentStats[StatTypes.TOTAL_MOVES.getIndex()];
        ++mGlobalStats[StatTypes.TOTAL_MOVES.getIndex()];
    }
    
    public void resetCurrent()
    {
        mCurrentStats = new long[StatTypes.values().length];
    }

    public void move()
    {
        ++mCurrentStats[StatTypes.BLOCKS_MOVED.getIndex()];
        ++mGlobalStats[StatTypes.BLOCKS_MOVED.getIndex()];
    }

    public void merge()
    {
        ++mCurrentStats[StatTypes.BLOCKS_MERGED.getIndex()];
        ++mGlobalStats[StatTypes.BLOCKS_MERGED.getIndex()];
    }

    public void restart()
    {
        ++mCurrentStats[StatTypes.GAME_RESTARTS.getIndex()];
        ++mGlobalStats[StatTypes.GAME_RESTARTS.getIndex()];
    }

    public void win()
    {
        ++mCurrentStats[StatTypes.GAME_WINS.getIndex()];
        ++mGlobalStats[StatTypes.GAME_WINS.getIndex()];
    }

    public void lose()
    {
        ++mCurrentStats[StatTypes.GAME_LOSES.getIndex()];
        ++mGlobalStats[StatTypes.GAME_LOSES.getIndex()];
    }

    public void score(int number)
    {
        mCurrentStats[StatTypes.TOTAL_SCORE.getIndex()] += number;
        mGlobalStats[StatTypes.TOTAL_SCORE.getIndex()] += number;
    }

    public void highestScore(int mScore)
    {
        mCurrentStats[StatTypes.HIGHEST_SCORE.getIndex()] = Math.max(mCurrentStats[StatTypes.HIGHEST_SCORE.getIndex()], mScore);
        mGlobalStats[StatTypes.HIGHEST_SCORE.getIndex()] = Math.max(mGlobalStats[StatTypes.HIGHEST_SCORE.getIndex()], mScore);
    }
    
    public void maximalBlock(int number)
    {
        mCurrentStats[StatTypes.MAXIMAL_BLOCK.getIndex()] = Math.max(mCurrentStats[StatTypes.MAXIMAL_BLOCK.getIndex()], number);
        mGlobalStats[StatTypes.MAXIMAL_BLOCK.getIndex()] = Math.max(mGlobalStats[StatTypes.MAXIMAL_BLOCK.getIndex()], number);
    }
    public void updateTime(long startTime)
    {
        long now = System.currentTimeMillis();
        mCurrentStats[StatTypes.TOTAL_TIME_PLAYED.getIndex()] = (now - startTime) / 1000;
        mGlobalStats[StatTypes.TOTAL_TIME_PLAYED.getIndex()] += (now - mLastUpdate) / 1000;
        setLastUpdate(now);
    }
    public void setLastUpdate(long now) { mLastUpdate = now; }
}
