package windows;

import java.io.Serializable;

/**
 * @brief Represents player profile
 */
public class Player implements Serializable
{
    /**
     * Randomly generated serial used for serialization
     */
    private static final long serialVersionUID = 5442151602381839138L;

    public Player(String player_name)
    {
        m_player_name = player_name;
    }
    
    private String m_player_name;
}
