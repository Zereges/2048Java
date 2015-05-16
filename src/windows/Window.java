package windows;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Abstract class representing OS window. Implemented using {@code JFrame}.
 */
public abstract class Window
{
    /** {@code JFrame}. */
    protected JFrame mFrame;
    
    /**
     * Constructs a window.
     * @param title Title to be shown.
     */
    public Window(String title)
    {
        mFrame = new JFrame(title);
        mFrame.setLocationByPlatform(true);
        mFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    /**
     * Constructs a window.
     * @param title Title to be shown.
     * @param resizable Indicates whether created window should be resizable or not.
     */
    public Window(String title, boolean resizable)
    {
        this(title);
        mFrame.setResizable(resizable);
    }
    
    /**
     * Displays warning to the user.
     * @param title Title of warning.
     * @param message Message of warning.
     */
    public void showWarning(String title, String message)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);        
    }
    
    /**
     * Displays confirmation windows and waits for the user to perform an action.
     * @param title Title of the confirmation.
     * @param message Message to the user.
     * @return True if user clicked Yes, false otherwise.
     */
    public boolean showConfirm(String title, String message)
    {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }

    /** Shows a window using {@code SwingUtilities}. */
    public void show() { SwingUtilities.invokeLater(() -> { mFrame.setVisible(true); }); }
    
    /** Closes a window. */
    public void close() { mFrame.dispose(); }
}
