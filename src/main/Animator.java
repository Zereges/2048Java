package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import anims.Animation;
import defs.Definitions;

/**
 * Is responsible for animating events on the game field.
 * @see Animation
 */
public class Animator
{
    /** List of {@link anims.Animation Animation} events. */
    private List<Animation> mAnimations = new ArrayList<>();
    
    /** Reference to {@link Game}. */
    private Game mGame;
    
    /** Reference to lock used to synchronize access to {@link #mAnimations}. */
    private Lock mLock;
    
    /**
     * Constructs Animator for the Game.
     * @param game Reference to {@link Game}
     */
    public Animator(Game game, Lock lock) { mGame = game; mLock = lock; }
    
    /**
     * Passes {@code animation} to the Animator class.
     * No animation is performed until {@link #startAnimation()} is called.
     * @param animation {@link anims.Animation} to be added.
     */
    public void add(Animation animation) { mAnimations.add(animation); }
    
    /**
     * Passes {@code animation} to the Animator class and starts animating immediately.
     * @param animation {@link anims.Animation} to be added.
     */
    public void addAndStart(Animation animation)
    {
        mLock.lock();
        if (mAnimations.size() == 0)
        {
            mLock.unlock();
            mAnimations.add(animation);
            startAnimation();
        }
        else
        {
            mAnimations.add(animation);
            mLock.unlock();
        }
    }
    
    /** Removes all animations in progress. */
    public void clear() { mAnimations.clear(); }
    
    /** Start animating in new thread. */
    public void startAnimation()
    {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                long nextTick = System.currentTimeMillis();
                long sleepTime;
                mLock.lock();
                while (mAnimations.size() != 0)
                {
                    animate();
                    mLock.unlock();
                    mGame.repaint();
                    nextTick += 1000 / Definitions.FRAMES_PER_SECOND;
                    sleepTime = nextTick - System.currentTimeMillis();
                    if (sleepTime > 0)
                    {
                        try
                        {
                            Thread.sleep(sleepTime);
                        }
                        catch (InterruptedException e) { }
                    }
                    mLock.lock();
                }
                mLock.unlock();
            }
        };
        thread.start();
    }

    /**
     * Checks whether all animations finished.
     * @return True if all animations finished, false otherwise.
     */
    public boolean hasFinished() { return mAnimations.size() == 0; }

    /** Performs one step of the animation. This is called about {@link Definitions#FRAMES_PER_SECOND} times in a second. */
    private void animate()
    {
        mAnimations.removeIf((Animation animation) -> { return animation.animate(); });
    }
}
