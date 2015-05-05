package windows.components;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * @brief Modified JTextField to allow user friendly both input and output. 
 *
 */
public class FancyTextField extends JTextField
{
    private static final long serialVersionUID = 1L;
    private boolean mValid;
    private Color mDefaultColor;
    private String mDefaultText;
    
    /**
     * Constructs basic FancyTextField according to {@link JTextField#JTextField(String) JTextField constructor}.
     * @param text Text to show in FancyTextField.
     * @param valid Indicates whether the text is valid or not and should be deleted after component gains focus.
     */
    public FancyTextField(String text, Boolean valid)
    {
        super(text);
        mDefaultText = text;
        mValid = valid;
        mDefaultColor = getForeground();
        if (!mValid)
            setForeground(Color.LIGHT_GRAY);
        
        addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e)
            {
                if (!mValid)
                    setText("", true);
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                if ("".equals(getText()))
                    setText(mDefaultText, false);
            }            
        });
    }
    
    /**
     * Sets valid or invalid text of the FancyTextField.
     * @param text Text to set into FancyTextField.
     * @param valid If {@code true}, the text presented into the component is valid. Otherwise it is not.
     */
    public void setText(String text, boolean valid)
    {
        super.setText(text);
        setValid(valid);
    }

    /**
     * Checks whether the {@code FancyTextField} contains valid text or not.
     * @return True if the component contains valid text, false otherwise.
     */
    public boolean isValid() { return mValid; }
    
    private void setValid(boolean valid)
    {
        setForeground(valid ? mDefaultColor : Color.LIGHT_GRAY);
        mValid = valid;
    }
}
