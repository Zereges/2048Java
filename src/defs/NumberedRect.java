package defs;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class NumberedRect extends Rect
{
    protected int mNumber;
    
    public NumberedRect(Point point, int number)
    {
        super(point, Definitions.getBlockColor(number));
        mNumber = number;
    }
    
    public int getNumber()
    {
        return (int) Math.pow(2, mNumber);
    }
    
    @Override
    public void draw(Graphics graphics)
    {
        super.draw(graphics);
        String text = "" + getNumber();
        graphics.setFont(graphics.getFont().deriveFont(0, Definitions.MIN_BLOCK_SIZE / (text.length())));
        graphics.setColor(Definitions.DEFAULT_BLOCK_FONT_COLOR);

        // centering the text
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(text, graphics);
        int x = mPoint.x + (mWidth - (int) rect.getWidth()) / 2;
        int y = mPoint.y + (mHeight - (int) rect.getHeight()) / 2 + fontMetrics.getAscent();
        graphics.drawString(text, x, y);
    }
}
