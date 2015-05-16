package main;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import defs.AchievementTypes;
import defs.Blocks;

public class Achievements implements Serializable
{
    private static final long serialVersionUID = -5874188051861561906L;
    private SortedSet<AchievementTypes> mCompleted = new TreeSet<>();
    
    public void setCompleted(AchievementTypes achievement) { setCompleted(achievement, true); }
    
    public void setCompleted(AchievementTypes achievement, boolean completed)
    {
        if (completed)
            mCompleted.add(achievement);
        else
            mCompleted.remove(achievement);
    }
    
    public boolean isCompleted(AchievementTypes achievement)
    {
        return mCompleted.contains(achievement);
    }

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
    
    public void won(long startTime, Game game)
    {
        long now = System.currentTimeMillis();
        if (now - startTime < TimeUnit.MINUTES.toMillis(15))
            setCompleted(AchievementTypes.WIN_IN_15MIN);
        if (now - startTime < TimeUnit.MINUTES.toMillis(10))
            setCompleted(AchievementTypes.WIN_IN_10MIN);
    }
    
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
