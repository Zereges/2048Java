package windows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import main.Player;
import windows.components.FancyTextField;
import defs.Definitions;
import defs.UnrecoverableException;
import defs.UnrecoverableType;

/**
 * @brief Represents window used for picking player profile.
 */
public class PlayerPickerWindow extends Window
{
    private JLabel mInfoLabel = new JLabel("Select a profile to load. Profiles are saved in " + Definitions.SAVES_DIRECTORY + " folder.");
    private JComboBox<String> mPlayerProfiles = new JComboBox<>();
    private FancyTextField mNewProfileName = new FancyTextField("Type in desired name", false);
    private JButton mNewProfile = new JButton("Create new profile");
    private JButton mDeleteProfile = new JButton("Delete profile");
    private JButton mStartGame = new JButton("Load profile & Start game");
    public PlayerPickerWindow() throws UnrecoverableException
    {
        super(Definitions.WINDOW_TITLE, false);
        // New profile name
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
                mPlayerProfiles.addItem(p.getName());
                mPlayerProfiles.setSelectedItem(name);
            }
        });
        
        // Delete profile button
        mDeleteProfile.addActionListener((ActionEvent e) -> {
            if (!showConfirm("Are you sure?", "This will delete all the contents from the disc."))
                return;
            
            Player.delete(mPlayerProfiles.getItemAt(mPlayerProfiles.getSelectedIndex()));
            mPlayerProfiles.removeItemAt(mPlayerProfiles.getSelectedIndex());
            if (mPlayerProfiles.getItemCount() == 0)
                noProfiles();
        });
        
        // Start game button
        mStartGame.addActionListener((ActionEvent e) -> {
            mFrame.dispose();
            GameWindow window = new GameWindow(Player.load(mPlayerProfiles.getItemAt(mPlayerProfiles.getSelectedIndex())));
            window.show();
        });
        
        listPlayerProfiles();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        mFrame.setLayout(layout);
        
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
    }

    private void listPlayerProfiles() throws UnrecoverableException
    {
        File statsDir = new File(Definitions.SAVES_DIRECTORY);
        if (!statsDir.isDirectory())
            if (!statsDir.mkdir())
                throw new UnrecoverableException(UnrecoverableType.CANNOT_CREATE_STATS_DIR);
        File[] files = statsDir.listFiles((File dir, String name) -> { return name.endsWith(Definitions.SAVES_EXTENSION); });
        
        if (files.length == 0)
            noProfiles();
        else
        {
            for (int i = 0; i < files.length; ++i)
            {
                String playerName = files[i].getName();
                playerName = playerName.substring(0, playerName.lastIndexOf('.'));
                mPlayerProfiles.addItem(playerName);
            }
        }
    }
    
    private void noProfiles()
    {
        if (mPlayerProfiles.getItemCount() == 0)
        {
            String message = "No profiles found";
            mPlayerProfiles.addItem(message);
            mPlayerProfiles.setSelectedItem(message);
            mPlayerProfiles.setEnabled(false);
            mStartGame.setEnabled(false);
            mDeleteProfile.setEnabled(false);
        }        
    }
}
