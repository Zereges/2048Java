package defs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Rect
{
    private Point mPoint;
    private int mWidth = Definitions.MIN_BLOCK_SIZE;
    private int mHeight = Definitions.MIN_BLOCK_SIZE;
    private Color mColor;
    
    public Rect(Point point, Color color)
    {
        mPoint = point;
        mColor = color;
    }
    public Rect(Point point, Color color, int width, int height)
    {
        this(point, color);
        mWidth = width;
        mHeight = height;
    }
    
    public void draw(Graphics graphics)
    {
        graphics.setColor(mColor);
        graphics.fillRect(mPoint.x, mPoint.y, mWidth, mHeight);
    }

    public static Point getBlockCoords(int x, int y)
    {
        return new Point(
                Definitions.MIN_BLOCK_SPACE + x * (Definitions.MIN_BLOCK_SPACE + Definitions.MIN_BLOCK_SIZE),
                Definitions.MIN_BLOCK_SPACE + y * (Definitions.MIN_BLOCK_SPACE + Definitions.MIN_BLOCK_SIZE)
                );
    }
}
