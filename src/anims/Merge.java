package anims;

import java.awt.Point;
import java.util.List;

import main.Game;
import defs.NumberedRect;

public class Merge extends Movement
{
    NumberedRect mTargetRect;
    List<NumberedRect> mAnimatedRects;
    
    public Merge(NumberedRect source, NumberedRect targetRect, List<NumberedRect> animatedRects, int speed)
    {
        super(source, targetRect.getPoint(), speed);
        mTargetRect = targetRect;
        mAnimatedRects = animatedRects;

    }

    public Merge(NumberedRect source, NumberedRect targetRect, List<NumberedRect> animatedRects)
    {
        super(source, targetRect.getPoint());
        mTargetRect = targetRect;
        mAnimatedRects = animatedRects;
    }
    
    @Override
    public boolean animate()
    {
        if (super.animate())
        {
            mAnimatedRects.clear();
            mTargetRect.updateShownNumber();
            return true;
        }
        return false;
    }
}
