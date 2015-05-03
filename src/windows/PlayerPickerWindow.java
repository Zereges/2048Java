package windows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


import defs.Definitions;

/**
 * @brief Represents window used for picking player profile.
 */
public class PlayerPickerWindow
{
    private JFrame mFrame = new JFrame("2048 Game");
    private JLabel mInfoLabel = new JLabel("Select a profile to load. Profiles are saved in " + Definitions.SAVES_DIRECTORY + " folder.");
    private JComboBox<String> mPlayerProfiles = new JComboBox<>();
    private JTextField mNewProfileName = new JTextField("Profile Name");
    private JButton mNewProfileButton = new JButton("New Profile");
    private JButton mStartGame = new JButton("Load Profile & Start game");
    public PlayerPickerWindow()
    {
        File statsDir = new File(Definitions.SAVES_DIRECTORY);
        mPlayerProfiles.addItem("Test 1");
        mPlayerProfiles.addItem("Test 2");
        mPlayerProfiles.addItem("Test 3");
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
}
