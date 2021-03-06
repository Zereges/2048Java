package windows.components;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 * Modified {@code JTextField} to allow user friendly both input and output.
 * @see windows.PlayerPickerWindow
 */
public class FancyTextField extends JTextField
{
    /** Auto-generated for {@code Serializable} interface. */
    private static final long serialVersionUID = 1L;
    
    /** Indicates whether text in text field is valid or not. */
    private boolean mValid;
    
    /** Default text color. */
    private Color mDefaultColor;
    
    /** Default text in text field. */
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
    
    /**
     * Sets validity of the text in the text field.
     * @param valid Indicates whether text is set as valid or not.
     */
    private void setValid(boolean valid)
    {
        setForeground(valid ? mDefaultColor : Color.LIGHT_GRAY);
        mValid = valid;
    }
}
