package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import anims.Movement;
import anims.Spawn;
import windows.GameWindow;
import defs.Blocks;
import defs.Definitions;
import defs.Direction;
import defs.NumberedRect;
import defs.Player;
import defs.Rect;

public class Game extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    private Player mPlayer;
    private GameWindow mWindow;
    private List<Rect> mBackground = new ArrayList<>();
    private NumberedRect[][] mRects = new NumberedRect[Definitions.BLOCK_COUNT_X][Definitions.BLOCK_COUNT_Y];
    private Animator mAnimator = new Animator(this);
    private boolean mCanplay = true;
    private Random mRandom = new Random();


    private class MyDispatcher implements KeyEventDispatcher
    {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e)
        {
            if (e.getID() == KeyEvent.KEY_PRESSED)
                keyPressed(e.getKeyCode());
            return false;
        }
    }
    
    public Game(Player player, GameWindow window)
    {
        super(true); // double buffering.
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new MyDispatcher());
        mPlayer = player;
        mWindow = window;
        
        mBackground.add(new Rect(new Point(0, 0), Definitions.BACKGROUND_COLOR, Definitions.getMinDimension().width, Definitions.getMinDimension().height));
        for (int x = 0; x < Definitions.BLOCK_COUNT_X; ++x)
            for (int y = 0; y < Definitions.BLOCK_COUNT_Y; ++y)
            {
                mBackground.add(new Rect(
                    new Point(Definitions.MIN_BLOCK_SPACE + x * (Definitions.MIN_BLOCK_SIZE + Definitions.MIN_BLOCK_SPACE),
                              Definitions.MIN_BLOCK_SPACE + y * (Definitions.MIN_BLOCK_SIZE + Definitions.MIN_BLOCK_SPACE)),
                    Definitions.getBlockColor(Blocks.BLOCK_0),
                    Definitions.MIN_BLOCK_SIZE, 
                    Definitions.MIN_BLOCK_SIZE)
                );
            }
    }
    
    public void keyPressed(int keyCode)
    {
        switch (keyCode)
        {
            case KeyEvent.VK_LEFT:
                play(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                play(Direction.RIGHT);
                break;
            case KeyEvent.VK_UP:
                play(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                play(Direction.DOWN);
                break;
    
            case KeyEvent.VK_R:
                restart();
                break;
    
            // TBD
            case KeyEvent.VK_B:
                randomBlock();
        }
    }
    
    public void play(Direction direction)
    {
        if (!can_play())
            return;
        
        boolean played;
        // case left
        for (int y = 0; y < Definitions.BLOCK_COUNT_Y; ++y)
        {
            for (int x = 1; x < Definitions.BLOCK_COUNT_X; ++x)
            {
                if (mRects[x][y] == null)
                    continue;
                int i = x;
                while (i > 0 && mRects[--i][y] == null); // find closest block
                if (mRects[i][y] == null || mRects[++i][y] == null)
                {
                    moveTo(x, y, i, y);
                    played = true;
                }
            }
        }
        mAnimator.startAnimation();
    }
    
    private boolean can_play()
    {
        return mAnimator.canPlay() && mCanplay;
    }

    private void moveTo(int fromX, int fromY, int toX, int toY)
    {
        mAnimator.add(new Movement(mRects[fromX][fromY], Rect.getBlockCoords(toX, toY)));
        mRects[toX][toY] = mRects[fromX][fromY];
        mRects[fromX][fromY] = null;
    }

    private boolean spawnBlock(int block, int x, int y)
    {
        if (mRects[x][y] != null)
            return false;
        mRects[x][y] = new NumberedRect(Rect.getBlockCoords(x, y), block, 0, 0, 0);
        mAnimator.add(new Spawn(mRects[x][y], Rect.getBlockCoords(x, y)));
        mAnimator.startAnimation();
        return true;
    }
    private boolean spawnBlock(Blocks block, int x, int y) { return spawnBlock(block.getValue(), x, y); }

    private boolean randomBlock(int block)
    {
        List<Integer> poss = new ArrayList<>();
        for (int i = 0; i < Definitions.BLOCK_COUNT_X * Definitions.BLOCK_COUNT_Y; ++i)
            if (mRects[i / Definitions.BLOCK_COUNT_X][i % Definitions.BLOCK_COUNT_Y] == null)
                poss.add(i);
        if (poss.size() == 0)
            return false;

        int pos = poss.get(mRandom.nextInt(poss.size()));
        return spawnBlock(block, pos / Definitions.BLOCK_COUNT_X, pos % Definitions.BLOCK_COUNT_Y);
    }
    private boolean randomBlock(Blocks block) { return randomBlock(block.getValue()); }
    private boolean randomBlock()
    {
        return randomBlock(mRandom.nextInt(100) < Definitions.BLOCK_4_SPAWN_CHANCE ? Blocks.BLOCK_4 : Blocks.BLOCK_2);
    }
    
    
    public void restart()
    {

    }
    
    @Override
    public void paintComponent(Graphics graphics)
    {
        drawBackground(graphics);
        drawRects(graphics);
    }
    
    private void drawBackground(Graphics graphics)
    {
        for (Rect r : mBackground)
            r.draw(graphics);
    }
    private void drawRects(Graphics graphics)
    {
        for (NumberedRect[] rects : mRects)
            for (NumberedRect rect : rects)
                if (rect != null)
                    rect.draw(graphics);
    }
}
