package main;

import java.io.Serializable;

import defs.Direction;
import defs.StatTypes;

public class Stats implements Serializable
{
    private static final long serialVersionUID = 231497236618423832L;
    private long mCurrentStats[] = new long[StatTypes.values().length];
    private long mGlobalStats[] = new long[StatTypes.values().length];
    
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
    
    public void resetCurrent() { mCurrentStats = new long[StatTypes.values().length]; }
}
