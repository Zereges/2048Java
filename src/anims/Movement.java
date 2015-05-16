package anims;

import java.awt.Point;
import defs.Definitions;
import defs.NumberedRect;

/**
 * Class used for animating movement on the game field.
 * @see Animation
 */
public class Movement extends Animation
{
    /**
     * Constructs Movement class for given arguments.
     * @param source Moving {@link defs.NumberedRect}.
     * @param target End point of the movement.
     * @param speed Speed of the movement in pixels per frame.
     */
    public Movement(NumberedRect source, Point target, int speed)
    {
        super(source, target, speed);
    }

    /**
     * Constructs Movement class for given arguments.
     * @param source Moving {@link defs.NumberedRect}.
     * @param target End point of the movement.
     */
    public Movement(NumberedRect source, Point target)
    {
        super(source, target, Definitions.DEFAULT_MOVE_SPEED);
    }

    /**
     * Method responsible for performing the moving of {@code mSource} to {@code mPoint}.
     * @return True if animation has finished, false otherwise.
     * @see Animation#animate()
     */
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
