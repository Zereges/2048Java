package windows;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import defs.Definitions;
import defs.Player;
import windows.components.ScoreLabel;
import main.Game;

public class GameWindow
{
    private JFrame mFrame;
    private Game mGame;
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
        mFrame.setResizable(false);
        mFrame.setLocationByPlatform(true);
        mFrame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
                mPlayer.save();
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
    }
    
    void show()
    {
        SwingUtilities.invokeLater(() -> {
            mFrame.setVisible(true);
        });
    }

    public void setScore(String score)
    {
        mScore.setText("Score: " + score);
    }
    
    public void showWarning(String title, String message)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);        
    }
}
