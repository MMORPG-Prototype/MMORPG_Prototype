package pl.mmorpg.prototype.server.collision;

import com.badlogic.gdx.math.Rectangle;

public class IntegerRectangle
{
    public int x;
    public int y;
    public int width;
    public int height;

    public IntegerRectangle(Rectangle rectangle)
    {
        this(rectangle, 1);
    }

    public IntegerRectangle(Rectangle rectangle, int scale)
    {
        this.x = (int) rectangle.x / scale;
        this.y = (int) rectangle.y / scale;
        this.width = (int) rectangle.width / scale;
        this.height = (int) rectangle.height / scale;
    }

    public IntegerRectangle(int x, int y, int width, int height)
    {
        this(x, y, width, height, 1);
    }

    public IntegerRectangle(int x, int y, int width, int height, int scale)
    {
        this.x = x / scale;
        this.y = y / scale;
        this.width = width / scale;
        this.height = height / scale;
    }

    public int getRightBound()
    {
        return x + width;
    }

    public int getUpperBound()
    {
        return y + height;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

}
