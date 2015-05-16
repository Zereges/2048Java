package defs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Simple Rectangular object drawable on Graphics context.
 */
public class Rect
{
    /** Coordinates on the field. */
    protected Point mPoint;
    
    /** Width of the {@code Rect}. */
    protected int mWidth = Definitions.MIN_BLOCK_SIZE;

    /** Height of the {@code Rect}. */
    protected int mHeight = Definitions.MIN_BLOCK_SIZE;
    
    /** Color of the {@code Rect}. */
    protected Color mColor;
    
    /**
     * Constructs {@code Rect} for given arguments.
     * @param point {@code Point} coordinates on the field.
     * @param color Desired color of the {@code Rect}.
     */
    public Rect(Point point, Color color)
    {
        mPoint = point;
        mColor = color;
    }
    
    /**
     * Constructs {@code Rect} for given arguments.
     * @param point {@code Point} coordinates on the field.
     * @param color Desired color of the {@code Rect}.
     * @param width Width of the {@code Rect}. 
     * @param height Height of the {@code Rect}.
     */
    public Rect(Point point, Color color, int width, int height)
    {
        this(point, color);
        mWidth = width;
        mHeight = height;
    }
    
    /**
     * Method which is called while {@code Rect} is painted on the game field.
     * @param graphics {@code Graphics} context to draw on.
     * @see main.Game#paintComponent(Graphics)
     */
    public void draw(Graphics graphics)
    {
        graphics.setColor(mColor);
        graphics.fillRect(mPoint.x, mPoint.y, mWidth, mHeight);
    }

    /**
     * Converts game field to real x,y-coords. 
     * @param x x coord such that {@code 0 <= x < }{@link Definitions#BLOCK_COUNT_X}
     * @param y y coord such that {@code 0 <= y < }{@link Definitions#BLOCK_COUNT_Y}
     * @return Real game field coordinates in {@code Point}.
     */
    public static Point getBlockCoords(int x, int y)
    {
        return new Point(
                Definitions.MIN_BLOCK_SPACE + x * (Definitions.MIN_BLOCK_SPACE + Definitions.MIN_BLOCK_SIZE),
                Definitions.MIN_BLOCK_SPACE + y * (Definitions.MIN_BLOCK_SPACE + Definitions.MIN_BLOCK_SIZE)
                );
    }
    
    /**
     * Getter for x coordinate of the {@code Rect}.
     * @return X coordinate of the {@code Rect}.
     */
    public int getX() { return mPoint.x; }
    
    /**
     * Setter for x coordinate of the {@code Rect}.
     * @param x X coordinate to set.
     */
    public void setX(int x) { mPoint.x = x; } 

    /**
     * Getter for y coordinate of the {@code Rect}.
     * @return Y coordinate of the {@code Rect}.
     */
    public int getY() { return mPoint.y; }

    /**
     * Setter for y coordinate of the {@code Rect}.
     * @param y Y coordinate to set.
     */
    public void setY(int y) { mPoint.y = y; } 
    
    /**
     * Getter for width of the {@code Rect}.
     * @return Width of the {@code Rect}.
     */
    public int getWidth() { return mWidth; }

    /**
     * Setter for width of the {@code Rect}.
     * @param width Width to set.
     */
    public void setWidth(int width) { mWidth = width; } 

    /**
     * Getter for height of the {@code Rect}.
     * @return Height of the {@code Rect}.
     */
    public int getHeight() { return mHeight; }

    /**
     * Setter for height of the {@code Rect}.
     * @param height Height to set.
     */
    public void setHeight(int height) { mHeight = height; }
    
    /**
     * Getter for {@code mPoint}.
     * @return Point representing {@code Rect} coords.
     */
    public Point getPoint() { return mPoint; }
}
