package windows;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import defs.Definitions;
import windows.components.ScoreLabel;
import main.GameBoard;
import main.Player;

public class GameWindow
{
    private JFrame mFrame;
    private GameBoard mGame = new GameBoard();
    private Player mPlayer;
    private ScoreLabel mScore = new ScoreLabel();
    private JMenuBar mMenuBar = new JMenuBar();
    private JButton mStats = new JButton("Show Statistics");
    private JButton mRestart = new JButton("Restart");

    /**
     * Constructs game window for specified player profile.
     * @param player Player profile for current game.
     */
    public GameWindow(Player player)
    {
        mPlayer = player;
        mFrame = new JFrame("2048 Game (Playing as " + mPlayer + ")");
        mGame.setPreferredSize(Definitions.getMinDimension());
        mFrame.setResizable(false);
        mGame.setBackground(Color.BLUE);
        mMenuBar.add(mStats);
        mMenuBar.add(mRestart);
        
        mRestart.addActionListener((ActionEvent e) -> { mGame.restart(); });
        
        SwingUtilities.invokeLater(() -> {
            GridBagLayout layout = new GridBagLayout();
            GridBagConstraints constraints = new GridBagConstraints();
            mFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
            mFrame.setVisible(true);
        });
    }
    
}
