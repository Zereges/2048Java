package defs;

import java.awt.Point;

public class NumberedRect extends Rect
{
    private int mNumber;
    
    public NumberedRect(Point point, int number)
    {
        super(point, Definitions.getBlockColor(number));
        mNumber = number;
    }
}
