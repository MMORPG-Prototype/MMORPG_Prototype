package pl.mmorpg.prototype.client.collision.pixelmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import pl.mmorpg.prototype.client.collision.interfaces.RectangleCollisionObject;
import pl.mmorpg.prototype.client.collision.interfaces.ShiftableCollisionMap;
import pl.mmorpg.prototype.clientservercommon.Identifiable;

@SuppressWarnings("unchecked")
public class PixelCollisionMap<T extends RectangleCollisionObject & Identifiable> implements ShiftableCollisionMap<T>
{
    private Object[][] collisionMap;
    private Map<Long, T> insertedCollisionObjects = new ConcurrentHashMap<>();
    private int shiftX = 0;
    private int shiftY = 0;
    
    public PixelCollisionMap(int width, int height)
    {
        this(width, height, 0, 0);
    }
    
    public PixelCollisionMap(int width, int height, int shiftX, int shiftY)
    {
        collisionMap = createCollisionMap(width, height);
        this.shiftX = shiftX;
        this.shiftY = shiftY;
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
        Rectangle collisionRect = object.getCollisionRect();
		IntegerRectangle collision = new IntegerRectangle(collisionRect);
        
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
    
	@Override
	public void update(int shiftX, int shiftY)
	{
		int deltaX = shiftX - this.shiftX;
		int deltaY = shiftY - this.shiftY;
    	this.shiftX = shiftX;
    	this.shiftY = shiftY;
    	if(deltaX != 0)
    		shiftX(deltaX);
    	if(deltaY != 0)
    		shiftY(deltaY);
	}
    
    private void shiftX(int deltaShiftX)
    {
    	if(deltaShiftX > 0)
    		insertedCollisionObjects.values().forEach(object -> repositionGoingLeft(deltaShiftX, object));
    	else
    		insertedCollisionObjects.values().forEach(object -> repositionGoingRight(-deltaShiftX, object));
    }
    
    private void shiftY(int deltaShiftY)
    {
    	if(deltaShiftY > 0)
    		insertedCollisionObjects.values().forEach(object -> repositionGoingDown(deltaShiftY, object));
    	else
    		insertedCollisionObjects.values().forEach(object -> repositionGoingUp(-deltaShiftY, object));
    }

	@Override
	public T getObject(int gameX, int gameY)
	{
		gameX -= shiftX;
		gameY -= shiftY;
		if (gameX >= collisionMap.length || gameY >= collisionMap[0].length
                || gameX < 0 || gameY < 0)
            return null;
        return (T)collisionMap[gameX][gameY];
	}
	
	public void debugMethodRender(Batch batch, Texture texture)
	{
		for (int i = 0; i < collisionMap.length; i++)
            for (int j = 0; j < collisionMap[i].length; j++)
                if(collisionMap[i][j] != null)
                	batch.draw(texture, i, j, 1, 1);        
	}
}
