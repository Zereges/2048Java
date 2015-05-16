package defs;

/**
 * Represents achievements database.
 * @see main.Stats
 * @see windows.StatsWindow
 */
public enum StatTypes
{
    LEFT_MOVES(0, "Left moves"),
    RIGHT_MOVES(1, "Right moves"),
    UP_MOVES(2, "Up moves"),
    DOWN_MOVES(3, "Down moves"),
    TOTAL_MOVES(4, "Total moves"),
    BLOCKS_MOVED(5, "Blocks moved"),
    BLOCKS_MERGED(6, "Blocks merged"),
    GAME_RESTARTS(7, "Game restarts"),
    GAME_WINS(8, "Games won"),
    GAME_LOSES(9, "Games lost"),
    TOTAL_TIME_PLAYED(10, "Total seconds spent playing"),
    TOTAL_SCORE(11, "Total score gained"),
    HIGHEST_SCORE(12, "Highest score obtained"),
    MAXIMAL_BLOCK(13, "Maximal block");
    
    /** ID of the stat. */
    private final int mId;
    
    /** Description of the stat. */
    private final String mDesc;
    
    /**
     * Constructor for enum members.
     * @param id ID of the achievement.
     * @param desc Description of the achievement.
     */    
    StatTypes(int id, String desc) { mId = id; mDesc = desc; }
    
    /**
     * Converts enum to string.
     * @return String representation of given stat.
     */
    @Override
    public String toString() { return mDesc; }

    /**
     * Gets ID of the stat.
     * @return ID of the stat.
     */
    public int getId() { return mId; }
}
