package windows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    private JComboBox<Player> mPlayerProfiles = new JComboBox<>();
    private FancyTextField mNewProfileName = new FancyTextField("Type in desired name", false);
    private JButton mNewProfile = new JButton("Create new profile");
    private JButton mDeleteProfile = new JButton("Delete profile");
    private JButton mStartGame = new JButton("Load profile & Start game");
    public PlayerPickerWindow() throws UnrecoverableException
    {
        mNewProfile.addActionListener((ActionEvent e) -> {
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
                    mDeleteProfile.setEnabled(true);
                }
                mPlayerProfiles.addItem(p);
                mPlayerProfiles.setSelectedItem(name);
            }
        });
        
        mDeleteProfile.addActionListener((ActionEvent e) -> {
            // ToDo simple warining confirmation window.
            if (mPlayerProfiles.isEnabled())
            {
                Player p = mPlayerProfiles.getItemAt(mPlayerProfiles.getSelectedIndex());
                p.delete();
                mPlayerProfiles.removeItem(p);
                if (mPlayerProfiles.getItemCount() == 0)
                    noProfiles();
            }
        });
        
        mStartGame.addActionListener((ActionEvent e) -> {
            mFrame.dispose();
        });
        
        listPlayerProfiles();
        
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
            
            constraints.gridwidth = GridBagConstraints.RELATIVE;
            constraints.weightx = 1.0;
            constraints.gridx = 0; constraints.gridy = 1;
            mFrame.getContentPane().add(mPlayerProfiles, constraints);
            
            constraints.weightx = 0.0;
            constraints.gridx = 1; constraints.gridy = 1;
            mFrame.getContentPane().add(mDeleteProfile, constraints);
            
            constraints.gridx = 0; constraints.gridy = 2;
            mFrame.getContentPane().add(mNewProfileName, constraints);

            constraints.weightx = 0.0;
            constraints.gridx = 1; constraints.gridy = 2;
            mFrame.getContentPane().add(mNewProfile, constraints);
            
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            constraints.weightx = 1;
            constraints.gridx = 0; constraints.gridy = 3;
            mFrame.getContentPane().add(mStartGame, constraints);
            
            mFrame.pack();
            mFrame.setVisible(true);
        });
    }
    
    private void listPlayerProfiles() throws UnrecoverableException
    {
        File statsDir = new File(Definitions.SAVES_DIRECTORY);
        if (!statsDir.isDirectory())
            if (!statsDir.mkdir())
                throw new UnrecoverableException(UnrecovableType.CANNOT_CREATE_STATS_DIR);
        File[] files = statsDir.listFiles((File dir, String name) -> { return name.endsWith(Definitions.SAVES_EXTENSION); });
        
        if (files.length == 0)
            noProfiles();
        else
        {
            for (int i = 0; i < files.length; ++i)
                mPlayerProfiles.addItem(Player.load(files[i]));
        }
    }
    
    private void noProfiles()
    {
        if (mPlayerProfiles.getItemCount() == 0)
        {
            Player message = new Player("No profiles found");
            mPlayerProfiles.addItem(message);
            mPlayerProfiles.setSelectedItem(message);
            mPlayerProfiles.setEnabled(false);
            mStartGame.setEnabled(false);
            mDeleteProfile.setEnabled(false);                
        }        
    }
}
