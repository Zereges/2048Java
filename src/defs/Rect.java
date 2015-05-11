package defs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Rect
{
    protected Point mPoint;
    protected int mWidth = Definitions.MIN_BLOCK_SIZE;
    protected int mHeight = Definitions.MIN_BLOCK_SIZE;
    protected Color mColor;
    
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
    public int getX() { return mPoint.x; }
    public int getY() { return mPoint.y; }
    public Point getPoint() { return mPoint; }
}
