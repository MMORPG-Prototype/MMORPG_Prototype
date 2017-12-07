package pl.mmorpg.prototype.client.collision.pixelmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.exceptions.NotImplementedException;
import pl.mmorpg.prototype.client.objects.GameObject;

@SuppressWarnings("unchecked")
public class PixelCollisionMap<T extends GameObject> implements CollisionMap<T>
{
    private Object[][] collisionMap;
    private Map<Long, T> insertedCollisionObjects = new ConcurrentHashMap<>();
    private int shiftX = 0;
    private int shiftY = 0;
    
    public PixelCollisionMap(int width, int height)
    {
        collisionMap = createCollisionMap(width, height);
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
    
    @Override
    public void insert(T object)
    {
        IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());
    	if(!objectFitsInMap(collision))
    		return;
    	insertedCollisionObjects.put(object.getId(), object);

        for (int i = collision.x; i <= collision.x + collision.width; i++)
            for (int j = collision.y; j <= collision.y + collision.height; j++)
                collisionMap[i][j] = object;
    }

    private boolean objectFitsInMap(IntegerRectangle collision)
	{
		return isValidPoint(collision.getX(), collision.getY()) && isValidPoint(collision.getRightBound(), collision.getUpperBound());
	}
    
    private boolean isValidPoint(int x, int y)
    {
    	return x >= 0 && x < collisionMap.length && y >= 0 && y <= collisionMap[0].length;
    }

	@Override
    public void remove(T object)
    {
        IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());

        for (int i = collision.x; i <= collision.x + collision.width; i++)
            for (int j = collision.y; j <= collision.y + collision.height; j++)
                collisionMap[i][j] = null;
        insertedCollisionObjects.remove(object.getId());
    }

    @Override
    public void repositionGoingLeft(int moveValue, T object)
    {
        IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());

        for (int i = collision.y; i <= collision.getUpperBound(); i++)
            for (int j = collision.x - moveValue; j < collision.x; j++)
                collisionMap[j][i] = object;

        for (int i = collision.y; i <= collision.getUpperBound(); i++)
            for (int j = collision.getRightBound() - moveValue + 1; j <= collision.getRightBound(); j++)
                collisionMap[j][i] = null;
    }

    @Override
    public void repositionGoingRight(int moveValue, T object)
    {
    	IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());

        for (int i = collision.y; i <= collision.getUpperBound(); i++)
            for (int j = collision.getRightBound() + 1; j <= collision.getRightBound() + moveValue; j++)
                collisionMap[j][i] = object;

        for (int i = collision.y; i <= collision.getUpperBound(); i++)
            for (int j = collision.x; j < collision.x + moveValue; j++)
                collisionMap[j][i] = null;
    }

    @Override
    public void repositionGoingDown(int moveValue, T object)
    {
    	IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());

        for (int i = collision.y - 1; i >= collision.y - moveValue; i--)
            for (int j = collision.x; j <= collision.getRightBound(); j++)
                collisionMap[j][i] = object;

        for (int i = collision.y + collision.height; i >= collision.y + collision.height - moveValue + 1; i--)
            for (int j = collision.x; j <= collision.getRightBound(); j++)
                collisionMap[j][i] = null;
    }

    @Override
    public void repositionGoingUp(int moveValue, T object)
    {
        IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());

        for (int i = collision.y + collision.height + 1; i <= collision.y + collision.height + moveValue; i++)
            for (int j = collision.x; j <= collision.getRightBound(); j++)
                collisionMap[j][i] = object;

        for (int i = collision.y + moveValue - 1; i >= collision.y; i--)
            for (int j = collision.x; j <= collision.getRightBound(); j++)
                collisionMap[j][i] = null;
    }
    
    public void shift(int shiftX, int shiftY)
    {
    	this.shiftX = shiftX;
    	this.shiftY = shiftY;
    	throw new NotImplementedException();
    }

	@Override
	public T getObject(int gameX, int gameY)
	{
		if (gameX >= collisionMap.length || gameY >= collisionMap[0].length
                || gameX < 0 || gameY < 0)
            return null;
        return (T)collisionMap[gameX][gameY];
	}
}
