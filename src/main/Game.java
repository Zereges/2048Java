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
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import anims.Merge;
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
    private int mScore = 0;
    private boolean mWon = false;
    private long mStartTime;

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
    
        for (int i = 0; i < Definitions.DEFAULT_START_BLOCKS; ++i)
            randomBlock();    
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
        if (!canPlay())
            return;
        
        boolean played = false;
        switch (direction)
        {
            case LEFT:
                for (int y = 0; y < Definitions.BLOCK_COUNT_Y; ++y)
                {
                    for (int x = 1; x < Definitions.BLOCK_COUNT_X; ++x)
                    {
                        if (mRects[x][y] == null)
                            continue;
                        int i = x;
                        while (i > 0 && mRects[--i][y] == null); // find closest block
                        if (canMerge(mRects[x][y], mRects[i][y]))
                        {
                            mergeTo(x, y, i, y);
                            played = true;
                        }
                        else if (mRects[i][y] == null || mRects[++i][y] == null)
                        {
                            moveTo(x, y, i, y);
                            played = true;
                        }
                    }
                }
                break;
            case RIGHT:
                for (int y = 0; y < Definitions.BLOCK_COUNT_Y; ++y)
                {
                    for (int x = Definitions.BLOCK_COUNT_X - 2; x >= 0; --x)
                    {
                        if (mRects[x][y] == null)
                            continue;
                        int i = x;
                        while (i < Definitions.BLOCK_COUNT_X - 1 && mRects[++i][y] == null);
                        if (canMerge(mRects[x][y], mRects[i][y]))
                        {
                            mergeTo(x, y, i, y);
                            played = true;
                        }
                        else if (mRects[i][y] == null || mRects[--i][y] == null)
                        {
                            moveTo(x, y, i, y);
                            played = true;
                        }
                    }
                }
                break;
            case UP:
                for (int x = 0; x < Definitions.BLOCK_COUNT_X; ++x)
                {
                    for (int y = 1; y < Definitions.BLOCK_COUNT_Y; ++y)
                    {
                        if (mRects[x][y] == null)
                            continue;
                        int i = y;
                        while (i > 0 && mRects[x][--i] == null); // find closest block
                        if (canMerge(mRects[x][y], mRects[x][i]))
                        {
                            mergeTo(x, y, x, i);
                            played = true;
                        }
                        else if (mRects[x][i] == null || mRects[x][++i] == null)
                        {
                            moveTo(x, y, x, i);
                            played = true;
                        }
                    }
                }
                break;
            case DOWN:
                for (int x = 0; x < Definitions.BLOCK_COUNT_X; ++x)
                {
                    for (int y = Definitions.BLOCK_COUNT_Y - 2; y >= 0; --y)
                    {
                        if (mRects[x][y] == null)
                            continue;
                        int i = y;
                        while (i < Definitions.BLOCK_COUNT_Y - 1 && mRects[x][++i] == null);
                        if (canMerge(mRects[x][y], mRects[x][i]))
                        {
                            mergeTo(x, y, x, i);
                            played = true;
                        }
                        else if (mRects[x][i] == null || mRects[x][--i] == null)
                        {
                            moveTo(x, y, x, i);
                            played = true;
                        }
                    }
                }
                break;
        }

        mAnimator.startAnimation();
        onTurnEnd(played);
    }
    
    private void onTurnEnd(boolean played)
    {
        if (played)
        {
            randomBlock();
        }
        
        if (isGameOver())
            gameOver();
    }

    private void gameOver()
    {
        if (mWon)
            return;
        mCanplay = false;
        mWindow.showWarning("Game Over!", "Press Restart to play again.");
    }

    private void mergeTo(int fromX, int fromY, int toX, int toY)
    {
        mRects[fromX][fromY] = null;
        mRects[toX][toY].nextNumber();
    }

    public boolean canMerge(NumberedRect r1, NumberedRect r2)
    {
        return r1 != null && r2 != null && r1.getNumber() == r2.getNumber();
    }

    private boolean canPlay()
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
        
        mAnimator.addAndStart(new Spawn(mRects[x][y], Rect.getBlockCoords(x, y)));
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
    
    public void removeBlock(int x, int y)
    {
        mRects[x][y] = null;
    }
    
    public void restart()
    {
        mAnimator.clear();
        mRects = new NumberedRect[Definitions.BLOCK_COUNT_X][Definitions.BLOCK_COUNT_Y];
        
        for (int i = 0; i < Definitions.DEFAULT_START_BLOCKS; ++i)
            randomBlock();
        mCanplay = true;
        mWon = false;
        mStartTime = System.currentTimeMillis() / 1000;
        mScore = 0;
        mWindow.setScore(mScore + "");        
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

    public void addScore(int number)
    {
        mScore += number;
        mWindow.setScore(mScore + (mWon ? " (W)" : ""));
    }
    
    public boolean isGameOver()
    {
        for (int x = 0; x < Definitions.BLOCK_COUNT_X; ++x)
            for (int y = 0; y < Definitions.BLOCK_COUNT_Y; ++y)
                if (mRects[x][y] == null)
                    return false;

        // Board is full, looking for merge.
        for (int x = 0; x < Definitions.BLOCK_COUNT_X - 1; ++x)
            for (int y = 0; y < Definitions.BLOCK_COUNT_Y - 1; ++y)
            {
                if (canMerge(mRects[x][y], mRects[x][y + 1]))
                    return false;
                if (canMerge(mRects[x][y], mRects[x + 1][y]))
                    return false;
            }
        for (int x = 0; x < Definitions.BLOCK_COUNT_X - 1; ++x)
            if (canMerge(mRects[x][Definitions.BLOCK_COUNT_Y - 1], mRects[x + 1][Definitions.BLOCK_COUNT_Y - 1]))
                return false;
        for (int y = 0; y < Definitions.BLOCK_COUNT_Y - 1; ++y)
            if (canMerge(mRects[Definitions.BLOCK_COUNT_X - 1][y], mRects[Definitions.BLOCK_COUNT_X - 1][y + 1]))
                return false;
        
        return true;
        
    }
}
