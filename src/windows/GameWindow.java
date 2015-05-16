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

public class GameWindow extends Window
{
    private Game mGame;
    private Player mPlayer;
    private ScoreLabel mScore = new ScoreLabel();
    private JMenuBar mMenuBar = new JMenuBar();
    private JButton mStats = new JButton("Show Statistics");
    private JButton mRestart = new JButton("Restart");
    private StatsWindow mStatsWindow;

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
                mFrame.dispose();
            }
        });
        mGame = new Game(mPlayer, this);
        mGame.setPreferredSize(Definitions.getMinDimension());
        mGame.setSize(Definitions.getMinDimension());
        mGame.setBackground(Color.BLUE);
        mMenuBar.add(mStats);
        mMenuBar.add(mRestart);
        
        mRestart.addActionListener((ActionEvent e) -> { mGame.restart(); });
        mStats.addActionListener((ActionEvent e) -> {
            mGame.updateStatsTime();
            mStatsWindow.show();
        });
        mStatsWindow = new StatsWindow("2048 Stats for " + mPlayer, mPlayer.getStats());


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
    
    public ScoreLabel getScoreLabel() { return mScore; }
}
