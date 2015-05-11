package main;

import java.util.ArrayList;
import java.util.List;

import anims.Animation;
import defs.Definitions;

public class Animator
{
    private List<Animation> mAnimations = new ArrayList<>();
    private Game mGame;
    
    public Animator(Game game) { mGame = game; }
    
    private void animate()
    {
        mAnimations.removeIf((Animation animation) -> { return animation.animate(); });
    }
    public void add(Animation animation) { mAnimations.add(animation); }
    public void clear() { mAnimations.clear(); }
    public boolean canPlay() { return mAnimations.size() == 0; }
    public void startAnimation()
    {
        Thread t2 = new Thread()
        {
            @Override
            public void run()
            {
                long nextTick = System.currentTimeMillis();
                long sleepTime;
                
                while (mAnimations.size() != 0)
                {
                    animate();
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
                }
            }
        };
        t2.start();
    }
}
