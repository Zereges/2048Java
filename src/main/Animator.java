package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
    
    /** Lock used to synchronize access to {@link } */
    private Lock lock = new ReentrantLock();
    
    /**
     * Constructs Animator for the Game.
     * @param game Reference to {@link Game}
     */
    public Animator(Game game) { mGame = game; }
    
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
        lock.lock();
        if (mAnimations.size() == 0)
        {
            lock.unlock();
            mAnimations.add(animation);
            startAnimation();
        }
        else
        {
            mAnimations.add(animation);
            lock.unlock();
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
                lock.lock();
                while (mAnimations.size() != 0)
                {
                    animate();
                    lock.unlock();
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
                    lock.lock();
                }
                lock.unlock();
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
    private void animate() { mAnimations.removeIf((Animation animation) -> { return animation.animate(); }); }
}
