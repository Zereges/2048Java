package defs;

/**
 * Represents achievements database.
 * @see main.Achievements
 * @see windows.AchievementsWindow
 */
public enum AchievementTypes
{
    MAKE_BLOCK_512(0, "Obtain a block with the value of 512."),
    MAKE_BLOCK_1024(1, "Obtain a block with the value of 1024."),
    MAKE_BLOCK_2048(2, "Obtain a block with the value of 2048."),
    MAKE_BLOCK_4096(3, "Obtain a block with the value of 4096."),
    WIN_IN_15MIN(4, "Win the game in 15 minutes."),
    WIN_IN_10MIN(5, "Win the game in 10 minutes."),
    LOSE_AFTER_30MIN(6, "Lose the game after 30 minutes."),
    LOSE_IN_1MIN(7, "Lose the game in 1 minute."),
    LOSE_WITH_1028(8, "Lose the game while having Block with the value of 1024.");
    
    /** ID of the achievement. */
    private final int mId;
    
    /** Description of the achievement. */
    private final String mDesc;
    
    /**
     * Constructor for enum members.
     * @param id ID of the achievement.
     * @param desc Description of the achievement.
     */
    AchievementTypes(int id, String desc) { mId = id; mDesc = desc; }

    /**
     * Converts enum to string.
     * @return String representation of given achievement.
     */
    @Override
    public String toString() { return mDesc; }

    /**
     * Gets ID of the achievement.
     * @return ID of the achievement.
     */
    public int getId() { return mId; }
}
