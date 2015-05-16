package anims;

import java.awt.Point;
import defs.NumberedRect;

/**
 * An abstract animation passed to the {@link main.Animator} class.
 * @see main.Animator
 * @see Merge
 * @see Movement
 * @see Spawn
 */
public abstract class Animation
{
    /** NumberedRect on which animation is performed. */
    protected NumberedRect mSource;
        
    /** Point, where should the animation end. */
    protected Point mTarget;

    /** Speed of the animation in pixels per frame. */ 
    protected int mSpeed;
    
    /**
     * Base constructor for Animation class.
     * @param source Animated {@link defs.NumberedRect}.
     * @param target Target of the animation.
     * @param speed Speed of the animation.
     */
    public Animation(NumberedRect source, Point target, int speed)
    {
        mSource = source;
        mTarget = target;
        mSpeed = speed;
    }

    /** 
     * Method responsible for performing the animation on {@code mSource}.
     * @return True if the animation is completed after the call, false otherwise.
     */
    public abstract boolean animate();
}
