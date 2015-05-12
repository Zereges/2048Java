package windows.components;

import javax.swing.JLabel;

/**
 * @brief Label used for displaying score in current game.
 * 
 */
public class ScoreLabel extends JLabel {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor of ScoreLabel.
     */
    public ScoreLabel()
    {
        setText("Score: " + mScore);
    }
    
    /**
     * Sets current score to given value.
     * @param score Value to set current score to.
     */
    public void setScore(int score) { mScore = score; updateScore(); }
    
    /** 
     * Increments current score by given difference.
     * @param diff Difference to add to current score.
     */
    public void addScore(int diff) { mScore += diff; updateScore(); }
    
    /**
     * Gets the value of current score.
     * @return Current player's score.
     */
    public int getScore() { return mScore; }
    
    /**
     * Sets whether player won this game.
     * @param won True if player won this game, false otherwise.
     */
    public void setWon(boolean won) { mWon = won; } 
    
    private void updateScore() { setText("Score: " + mScore + (mWon ? " (Won)" : "")); }
    
    /** Current player's score. */
    private int mScore = 0;
    
    /** Indicates, whether player won this game. */
    private boolean mWon = false;
}
