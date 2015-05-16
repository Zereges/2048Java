package anims;

import java.awt.Point;
import defs.NumberedRect;

public abstract class Animation
{
    protected NumberedRect mSource;
    protected int mSpeed;
    protected Point mTarget;
    
    public Animation(NumberedRect source, Point target, int speed)
    {
        mSource = source;
        mTarget = target;
        mSpeed = speed;
    }

    public abstract boolean animate();
}
