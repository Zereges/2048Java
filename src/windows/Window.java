package windows;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Window
{
    protected JFrame mFrame;
    public Window(String title)
    {
        mFrame = new JFrame(title);
        mFrame.setLocationByPlatform(true);
        mFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public Window(String title, boolean resizable)
    {
        this(title);
        mFrame.setResizable(resizable);
    }
    
    public void showWarning(String title, String message)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);        
    }
    
    public boolean showConfirm(String title, String message)
    {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }

    public void show()
    {
        SwingUtilities.invokeLater(() -> {
            mFrame.setVisible(true);
        });
    }
    
    public void hide()
    {
        mFrame.setVisible(false);
    }
    
    public void close()
    {
        mFrame.dispose();
    }
}
