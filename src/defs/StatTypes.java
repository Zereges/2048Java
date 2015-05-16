package defs;

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
    
    private final int mId;
    private final String mDesc;
    StatTypes(int id, String desc) { mId = id; mDesc = desc; }
    
    @Override
    public String toString() { return mDesc; }

    public int getIndex() { return mId; }
}
