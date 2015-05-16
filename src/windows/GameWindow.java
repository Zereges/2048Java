package windows;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import defs.Definitions;
import windows.components.ScoreLabel;
import main.Game;
import main.Player;

/**
 * Represents base game window.
 * @see Window
 */
public class GameWindow extends Window
{
    /** Reference to {@link main.Game} being played. */
    private Game mGame;
    
    /** Reference to {@link main.Player} playing the game. */
    private Player mPlayer;
    
    /** {@link windows.components.ScoreLabel} for showing current score. */
    private ScoreLabel mScore = new ScoreLabel();
    
    /** {@code JMenuBar}. */
    private JMenuBar mMenuBar = new JMenuBar();

    /** Show Statistics {@code JButton}. */
    private JButton mStats = new JButton("Show Statistics");
    
    /** Show Achievements {@code JButton}. */
    private JButton mAchievements = new JButton("Show Achievements");
    
    /** Restart {@code JButton}. */
    private JButton mRestart = new JButton("Restart");
    
    /** {@link StatsWindow} which is shown after {@link mStats} click. */
    private StatsWindow mStatsWindow;

    /** {@link AchievementsWindow} which is shown after {@link mAchievements} click. */
    private AchievementsWindow mAchievementsWindow;

    /**
     * Constructs game window for specified player profile.
     * @param player Player profile for current game.
     */
    public GameWindow(Player player)
    {
        super(Definitions.WINDOW_TITLE + " (Playing as " + player + ")", false);
        mPlayer = player;
        mFrame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
                mGame.updateStatsTime();
                mPlayer.save();
                mStatsWindow.close();
                mAchievementsWindow.close();
                mFrame.dispose();
            }
        });
        mGame = new Game(mPlayer, this);
        mGame.setPreferredSize(Definitions.getMinDimension());
        mGame.setSize(Definitions.getMinDimension());
        mGame.setBackground(Color.BLUE);
        mMenuBar.add(mStats);
        mMenuBar.add(mAchievements);
        mMenuBar.add(mRestart);
        
        mRestart.addActionListener((ActionEvent e) -> { mGame.restart(); });
        mStats.addActionListener((ActionEvent e) -> {
            mGame.updateStatsTime();
            mStatsWindow.show();
        });
        mAchievements.addActionListener((ActionEvent e) -> {
            mAchievementsWindow.show();
        });
        mStatsWindow = new StatsWindow(mPlayer.getStats());
        mAchievementsWindow = new AchievementsWindow(mPlayer.getAchievements());

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        mFrame.setLayout(layout);
        
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 0;
        constraints.gridx = 0; constraints.gridy = 0;
        mFrame.getContentPane().add(mScore, constraints);
        
        constraints.weightx = 1;
        constraints.gridx = 0; constraints.gridy = 1;
        mFrame.getContentPane().add(mGame, constraints);
        
        mFrame.setJMenuBar(mMenuBar);
        mFrame.pack();
    }
    
    /**
     * Getter for {@link windows.components.ScoreLabel}.
     * @return {@link windows.components.ScoreLabel} for showing score.
     */
    public ScoreLabel getScoreLabel() { return mScore; }
}
