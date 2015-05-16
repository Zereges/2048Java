package anims;

import java.util.List;
import defs.NumberedRect;

/**
 * Class used for animating merge of two Blocks on the game field.
 * @see Animation
 */
public class Merge extends Movement
{
    /** Merge target of the merge conflict. Target is the one of two blocks, which is moving less. */
    private NumberedRect mTargetRect;
    
    /** When animation is finished, the class will remove animated rect from the list. */
    private List<NumberedRect> mAnimatedRects;
    
    /**
     * Constructs Merge class for given arguments.
     * @param source Merging {@link defs.NumberedRect} on which animation is performed.
     * @param targetRect Merged {@link defs.NumberedRect} as target of the animation.
     * @param animatedRects When animation is finished, the class will remove animated rect from the list.
     * @param speed Speed of the animation in pixels per frame.
     */
    public Merge(NumberedRect source, NumberedRect targetRect, List<NumberedRect> animatedRects, int speed)
    {
        super(source, targetRect.getPoint(), speed);
        mTargetRect = targetRect;
        mAnimatedRects = animatedRects;
    }
    
    /**
     * Constructs Merge class for given arguments.
     * @param source Merging {@link defs.NumberedRect} on which animation is performed.
     * @param targetRect Merged {@link defs.NumberedRect} as target of the animation.
     * @param animatedRects When animation is finished, the class will remove animated rect from the list.
     */    
    public Merge(NumberedRect source, NumberedRect targetRect, List<NumberedRect> animatedRects)
    {
        super(source, targetRect.getPoint());
        mTargetRect = targetRect;
        mAnimatedRects = animatedRects;
    }
    
    /**
     * Method responsible for performing the merge of {@code mSource} to {@code mTargetRect}.
     * If the animation is finished, it will remove {@code mSource} from {@code mAnimatedRects}.
     * @return True if animation has finished, false otherwise.
     * @see Animation#animate()
     */
    @Override
    public boolean animate()
    {
        if (super.animate())
        {
            mAnimatedRects.remove(mSource);
            mTargetRect.updateShownNumber();
            return true;
        }
        return false;
    }
}
