package main;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import defs.AchievementTypes;
import defs.Blocks;

/**
 * Represents saved Achievements for current player.
 * @see defs.AchievementTypes
 * @see Player
 */
public class Achievements implements Serializable
{
    /** Auto-generated for {@code Serializable} interface. */
    private static final long serialVersionUID = -5874188051861561906L;
    
    /** Already completed achievements. */
    private SortedSet<AchievementTypes> mCompleted = new TreeSet<>();
    
    /**
     * Sets given achievement as completed.
     * @param achievement Achievement to set as completed.
     * @see #setCompleted(AchievementTypes, boolean)
     */
    public void setCompleted(AchievementTypes achievement) { setCompleted(achievement, true); }
    
    /**
     * Sets completation of given achievement based on the argument of the method.
     * @param achievement Achievement to change completion of.
     * @param completed True if achievement is set as completed, false if achievement is set as incomplete.
     */
    public void setCompleted(AchievementTypes achievement, boolean completed)
    {
        if (completed)
            mCompleted.add(achievement);
        else
            mCompleted.remove(achievement);
    }
    
    /**
     * Checks whether given achievement is already completed or not
     * @param achievement Achievement to check for.
     * @return True if achievement has been already completed, false otherwise.
     */
    public boolean isCompleted(AchievementTypes achievement) { return mCompleted.contains(achievement); }

    /**
     * Is fired when two {@link defs.NumberedRect} are merged into one.
     * @param number Real shown number of new block being merged.
     */
    public void merge(int number)
    {
        if (number == Blocks.BLOCK_512.getValue())
            setCompleted(AchievementTypes.MAKE_BLOCK_512);
        if (number == Blocks.BLOCK_1024.getValue())
            setCompleted(AchievementTypes.MAKE_BLOCK_1024);
        if (number == Blocks.BLOCK_2048.getValue())
            setCompleted(AchievementTypes.MAKE_BLOCK_2048);
        if (number == Blocks.BLOCK_4096.getValue())
            setCompleted(AchievementTypes.MAKE_BLOCK_4096);
    }
    
    /**
     * Is fired when {@link main.Player} wins the game.
     * @param startTime Time in milliseconds when the game was started.
     * @param game Reference to {@link main.Game}.
     */
    public void won(long startTime, Game game)
    {
        long now = System.currentTimeMillis();
        if (now - startTime < TimeUnit.MINUTES.toMillis(15))
            setCompleted(AchievementTypes.WIN_IN_15MIN);
        if (now - startTime < TimeUnit.MINUTES.toMillis(10))
            setCompleted(AchievementTypes.WIN_IN_10MIN);
    }

    /**
     * Is fired when {@link main.Player} loses the game.
     * @param startTime Time in milliseconds when the game was started.
     * @param game Reference to {@link main.Game}.
     */
    public void lose(long startTime, Game game)
    {
        long now = System.currentTimeMillis();
        if (now - startTime > TimeUnit.MINUTES.toMillis(30))
            setCompleted(AchievementTypes.LOSE_AFTER_30MIN);
        if (now - startTime < TimeUnit.MINUTES.toMillis(1))
            setCompleted(AchievementTypes.LOSE_IN_1MIN);
        if (game.hasBlock(Blocks.BLOCK_1024))
            setCompleted(AchievementTypes.LOSE_WITH_1028);
    }
}
