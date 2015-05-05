package defs;

import java.io.File;

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
    
    /**
     * Converts argument into File class representing serialized player profile.
     * @param player_name Player name to generate file from.
     * @return File representing abstracct path to serialized player profile.
     * @see Player
     */
    public static final File getPlayerFile(String player_name)
    {
        return new File(SAVES_DIRECTORY + "/" + player_name + ".sav");
    }
}
