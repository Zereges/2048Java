package windows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;




import java.io.FileFilter;
import java.io.FilenameFilter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;










import windows.components.FancyTextField;
import main.Player;
import defs.Definitions;
import defs.UnrecoverableException;
import defs.UnrecovableType;

/**
 * @brief Represents window used for picking player profile.
 */
public class PlayerPickerWindow
{
    private JFrame mFrame = new JFrame("2048 Game");
    private JLabel mInfoLabel = new JLabel("Select a profile to load. Profiles are saved in " + Definitions.SAVES_DIRECTORY + " folder.");
    private JComboBox<String> mPlayerProfiles = new JComboBox<>();
    private FancyTextField mNewProfileName = new FancyTextField("Type in desired name", false);
    private JButton mNewProfileButton = new JButton("Create new profile");
    private JButton mStartGame = new JButton("Load profile & Start game");
    public PlayerPickerWindow() throws UnrecoverableException
    {
        mNewProfileButton.addActionListener((ActionEvent e) -> {
            if (!mNewProfileName.isValid())
                return;
            String name = mNewProfileName.getText();
            Player p = new Player(name);
            if (!p.trySave())
            {
                mNewProfileName.setText("That player name already exists", false);
            }
            else
            {
                if (!mPlayerProfiles.isEnabled())
                {
                    mPlayerProfiles.setEnabled(true);
                    mPlayerProfiles.removeAllItems();
                    mStartGame.setEnabled(true);
                }
                mPlayerProfiles.addItem(name);
                mPlayerProfiles.setSelectedItem(name);
            }
        });
        
        Player[] playerProfiles = listPlayerProfiles();
        SwingUtilities.invokeLater(() -> {
            GridBagLayout layout = new GridBagLayout();
            GridBagConstraints constraints = new GridBagConstraints();
            mFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mFrame.setLayout(layout);
            mFrame.setResizable(false);
            
            constraints.fill = GridBagConstraints.BOTH;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            constraints.weightx = 1;
            constraints.gridx = 0; constraints.gridy = 0;
            mFrame.getContentPane().add(mInfoLabel, constraints);
            
            constraints.gridx = 0; constraints.gridy = 1;
            mFrame.getContentPane().add(mPlayerProfiles, constraints);
            
            constraints.weightx = 1.0;
            constraints.gridwidth = GridBagConstraints.RELATIVE;
            constraints.gridx = 0; constraints.gridy = 2;
            mFrame.getContentPane().add(mNewProfileName, constraints);

            constraints.weightx = 0.0;
            constraints.gridx = 1; constraints.gridy = 2;
            mFrame.getContentPane().add(mNewProfileButton, constraints);
            
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            constraints.weightx = 1;
            constraints.gridx = 0; constraints.gridy = 3;
            mFrame.getContentPane().add(mStartGame, constraints);
            
            mFrame.pack();
            mFrame.setVisible(true);
        });
    }
    
    private Player[] listPlayerProfiles() throws UnrecoverableException
    {
        File statsDir = new File(Definitions.SAVES_DIRECTORY);
        if (!statsDir.isDirectory())
            if (!statsDir.mkdir())
                throw new UnrecoverableException(UnrecovableType.CANNOT_CREATE_STATS_DIR);
        File[] files = statsDir.listFiles((File dir, String name) -> { return name.endsWith(Definitions.SAVES_EXTENSION); });
        
        Player[] playerProfiles = new Player[files.length];
        if (files.length == 0)
        {
            String message = "No profiles found";
            mPlayerProfiles.addItem(message);
            mPlayerProfiles.setSelectedItem(message);
            mPlayerProfiles.setEnabled(false);
            mStartGame.setEnabled(false);
        }
        else
        {
            for (int i = 0; i < files.length; ++i)
            {
                playerProfiles[i] = Player.load(files[i]);
                mPlayerProfiles.addItem(playerProfiles[i].getName());
            }
        }
        return playerProfiles;
    }
}
