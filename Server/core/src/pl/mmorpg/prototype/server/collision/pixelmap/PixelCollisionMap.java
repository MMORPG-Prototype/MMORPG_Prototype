package pl.mmorpg.prototype.server.collision.pixelmap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.server.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.server.collision.interfaces.RectangleCollisionObject;
import pl.mmorpg.prototype.server.resources.Assets;

public class PixelCollisionMap<T extends RectangleCollisionObject> implements CollisionMap<T>
{
    private Object[][] collisionMap;
    private int scale;
    
    public PixelCollisionMap(int width, int height)
    {
        this(width, height, 1);
    }

    public PixelCollisionMap(int width, int height, int scale)
    {
        this.scale = scale;
        collisionMap = createCollisionMap(width, height);
    }
    
    public PixelCollisionMap(int width, int height, int scale, T borderObject)
    {
        this(width, height, scale);
        placeObjectOnBorder(borderObject);
    }
    
    public PixelCollisionMap(int width, int height, T borderObject)
    {
        this(width, height);
        placeObjectOnBorder(borderObject);
    }

    private Object[][] createCollisionMap(int width, int height)
    {
        Object[][] collisionMap = new Object[width][];
        for (int i = 0; i < width; i++)
        {
            collisionMap[i] = new Object[height];
            for (int j = 0; j < height; j++)
                collisionMap[i][j] = null;
        }
        return collisionMap;
    }

    public void placeObjectOnBorder(T object)
    {
        for (int i = 0; i < collisionMap.length; i++)
        {
            collisionMap[i][0] = object;
            collisionMap[i][collisionMap[0].length - 1] = object;
        }

        for (int j = 0; j < collisionMap[0].length; j++)
        {
            collisionMap[0][j] = object;
            collisionMap[collisionMap.length - 1][j] = object;
        }
    }

    public void setScale(int scale)
    {
        this.scale = scale;
    }

    public int getScale()
    {
        return scale;
    }

    @Override
    public void insert(T object)
    {
        IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect(), scale);

        for (int i = collision.x; i <= collision.x + collision.width; i++)
            for (int j = collision.y; j <= collision.y + collision.height; j++)
                collisionMap[i][j] = object;
    }

    @Override
    public void remove(T object)
    {
        IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect(), scale);

        for (int i = collision.x; i <= collision.x + collision.width; i++)
            for (int j = collision.y; j <= collision.y + collision.height; j++)
                collisionMap[i][j] = null;
    }

    @Override
    public T tryToRepositionGoingLeft(int moveValue, T object)
    {
        T possibleCollision = checkForSpaceGoingLeft(moveValue,
                new IntegerRectangle(object.getCollisionRect(), scale));
        if (possibleCollision == null)
            repositionCollisionGoingLeft(moveValue, object);
        return possibleCollision;
    }

    @Override
    public T tryToRepositionGoingRight(int moveValue, T object)
    {
        T possibleCollision = checkForSpaceGoingRight(moveValue,
                new IntegerRectangle(object.getCollisionRect(), scale));
        if (possibleCollision == null)
            repositionCollisionGoingRight(moveValue, object);
        return possibleCollision;
    }

    @Override
    public T tryToRepositionGoingDown(int moveValue, T object)
    {
        T possibleCollision = checkForSpaceGoingDown(moveValue,
                new IntegerRectangle(object.getCollisionRect(), scale));
        if (possibleCollision == null)
            repositionCollisionGoingDown(moveValue, object);
        return possibleCollision;
    }

    @Override
    public T tryToRepositionGoingUp(int moveValue, T object)
    {
        T possibleCollision = checkForSpaceGoingUp(moveValue,
                new IntegerRectangle(object.getCollisionRect(), scale));
        if (possibleCollision == null)
            repositionCollisionGoingUp(moveValue, object);
        return possibleCollision;
    }

    private T checkForSpaceGoingLeft(int moveValue, IntegerRectangle collision)
    {
        for (int i = collision.x - 1; i >= collision.x - moveValue; i--)
            for (int j = collision.y; j <= collision.getUpperBound(); j++)
                if (collisionMap[i][j] != null)
                    return (T)collisionMap[i][j];
        return null;
    }

    private T checkForSpaceGoingRight(int moveValue, IntegerRectangle collision)
    {
        for (int i = collision.getRightBound() + 1; i <= collision.getRightBound() + moveValue; i++)
            for (int j = collision.y; j <= collision.getUpperBound(); j++)
                if (collisionMap[i][j] != null)
                    return (T)collisionMap[i][j];
        return null;
    }

    private T checkForSpaceGoingDown(int moveValue, IntegerRectangle collision)
    {
        for (int i = collision.y - 1; i >= collision.y - moveValue; i--)
            for (int j = collision.x; j <= collision.getRightBound(); j++)
                if (collisionMap[j][i] != null)
                    return (T)collisionMap[j][i];
        return null;
    }

    private T checkForSpaceGoingUp(int moveValue, IntegerRectangle collision)
    {
        for (int i = collision.getUpperBound() + 1; i <= collision.getUpperBound() + moveValue; i++)
            for (int j = collision.x; j <= collision.getRightBound(); j++)
                if (collisionMap[j][i] != null)
                    return (T)collisionMap[j][i];
        return null;
    }

    private void repositionCollisionGoingLeft(int moveValue, T object)
    {
        IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect(), scale);

        for (int i = collision.y; i <= collision.getUpperBound(); i++)
            for (int j = collision.x - moveValue; j < collision.x; j++)
                collisionMap[j][i] = object;

        for (int i = collision.y; i <= collision.getUpperBound(); i++)
            for (int j = collision.getRightBound() - moveValue + 1; j <= collision.getRightBound(); j++)
                collisionMap[j][i] = null;
    }

    private void repositionCollisionGoingRight(int moveValue, T object)
    {
        IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect(), scale);

        for (int i = collision.y; i <= collision.getUpperBound(); i++)
            for (int j = collision.getRightBound() + 1; j <= collision.getRightBound() + moveValue; j++)
                collisionMap[j][i] = object;

        for (int i = collision.y; i <= collision.getUpperBound(); i++)
            for (int j = collision.x; j < collision.x + moveValue; j++)
                collisionMap[j][i] = null;
    }

    private void repositionCollisionGoingDown(int moveValue, T object)
    {
        IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect(), scale);

        for (int i = collision.y - 1; i >= collision.y - moveValue; i--)
            for (int j = collision.x; j <= collision.getRightBound(); j++)
                collisionMap[j][i] = object;

        for (int i = collision.y + collision.height; i >= collision.y + collision.height - moveValue + 1; i--)
            for (int j = collision.x; j <= collision.getRightBound(); j++)
                collisionMap[j][i] = null;
    }

    private void repositionCollisionGoingUp(int moveValue, T object)
    {
        IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect(), scale);

        for (int i = collision.y + collision.height + 1; i <= collision.y + collision.height + moveValue; i++)
            for (int j = collision.x; j <= collision.getRightBound(); j++)
                collisionMap[j][i] = object;

        for (int i = collision.y + moveValue - 1; i >= collision.y; i--)
            for (int j = collision.x; j <= collision.getRightBound(); j++)
                collisionMap[j][i] = null;
    }

    public void render(SpriteBatch batch)
    {
        Texture blackPixel = Assets.get("collisionMapDebug.png");
        for (int i = 0; i < collisionMap.length; i += 1)
            for (int j = 0; j < collisionMap[0].length; j += 1)
                if (collisionMap[i][j] != null)
                    batch.draw(blackPixel, i, j);
    }

    public T getTopObject(int gameX, int gameY)
    {
        if (gameX / scale >= collisionMap.length || gameY / scale >= collisionMap[0].length
                || gameX < 0 || gameY < 0)
            return null;
        return (T)collisionMap[gameX / scale][gameY / scale];
    }
}
