package pl.mmorpg.prototype.client.collision.pixelmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import pl.mmorpg.prototype.client.collision.interfaces.RectangleCollisionObject;
import pl.mmorpg.prototype.client.collision.interfaces.ShiftableCollisionMap;
import pl.mmorpg.prototype.clientservercommon.Identifiable;

@SuppressWarnings("unchecked")
public class PixelCollisionMap<T extends RectangleCollisionObject & Identifiable> implements ShiftableCollisionMap<T>
{
	private Object[][] collisionMap;
	private Map<Long, CollisionObjectInfo<T>> insertedCollisionObjects = new ConcurrentHashMap<>();
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
		insert(new CollisionObjectInfo<>(object));
	}

	public void insert(CollisionObjectInfo<T> collisionInfo)
	{
		T object = collisionInfo.getObject();
		insertedCollisionObjects.put(object.getId(), collisionInfo);
		IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());
		if (!fitsInMap(collision))
			return;
		collisionInfo.setOnCollisionMap(true);
		insertCollisionOnly(collision, object);
	}

	private void insertCollisionOnly(IntegerRectangle collision, T object)
	{
		for (int i = collision.x; i < collision.x + collision.width; i++)
			for (int j = collision.y; j < collision.y + collision.height; j++)
				collisionMap[i - shiftX][j - shiftY] = object;
	}

	private boolean fitsInMap(IntegerRectangle collision)
	{
		return isValidPoint(collision.getX(), collision.getY())
				&& isValidPoint(collision.getRightBound(), collision.getUpperBound());
	}

	private boolean isValidPoint(int x, int y)
	{
		return x >= shiftX && x < collisionMap.length + shiftX && y >= shiftY && y <= collisionMap[0].length + shiftY;
	}

	@Override
	public void remove(T object)
	{
		insertedCollisionObjects.remove(object.getId());
		IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());
		if (!fitsInMap(collision))
			return;

		removeCollisionOnly(collision);
	}

	private void removeCollisionOnly(IntegerRectangle collision)
	{
		for (int i = collision.x; i < collision.x + collision.width; i++)
			for (int j = collision.y; j < collision.y + collision.height; j++)
				collisionMap[i - shiftX][j - shiftY] = null;
	}

	@Override
	public void repositionGoingLeft(int moveValue, T object)
	{
		IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());
		repositionGoingLeft(moveValue, collision, object);
	}

	private void repositionGoingLeft(int moveValue, IntegerRectangle collision, T object)
	{
		IntegerRectangle shiftedCollision = new IntegerRectangle(collision);
		shiftedCollision.x -= moveValue;
		repositionWithBoundsChecking(moveValue, collision, shiftedCollision, object,
				() -> repositionCollisionOnlyGoingLeft(moveValue, collision, object));
	}

	private void repositionCollisionOnlyGoingLeft(int moveValue, IntegerRectangle collision, T object)
	{
		for (int i = collision.y; i <= collision.getUpperBound(); i++)
			for (int j = collision.x - moveValue; j < collision.x; j++)
				collisionMap[j - shiftX][i - shiftY] = object;

		for (int i = collision.y; i <= collision.getUpperBound(); i++)
			for (int j = collision.getRightBound() - moveValue + 1; j <= collision.getRightBound(); j++)
				collisionMap[j - shiftX][i - shiftY] = null;
	}

	@Override
	public void repositionGoingRight(int moveValue, T object)
	{
		IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());
		repositionGoingRight(moveValue, collision, object);
	}

	private void repositionGoingRight(int moveValue, IntegerRectangle collision, T object)
	{
		IntegerRectangle shiftedCollision = new IntegerRectangle(collision);
		shiftedCollision.x += moveValue;
		repositionWithBoundsChecking(moveValue, collision, shiftedCollision, object,
				() -> repositionCollisionOnlyGoingRight(moveValue, collision, object));
	}

	private void repositionCollisionOnlyGoingRight(int moveValue, IntegerRectangle collision, T object)
	{
		for (int i = collision.y; i <= collision.getUpperBound(); i++)
			for (int j = collision.getRightBound(); j <= collision.getRightBound() + moveValue; j++)
				collisionMap[j - shiftX][i - shiftY] = object;

		for (int i = collision.y; i <= collision.getUpperBound(); i++)
			for (int j = collision.x; j < collision.x + moveValue; j++)
				collisionMap[j - shiftX][i - shiftY] = null;
	}

	@Override
	public void repositionGoingDown(int moveValue, T object)
	{
		IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());
		repositionGoingDown(moveValue, collision, object);
	}

	private void repositionGoingDown(int moveValue, IntegerRectangle collision, T object)
	{
		IntegerRectangle shiftedCollision = new IntegerRectangle(collision);
		shiftedCollision.y -= moveValue;
		repositionWithBoundsChecking(moveValue, collision, shiftedCollision, object,
				() -> repositionCollisionOnlyGoingDown(moveValue, collision, object));
	} 

	private void repositionWithBoundsChecking(int moveValue, IntegerRectangle collision, IntegerRectangle shiftedCollision,
			T object, Runnable repositionInProperDirection)
	{
		CollisionObjectInfo<T> collisionObjectInfo = insertedCollisionObjects.get(object.getId());
		if (fitsInMap(shiftedCollision))
		{
			if (!collisionObjectInfo.isOnCollisionMap())
			{
				insertCollisionOnly(shiftedCollision, object);
				collisionObjectInfo.setOnCollisionMap(true);
			} else
				repositionInProperDirection.run();

		} else if (collisionObjectInfo.isOnCollisionMap())
		{
			removeCollisionOnly(collision);
			collisionObjectInfo.setOnCollisionMap(false);
		}
	}

	private void repositionCollisionOnlyGoingDown(int moveValue, IntegerRectangle collision, T object)
	{
		for (int i = collision.y - 1; i >= collision.y - moveValue; i--)
			for (int j = collision.x; j <= collision.getRightBound(); j++)
				collisionMap[j - shiftX][i - shiftY] = object;

		for (int i = collision.y + collision.height - 1; i > collision.y + collision.height - moveValue - 1; i--)
			for (int j = collision.x; j <= collision.getRightBound(); j++)
				collisionMap[j - shiftX][i - shiftY] = null;
	}

	@Override
	public void repositionGoingUp(int moveValue, T object)
	{
		IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());
		repositionGoingUp(moveValue, collision, object);
	}

	private void repositionGoingUp(int moveValue, IntegerRectangle collision, T object)
	{
		IntegerRectangle shiftedCollision = new IntegerRectangle(collision);
		shiftedCollision.y += moveValue;
		repositionWithBoundsChecking(moveValue, collision, shiftedCollision, object,
				() -> repositionCollisionOnlyGoingUp(moveValue, collision, object));
	}

	private void repositionCollisionOnlyGoingUp(int moveValue, IntegerRectangle collision, T object)
	{
		for (int i = collision.y + collision.height; i < collision.y + collision.height + moveValue; i++)
			for (int j = collision.x; j <= collision.getRightBound(); j++)
				collisionMap[j - shiftX][i - shiftY] = object;

		for (int i = collision.y + moveValue - 1; i >= collision.y; i--)
			for (int j = collision.x; j <= collision.getRightBound(); j++)
				collisionMap[j - shiftX][i - shiftY] = null;
	}

	@Override
	public void update(int shiftX, int shiftY)
	{
		int deltaX = shiftX - this.shiftX;
		int deltaY = shiftY - this.shiftY;
		doShift(deltaX, deltaY);
		this.shiftX = shiftX;
		this.shiftY = shiftY;
	}

	private void doShift(int deltaX, int deltaY)
	{
		if (deltaX == 0 && deltaY == 0)
			return;

		if (deltaX > 0 && deltaY > 0)
			insertedCollisionObjects.values().forEach(info -> repositionLeftAndDown(deltaX, deltaY, info.getObject()));
		else if (deltaX > 0 && deltaY < 0)
			insertedCollisionObjects.values().forEach(info -> repositionLeftAndUp(deltaX, -deltaY, info.getObject()));
		else if (deltaX < 0 && deltaY > 0)
			insertedCollisionObjects.values()
					.forEach(info -> repositionRightAndDown(-deltaX, deltaY, info.getObject()));
		else if (deltaX < 0 && deltaY < 0)
			insertedCollisionObjects.values().forEach(info -> repositionRightAndUp(-deltaX, -deltaY, info.getObject()));
		else if (deltaX == 0)
			shiftY(deltaY);
		else
			shiftX(deltaX);
	}

	private void repositionLeftAndDown(int moveX, int moveY, T object)
	{
		IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());
		repositionGoingDown(moveY, collision, object);
		collision.y -= moveY;
		repositionGoingLeft(moveX, collision, object);
	}

	private void repositionLeftAndUp(int moveX, int moveY, T object)
	{
		IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());
		repositionGoingUp(moveY, collision, object);
		collision.y += moveY;
		repositionGoingLeft(moveX, collision, object);
	}

	private void repositionRightAndDown(int moveX, int moveY, T object)
	{
		IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());
		repositionGoingDown(moveY, collision, object);
		collision.y -= moveY;
		repositionGoingRight(moveX, collision, object);
	}

	private void repositionRightAndUp(int moveX, int moveY, T object)
	{
		IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());
		repositionGoingUp(moveY, collision, object);
		collision.y += moveY;
		repositionGoingRight(moveX, collision, object);
	}

	private void shiftX(int deltaShiftX)
	{
		if (deltaShiftX > 0)
			insertedCollisionObjects.values().forEach(info -> repositionGoingLeft(deltaShiftX, info.getObject()));
		else if (deltaShiftX < 0)
			insertedCollisionObjects.values().forEach(info -> repositionGoingRight(-deltaShiftX, info.getObject()));
	}

	private void shiftY(int deltaShiftY)
	{
		if (deltaShiftY > 0)
			insertedCollisionObjects.values().forEach(info -> repositionGoingDown(deltaShiftY, info.getObject()));
		else if (deltaShiftY < 0)
			insertedCollisionObjects.values().forEach(info -> repositionGoingUp(-deltaShiftY, info.getObject()));
	}

	@Override
	public T getObject(int gameX, int gameY)
	{
		int mapX = gameX - shiftX;
		int mapY = gameY - shiftY;
		if (mapX >= collisionMap.length || mapY >= collisionMap[0].length || mapX < 0 || mapY < 0)
			return null;
		return (T) collisionMap[mapX][mapY];
	}

	public void debugMethodRender(Batch batch, Texture texture)
	{
		for (int i = 0; i < collisionMap.length; i++)
			for (int j = 0; j < collisionMap[i].length; j++)
				if (collisionMap[i][j] != null)
					batch.draw(texture, i, j, 1, 1);
	}
}
