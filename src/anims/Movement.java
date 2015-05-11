package anims;

import java.awt.Point;

import defs.Definitions;
import defs.NumberedRect;

public class Movement extends Animation
{
    public Movement(NumberedRect source, Point target, int speed)
    {
        super(source, target, speed);
    }

    public Movement(NumberedRect source, Point target)
    {
        super(source, target, Definitions.DEFAULT_MOVE_SPEED);
    }
    
    @Override
    public boolean animate()
    {
        Point point = mSource.getPoint();
        
        if (mTarget.x - point.x > mSpeed)
            point.x += mSpeed;
        else if (mTarget.x - point.x < -mSpeed)
            point.x -= mSpeed;
        else
            point.x = mTarget.x;

        if (mTarget.y - point.y > mSpeed)
            point.y += mSpeed;
        else if (mTarget.y - point.y < -mSpeed)
            point.y -= mSpeed;
        else
            point.y = mTarget.y;

        return point.x == mTarget.x && point.y == mTarget.y;        
    }
}
