package defs;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

/**
 * Represents single block on the game field.
 * @see Blocks
 * @see Rect
 */
public class NumberedRect extends Rect
{
    /**
     * Inner number representing value of rect.
     * This holds: {@code log_2(mShownNumber) == mNumber} when {@code NumberedRect} is steady
     */
    protected int mNumber;
    
    /**
     * Real shown number of the NumberedRect. This holds: {@code log_2(mShownNumber) == mNumber}.
     * This holds: {@code log_2(mShownNumber) == mNumber} when {@code NumberedRect} is steady
     */
    protected int mShownNumber;
    
    /** Current font size of shown number. */
    protected int mFontSize = Definitions.getDefaultBlockFontSize(mNumber + "");
    
    /**
     * Constructs {@code NumberedRect} with given arguments.
     * @param point {@code Point} containing x,y-coords on the game field.
     * @param number {@code mNumber} for constructing {@code NumberedRect}.
     */
    public NumberedRect(Point point, int number)
    {
        super(point, Definitions.getBlockColor(number));
        mNumber = number;
        mShownNumber = (int) Math.pow(2, mNumber);
    }

    /**
     * Constructs {@code NumberedRect} with given arguments.
     * @param point {@code Point} containing x,y-coords on the game field.
     * @param number {@code mNumber} for constructing {@code NumberedRect}.  
     * @param width Width of the {@code NumberedRect}.
     * @param height Height of the {@code NumberedRect}.
     * @param fontSize Size of the font used for showing number in {@code NumberedRect}.
     */
    public NumberedRect(Point point, int number, int width, int height, int fontSize)
    {
        super(point, Definitions.getBlockColor(number), width, height);
        mNumber = number;
        mShownNumber = (int) Math.pow(2, mNumber);
        mFontSize = fontSize;
    }
    
    /**
     * Gets inner number of the {@code NumberedRect}.
     * @return Inner number of the {@code NumberedRect}.
     */
    public int getNumber() { return mNumber; }
    
    /**
     * Returns real shown number of the {@code NumberedRect} on the game field.
     * @return Real shown number of the {@code NumberedRect}.
     */
    public int getShownNumber() { return mShownNumber; }
      
    /**
     * Gets default font size of current {@code NumberedRect}. 
     * @return Default font size for given {@code NumberedRect}.
     */
    public int getFontSize() { return Definitions.getDefaultBlockFontSize("" + mNumber); }
    
    /**
     * Gets current font size of the {@code NumberedRect}.
     * @return Current font size of the {@code NumberedRect}.
     */
    public int getCurrentFontSize() { return mFontSize; }
    
    /**
     * Sets current font size to {@code fontSize}.
     * @param fontSize to set current font size to.
     */
    public void setCurrentFontSize(int fontSize) { mFontSize = fontSize; }

    /**
     * Increments inner number of {@code NumberedRect}.
     * @param withUpdate If {@code withUpdate} is true, it will update real shown number.
     */
    public void nextNumber(boolean withUpdate)
    {
        ++mNumber;
        if (withUpdate)
            updateShownNumber();
    }

    /**
     * Updates color and shown number of the {@code NumberedRect}.
     */
    public void updateShownNumber()
    {
        mColor = Definitions.getBlockColor(mNumber);
        mShownNumber = (int) Math.pow(2, mNumber);
    }

    /**
     * Method which is called while {@code NumberedRect} is painted on the game field.
     * @param graphics {@code Graphics} context to draw on.
     * @see main.Game#paintComponent(Graphics)
     */
    @Override
    public void draw(Graphics graphics)
    {
        super.draw(graphics);
        graphics.setColor(Definitions.DEFAULT_BLOCK_FONT_COLOR);
        String text = "" + mShownNumber;
        graphics.setFont(graphics.getFont().deriveFont(0, mFontSize));

        // centering the text
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(text, graphics);
        int x = mPoint.x + (mWidth - (int) rect.getWidth()) / 2;
        int y = mPoint.y + (mHeight - (int) rect.getHeight()) / 2 + fontMetrics.getAscent();
        graphics.drawString(text, x, y);
    }
}    
