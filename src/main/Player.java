package main;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import defs.Definitions;

/**
 * @brief Represents player profile
 */
public class Player implements Serializable
{
    /**
     * Randomly generated serial used for serialization
     */
    private static final long serialVersionUID = 5442151602381839138L;
    
    /**
     * Constructs new player profile using name.
     * @param player_name Name for new player.
     */
    public Player(String player_name)
    {
        mPlayerName = player_name;
    }
    
    /**
     * Serializes player profile into the file. This will replace a file if it exists.
     * @return True on success, false otherwise.
     * @throws IOException
     */
    public boolean save()
    {
        try (ObjectOutputStream filestream = new ObjectOutputStream(new FileOutputStream(Definitions.getPlayerFile(mPlayerName))))
        {
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
     * @param player_name New player name.
     */
    public void setName(String player_name) { mPlayerName = player_name; }
    
    /**
     * Deserializes player profile from the given file.
     * @param file File to look in for serialized player
     * @return Player class representing found profile in given file. If no profile could be found, returns null.
     * @throws IOException
     */
    public static Player load(File file)
    {
        try (ObjectInputStream filestream = new ObjectInputStream(new FileInputStream(file)))
        {
            return (Player) filestream.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            return null;
        }        
    }
    
    /**
     * Attempts to save a player to the file. If file already exists, this method will fail.
     * @return True if save was successful, false if not or file already exists.
     */
    public boolean trySave()
    {
        if (Definitions.getPlayerFile(mPlayerName).exists())
            return false;
        return save();
    }
    
    /** Name of the player. */
    private String mPlayerName;


}
