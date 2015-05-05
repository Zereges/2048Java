package windows;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import defs.Definitions;
import windows.components.GameBoard;
import windows.components.ScoreLabel;
import main.Player;

public class GameWindow
{
    private JFrame mFrame;
    private GameBoard mGameBoard = new GameBoard();
    private Player mPlayer;
    private ScoreLabel mScore = new ScoreLabel();
    private JMenuBar mMenuBar = new JMenuBar();
    private JMenu mMenu = new JMenu("Show Statistics");

    /**
     * Constructs game window for specified player profile.
     * @param player Player profile for current game.
     */
    public GameWindow(Player player)
    {
        mPlayer = player;
        mFrame = new JFrame("2048 Game (Playing as " + mPlayer + ")");
        mGameBoard.setPreferredSize(Definitions.getMinDimension());
        mFrame.setResizable(false);
        mGameBoard.setBackground(Color.BLUE);
        mMenuBar.add(mMenu);
        
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
            mFrame.getContentPane().add(mGameBoard, constraints);
            
            mFrame.setJMenuBar(mMenuBar);
            mFrame.pack();
            mFrame.setVisible(true);
        });
    }
    
}
