package defs;

public enum StatTypes
{
    LEFT_MOVES("Left moves", 0),
    RIGHT_MOVES("Right moves", 1),
    UP_MOVES("Up moves", 2),
    DOWN_MOVES("Down moves", 3),
    TOTAL_MOVES("Total moves", 4),
    BLOCKS_MOVED("Blocks moved", 5),
    BLOCKS_MERGED("Blocks merged", 6),
    GAME_RESTARTS("Game restarts", 7),
    GAME_WINS("Games won", 8),
    GAME_LOSES("Games lost", 9),
    TOTAL_TIME_PLAYED("Total seconds spent playing", 10),
    TOTAL_SCORE("Total score gained", 11),
    HIGHEST_SCORE("Highest score obtained", 12),
    MAXIMAL_BLOCK("Maximal block", 13);
    
    private String mDesc;
    private int mId;
    StatTypes(String desc, int id) { mDesc = desc; mId = id; }
    
    @Override
    public String toString() { return mDesc; }

    public int getIndex() { return mId; }
}
