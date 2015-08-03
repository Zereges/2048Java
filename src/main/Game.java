package main;

import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JPanel;
import anims.Merge;
import anims.Movement;
import anims.Spawn;
import windows.GameWindow;
import defs.Blocks;
import defs.Definitions;
import defs.Direction;
import defs.NumberedRect;
import defs.Rect;

/**
 * Represents game field.
 */
public class Game extends JPanel
{
    /** Auto-generated for {@code Serializable} interface. */
    private static final long serialVersionUID = 1L;
    
    /** Player profile. */
    private Player mPlayer;
    
    /** Reference to {@link windows.GameWindow Game Window}. */
    private GameWindow mWindow;
    
    /** List of {@link defs.Rect Rects} representing background of the field. */
    private List<Rect> mBackground = new ArrayList<>();
    
    /** {@link Definitions#BLOCK_COUNT_X} times {@link Definitions#BLOCK_COUNT_Y} board representing current game state. */
    private NumberedRect[][] mRects = new NumberedRect[Definitions.BLOCK_COUNT_X][Definitions.BLOCK_COUNT_Y];
    
    /** List of currently animated {@link defs.Rect Rects}. */
    private List<NumberedRect> mAnimatedRects = new ArrayList<>();
    
    /** Animator. */
    private Animator mAnimator;
    
    /** Lock used to synchronize access to {@link mAnimatedRects}. */
    private Lock mLock = new ReentrantLock();
    
    /** Represents whether player is allowed to perform next turn. */
    private boolean mCanplay = true;
    
    /** Random class used for spawning blocks on random position. */
    private Random mRandom = new Random();
    
    /** Current game score. */
    private int mScore = 0;
    
    /** Indicates whether player managed to win current game. */
    private boolean mWon = false;
    
    /** Time, when the game started. */
    private long mStartTime = System.currentTimeMillis();
    
    /**
     * Constructs game field.
     * @param player Player which is playing the game.
     * @param window Reference to {@link windows.GameWindow} on which Game will be shown.
     */
    public Game(Player player, GameWindow window)
    {
        super(true); // double buffering.
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher()
        {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e)
            {
                if (e.getID() == KeyEvent.KEY_PRESSED)
                    keyPressed(e.getKeyCode());
                return false;
            }            
        });
        mPlayer = player;
        mWindow = window;
        mAnimator = new Animator(this, mLock);
        
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
    
    /** 
     * Handler for capturing user keyboard presses.
     * @param keyCode Keycode refering to key pressed.
     */
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
        }
    }
    
    /**
     * Performs one turn in given direction.
     * @param direction {@link defs.Direction} player choosed to play. 
     */
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
        if (played)
            mPlayer.getStats().play(direction);

        onTurnEnd(played);
    }
        
    /** Is called when player makes a winning turn. */
    public void won()
    {
        mWon = true;
        mWindow.getScoreLabel().setWon(true);
        mPlayer.getStats().win();
    }
    
    /**
     * Removes {@link defs.NumberedRect} from the game field.
     * @param rect {@link defs.NumberedRect} to remove from the game field.
     */
    public void removeBlock(NumberedRect rect)
    {
        for (int x = 0; x < Definitions.BLOCK_COUNT_X; ++x)
            for (int y = 0; y < Definitions.BLOCK_COUNT_Y; ++y)
                if (mRects[x][y] == rect)
                {
                    mRects[x][y] = null;
                    return;
                }
    }
    
    /**
     * Restarts current game. This preserves current stats.
     * @see Stats
     */
    public void restart()
    {
        mAnimator.clear();
        mRects = new NumberedRect[Definitions.BLOCK_COUNT_X][Definitions.BLOCK_COUNT_Y];
        
        for (int i = 0; i < Definitions.DEFAULT_START_BLOCKS; ++i)
            randomBlock();
        mCanplay = true;
        mStartTime = System.currentTimeMillis();
        mWon = false;
        mScore = 0;
        mWindow.getScoreLabel().setScore(mScore);
        mWindow.getScoreLabel().setWon(false);
        mPlayer.getStats().restart();
    }

    /**
     * Incremens the score based on merged blocks.
     * @param number Score to add to current score.
     */
    public void addScore(int number)
    {
        mScore += number;
        mWindow.getScoreLabel().addScore(number);
    }
    
    /**
     * Checks whether game over has happened.
     * @return True if game is in game over state, false otherwise.
     */
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
    
    /**
     * Updates time statistics in {@code mStats}.
     * @see Stats
     */
    public void updateStatsTime() { mPlayer.getStats().updateTime(mStartTime); }

    /**
     * Checks whether given block is in the game field.
     * @param block {@link defs.Blocks Block} to look for.
     * @return True if given block was found, false otherwise.
     * @see #hasBlock(int)
     */
    public boolean hasBlock(Blocks block) { return hasBlock(block.getId()); }
    
    /**
     * Checks whether given block is in the game field.
     * @param block Value representing {@link defs.NumberedRect}.
     * @return True if given block was found, false otherwise.
     * @see #hasBlock(Blocks)
     */
    public boolean hasBlock(int block)
    {
        for (int x = 0; x < Definitions.BLOCK_COUNT_X; ++x)
            for (int y = 0; y < Definitions.BLOCK_COUNT_Y; ++y)
                if (mRects[x][y].getNumber() == block)
                    return true;
        return false;
    }
    
    /**
     * Is called when {@link Animator} invalidates this component.
     * @param graphics {@code Graphics} context.
     * @see Animator
     */
    @Override
    public void paintComponent(Graphics graphics)
    {
        drawBackground(graphics);
        drawRects(graphics);
    }
    
    /**
     * Is called after {@link #play(Direction)}.
     * @param played {@link Direction} the player choosed to play.
     */
    private void onTurnEnd(boolean played)
    {
        if (played)
        {
            randomBlock();
        }
        
        if (isGameOver())
            gameOver();
    }

    /** Is called when player makes a turn, which led to losing the game. */
    private void gameOver()
    {
        if (mWon)
            return;
        mCanplay = false;
        mPlayer.getStats().lose();
        mPlayer.getAchievements().lose(mStartTime, this);

        mWindow.showWarning("Game Over!", "Press Restart to play again.");
    }

    /**
     * Performs merging action of objects on the game field.
     * This also informs {@code mStats} of the call.
     * This also informs {@code mAchievements} of the call.
     * This also adds merging animation for {@link Animator} class.
     * @param fromX Game X coord of merging source.
     * @param fromY Game Y coord of merging source.
     * @param toX Game X coord of merging target.
     * @param toY Game Y coord of merging target.
     */
    private void mergeTo(int fromX, int fromY, int toX, int toY)
    {
        mAnimatedRects.add(mRects[fromX][fromY]);
        mAnimator.add(new Merge(mRects[fromX][fromY], mRects[toX][toY], mAnimatedRects));
        mRects[fromX][fromY] = null;
        mRects[toX][toY].nextNumber(false);

        int number = 2 * mRects[toX][toY].getShownNumber();
        addScore(number);

        mPlayer.getStats().merge();
        mPlayer.getStats().score(number);
        mPlayer.getStats().highestScore(mScore);
        mPlayer.getStats().maximalBlock(number);

        mPlayer.getAchievements().merge(number);
        
        if (!mWon && number == Definitions.GAME_WIN_NUMBER)
        {
            won();
            mPlayer.getAchievements().won(mStartTime, this);
        }
    }

    /**
     * Checks whether two {@link NumberedRect NumberedRects} can be merged together.
     * @param r1 First {@link NumberedRect}.
     * @param r2 Second {@link NumberedRect}.
     * @return True if can be merged, false otherwise.
     */
    private boolean canMerge(NumberedRect r1, NumberedRect r2)
    {
        return r1 != null && r2 != null && r1.getNumber() == r2.getNumber();
    }

    /**
     * Checks whether player may perform a turn.
     * @return True if can, false otherwise.
     */
    private boolean canPlay() { return mAnimator.hasFinished() && mCanplay; }

    /**
     * Performs moving action of objects on the game field.
     * This also informs {@code mStats} of the call.
     * This also adds moving animation for {@link Animator} class.
     * @param fromX Game X coord of moving source.
     * @param fromY Game Y coord of moving source.
     * @param toX Game X coord of moving target.
     * @param toY Game Y coord of moving target.
     */
    private void moveTo(int fromX, int fromY, int toX, int toY)
    {
        mAnimator.add(new Movement(mRects[fromX][fromY], Rect.getBlockCoords(toX, toY)));
        mRects[toX][toY] = mRects[fromX][fromY];
        mRects[fromX][fromY] = null;
        
        mPlayer.getStats().move();
    }

    /**
     * Spawns given block on given coords on the field and returns success state.
     * Spawn may be unsuccessful when target coord is not empty.
     * This also adds spawning animation for {@link Animator} class.
     * @param block Value representing the block to spawn.
     * @param x Game X coord to spawn the block on.
     * @param y Game Y coord to spawn the block on.
     * @return True if spawn was successful, false otherwise.
     * @see #spawnBlock(Blocks, int, int)
     */
    private boolean spawnBlock(int block, int x, int y)
    {
        if (mRects[x][y] != null)
            return false;
        mRects[x][y] = new NumberedRect(Rect.getBlockCoords(x, y), block, 0, 0, 0);
        mAnimator.addAndStart(new Spawn(mRects[x][y], Rect.getBlockCoords(x, y)));
        return true;
    }
    
    /**
     * Spawns given block on given coords on the field and returns success state.
     * Spawn may be unsuccessful when target coord is not empty.
     * This also adds spawning animation for {@link Animator} class.
     * @param block {@link defs.Blocks Block} to spawn on given coords.
     * @param x Game X coord to spawn the block on.
     * @param y Game Y coord to spawn the block on.
     * @return True if spawn was successful, false otherwise.
     * @see #spawnBlock(int, int, int)
     */
    @SuppressWarnings("unused")
    private boolean spawnBlock(Blocks block, int x, int y) { return spawnBlock(block.getId(), x, y); }

    /**
     * Spawns given block on random coords returns success state.
     * Spawn may be unsuccessful if the game field is full.
     * This also adds spawning animation for {@link Animator} class.
     * @param block Value representing the block to spawn.
     * @return True if spawn was successful, false otherwise.
     * @see #randomBlock(Blocks)
     * @see #randomBlock()
     */
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
    
    /**
     * Spawns given block on random coords returns success state.
     * Spawn may be unsuccessful if the game field is full.
     * This also adds spawning animation for {@link Animator} class.
     * @param block {@link defs.Blocks Block} to spawn.
     * @return True if spawn was successful, false otherwise.
     * @see #randomBlock(Blocks)
     * @see #randomBlock()
     */
    private boolean randomBlock(Blocks block) { return randomBlock(block.getId()); }
    
    /**
     * Spawns {@link defs.Blocks#BLOCK_2} (with {@link defs.Definitions#BLOCK_4_SPAWN_CHANCE} {@link defs.Blocks#BLOCK_4}
     * on the random coords on the game field and returns success state. 
     * Spawn may be unsuccessful if the game field is full.
     * This also adds spawning animation for {@link Animator} class.
     * @return True if spawn was successful, false otherwise.
     */
    private boolean randomBlock()
    {
        return randomBlock(mRandom.nextInt(100) < Definitions.BLOCK_4_SPAWN_CHANCE ? Blocks.BLOCK_4 : Blocks.BLOCK_2);
    }

    /**
     * Draws background of the game field.
     * @param graphics {@code Graphics} context.
     */
    private void drawBackground(Graphics graphics)
    {
        for (Rect r : mBackground)
            r.draw(graphics);
    }
    
    /**
     * Draws {@link defs.NumberedRect NumberedRects} on the game field.
     * @param graphics {@code Graphics} context.
     */
    private void drawRects(Graphics graphics)
    {
        for (NumberedRect[] rects : mRects)
            for (NumberedRect rect : rects)
                if (rect != null)
                    rect.draw(graphics);
        mLock.lock();
        for (NumberedRect rect : mAnimatedRects)
            rect.draw(graphics);
        mLock.unlock();
    }
}
