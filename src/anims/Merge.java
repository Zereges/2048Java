package anims;

import java.awt.Point;

import main.Game;
import defs.NumberedRect;

public class Merge extends Movement
{
    NumberedRect mTargetRect;
    Game mGame;
    int mFromX, mFromY;
    
    public Merge(NumberedRect source, NumberedRect targetRect, Game game, int fromX, int fromY, int speed)
    {
        super(source, targetRect.getPoint(), speed);
        mTargetRect = targetRect;
        mGame = game;
        mFromX = fromX;
        mFromY = fromY;
    }

    public Merge(NumberedRect source, NumberedRect targetRect, Game game, int fromX, int fromY)
    {
        super(source, targetRect.getPoint());
        mTargetRect = targetRect;
        mGame = game;
        mFromX = fromX;
        mFromY = fromY;
    }
    
    @Override
    public boolean animate()
    {
        if (super.animate())
        {
            mGame.removeBlock(mFromX, mFromY);
            mTargetRect.nextNumber();
            return true;
        }
        return false;
    }
}
