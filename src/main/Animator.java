package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import anims.Animation;
import defs.Definitions;

public class Animator
{
    private List<Animation> mAnimations = new ArrayList<>();
    private Game mGame;
    private Lock lock = new ReentrantLock();
    public Animator(Game game) { mGame = game; }
    
    private void animate()
    {
        mAnimations.removeIf((Animation animation) -> { return animation.animate(); });
    }
    public void add(Animation animation)
    {
        mAnimations.add(animation);
    }
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
    public void clear() { mAnimations.clear(); }
    public boolean canPlay() { return mAnimations.size() == 0; }
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

    public boolean hasFinished()
    {
        return mAnimations.size() == 0;
    }
}
