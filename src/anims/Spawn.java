package anims;

import java.awt.Point;

import defs.Definitions;
import defs.NumberedRect;

/**
 * Class used for animating the spawn of the {@link defs.NumberedRect} on the game filed.
 * @see Animation
 */
public class Spawn extends Animation
{
    /** Target width of {@code mSource} */
    private int mWidth = Definitions.MIN_BLOCK_SIZE;

    /** Target height of {@code mSource} */
    private int mHeight = Definitions.MIN_BLOCK_SIZE;
    
    /**
     * Constructs Spawn animation for given arguments.
     * @param source {@link defs.NumberedRect} to spawn.
     * @param target Target point of the Spawn animation.
     * @param speed Speed of the spawning of the {@code mSource} in pixels per frame.
     */
    public Spawn(NumberedRect source, Point target, int speed)
    {
        super(source, target, speed);
    }

    /**
     * Constructs Spawn animation for given arguments.
     * @param source {@link defs.NumberedRect} to spawn.
     * @param target Target point of the Spawn animation.
     */
    public Spawn(NumberedRect source, Point target)
    {
        super(source, target, Definitions.DEFAULT_SPAWN_SPEED);
    }
    
    /**
     * Method responsible for performing the spawning of {@code mSource}.
     * @return True if animation has finished, false otherwise.
     * @see Animation#animate()
     */
    @Override
    public boolean animate()
    {
        mSource.setWidth(mSource.getWidth() + mSpeed);
        if (mSource.getWidth() >= mWidth)
            mSource.setWidth(mWidth);

        mSource.setHeight(mSource.getHeight() + mSpeed);
        if (mSource.getHeight() >= mHeight)
            mSource.setHeight(mHeight);
        
        mSource.setX(mTarget.x + (mWidth - mSource.getWidth()) / 2);
        mSource.setY(mTarget.y + (mHeight - mSource.getHeight()) / 2);
        mSource.setCurrentFontSize((int) (mSource.getHeight() / (mHeight + 0.0) * mSource.getFontSize()));
        
        return mSource.getWidth() == mWidth && mSource.getHeight() == mHeight;
    }
}
