package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import defs.Definitions;

/**
 * Represents player profile.
 */
public class Player implements Serializable
{
    /** Auto-generated for {@code Serializable} interface. */
    private static final long serialVersionUID = 5442151602381839138L;
    
    /** Name of the player. */
    private String mPlayerName;
    
    /** {@link Stats} of the player. */
    private Stats mStats = new Stats();

    /** {@link Achievements} of the player. */
    private Achievements mAchievements = new Achievements();

    
    /**
     * Constructs new player profile using name.
     * @param playerName Name for new player.
     */
    public Player(String playerName) { mPlayerName = playerName; }
    
    /**
     * Serializes player profile into the file. This will replace a file if it exists.
     * @return True on success, false otherwise.
     */
    public boolean save()
    {
        try (ObjectOutputStream filestream = new ObjectOutputStream(new FileOutputStream(getPlayerFile())))
        {
            mStats.resetCurrent();
            filestream.writeObject(this);
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }
    
    /**
     * Gets player name as String.
     * @return String representing player name.
     */
    public String getName() { return mPlayerName; } 
    
    /**
     * Sets new player name.
     * @param playerName New player name.
     */
    public void setName(String playerName) { mPlayerName = playerName; }
    
    /**
     * Deserializes player profile for requested player name.
     * @param playerName String representing player name.
     * @return Player class representing found profile in given file. If no profile could be found, returns null.
     */
    public static Player load(String playerName)
    {
        File file = new File(Definitions.SAVES_DIRECTORY, playerName + Definitions.SAVES_EXTENSION);
        if (!file.exists())
            return null;
        try (ObjectInputStream filestream = new ObjectInputStream(new FileInputStream(file)))
        {
            Player player = (Player) filestream.readObject();
            player.getStats().setLastUpdate(System.currentTimeMillis());
            return player;
        }
        catch (IOException | ClassNotFoundException e)
        {
            return null;
        }        
    }
    
    /** Deletes file representing player profile. If deletion fails, nothing happens. */
    public void delete() { getPlayerFile().delete(); }

    /**
     * Converts argument into File class representing serialized player profile.
     * @param playerName Player name to generate file from.
     * @return File representing abstract path to serialized player profile.
     */
    public static final File getPlayerFile(String playerName)
    {
        return new File(Definitions.SAVES_DIRECTORY + "/" + playerName + Definitions.SAVES_EXTENSION);
    }

    /**
     * Converts this player into File class representing serialized profile.
     * @return File representing abstract path to serialized player profile.
     */
    public File getPlayerFile() { return getPlayerFile(mPlayerName); }
    
    
    /**
     * Attempts to save a player to the file. If file already exists, this method will fail.
     * @return True if save was successful, false if not or file already exists.
     */
    public boolean trySave()
    {
        if (getPlayerFile().exists())
            return false;
        return save();
    }
    
    /**
     * Gets {@link Stats} for the player.
     * @return {@link Stats} of that player.
     */
    public Stats getStats() { return mStats; }

    /**
     * Gets {@link Stats} for the player.
     * @return {@link Stats} of that player.
     */
    public Achievements getAchievements() { return mAchievements; }
    
    /**
     * Converts player to string from its name.
     * @return Player name.
     */
    @Override
    public String toString() { return mPlayerName; }

    /**
     * Deletes a file containing player profile.
     * @param playerName Player name for which delete is requested.
     */
    public static void delete(String playerName)
    {
        File file = new File(Definitions.SAVES_DIRECTORY, playerName + Definitions.SAVES_EXTENSION);
        file.delete();
    }
}
