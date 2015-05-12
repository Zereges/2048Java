package defs;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class NumberedRect extends Rect
{
    protected int mNumber;
    protected int mShownNumber;
    protected int mFontSize = Definitions.getDefaultBlockFontSize("" + mNumber);
    
    public NumberedRect(Point point, int number)
    {
        super(point, Definitions.getBlockColor(number));
        mNumber = number;
        mShownNumber = (int) Math.pow(2, mNumber);
    }

    public NumberedRect(Point point, int number, int width, int height, int fontSize)
    {
        super(point, Definitions.getBlockColor(number), width, height);
        mNumber = number;
        mShownNumber = (int) Math.pow(2, mNumber);
        int mFontSize = fontSize;
    }
    
    public int getNumber()
    {
        return mNumber;
    }
    
    public int getShownNumber()
    {
        return mShownNumber;
    }
    
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
    
    public int getFontSize() { return Definitions.getDefaultBlockFontSize("" + mNumber); }
    public int getCurrentFontSize() { return mFontSize; }
    public void setCurrentFontSize(int fontSize) { mFontSize = fontSize; }

    public void nextNumber(boolean withUpdate)
    {
        ++mNumber;
        if (withUpdate)
            updateShownNumber();
    }
    public void updateShownNumber()
    {
        mColor = Definitions.getBlockColor(mNumber);
        mShownNumber = (int) Math.pow(2, mNumber);
    }
}    
