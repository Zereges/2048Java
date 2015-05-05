package windows.components;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import defs.Direction;

public class GameBoard extends JPanel
{
    private static final long serialVersionUID = 1L;

    private class MyDispatcher implements KeyEventDispatcher
    {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e)
        {
            if (e.getID() == KeyEvent.KEY_PRESSED)
                keyPressed(e.getKeyCode());
            return false;
        }
    }
    
    public GameBoard()
    {
        super(true); // double buffering.
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new MyDispatcher());
    }
    
    public void keyPressed(int keyCode)
    {
        switch (keyCode)
        {
        case KeyEvent.VK_LEFT:
            play(Direction.LEFT);
            break;
        case KeyEvent.VK_RIGHT:
            play(Direction.RIGHT);
            break;
        case KeyEvent.VK_UP:
            play(Direction.UP);
            break;
        case KeyEvent.VK_DOWN:
            play(Direction.DOWN);
            break;
        }
    }
    
    public void play(Direction direction)
    {
        
    }
}
