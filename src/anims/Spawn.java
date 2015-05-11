package anims;

import java.awt.Point;

import defs.Definitions;
import defs.NumberedRect;

public class Spawn extends Animation
{
    private int mWidth = Definitions.MIN_BLOCK_SIZE;
    private int mHeight = Definitions.MIN_BLOCK_SIZE;
    
    public Spawn(NumberedRect source, Point target, int speed)
    {
        super(source, target, speed);
    }

    public Spawn(NumberedRect source, Point target)
    {
        super(source, target, Definitions.DEFAULT_SPAWN_SPEED);
    }
    
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
