package main;
import defs.UnrecoverableException;
import windows.PlayerPickerWindow;

/**
 * @author Filip Kliber (Zereges<at>gmail.com)
 * @brief Main application entry point.
 */
public class Program
{
    /**
     * @param args Command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            new PlayerPickerWindow();
        }
        catch (UnrecoverableException e)
        {
            
        }
    }
}
