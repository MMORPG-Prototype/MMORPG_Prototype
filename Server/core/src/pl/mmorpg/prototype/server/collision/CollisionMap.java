package pl.mmorpg.prototype.server.collision;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.MovableGameObject;

public class CollisionMap
{
	private GameObject nullObject = GameObject.NULL_OBJECT;
	private GameObject[][] collisionMap;

	public CollisionMap(int width, int height)
	{
		collisionMap = createCollisionMap(width, height);
		collisionMap = placeNullObjectOnBorder(collisionMap);
	}

	private GameObject[][] createCollisionMap(int width, int height)
	{
		GameObject[][] collisionMap = new GameObject[width][];
		for (int i = 0; i < width; i++)
		{
			collisionMap[i] = new GameObject[height];
			for (int j = 0; j < height; j++)
				collisionMap[i][j] = null;
		}
		return collisionMap;
	}

	private GameObject[][] placeNullObjectOnBorder(GameObject[][] collisionMap)
	{
		for (int i = 0; i < collisionMap.length; i++)
		{
			collisionMap[i][0] = nullObject;
			collisionMap[i][collisionMap.length - 1] = nullObject;
		}

		for (int j = 0; j < collisionMap[0].length; j++)
		{
			collisionMap[0][j] = nullObject;
			collisionMap[collisionMap[0].length - 1][j] = nullObject;
		}
		return collisionMap;
	}

	public void insert(GameObject object)
	{
		IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());

		for (int i = collision.x; i < collision.x + collision.width; i++)
			for (int j = collision.y; j < collision.y + collision.height; j++)
				collisionMap[i][j] = object;
	}

	public GameObject tryToRepositionCollisionGoingLeft(int moveValue, MovableGameObject object)
	{
		GameObject possibleCollision = checkForSpaceGoingLeft(moveValue,
				new IntegerRectangle(object.getCollisionRect()));
		if (possibleCollision == null)
			repositionCollisionGoingLeft(moveValue, object);
		return object;
	}

	public GameObject tryToRepositionCollisionGoingRight(int moveValue, MovableGameObject object)
	{
		GameObject possibleCollision = checkForSpaceGoingRight(moveValue,
				new IntegerRectangle(object.getCollisionRect()));
		if (possibleCollision == null)
			repositionCollisionGoingRight(moveValue, object);
		return object;
	}

	public GameObject tryToRepositionCollisionGoingDown(int moveValue, MovableGameObject object)
	{
		GameObject possibleCollision = checkForSpaceGoingDown(moveValue,
				new IntegerRectangle(object.getCollisionRect()));
		if (possibleCollision == null)
			repositionCollisionGoingDown(moveValue, object);
		return object;
	}

	public GameObject tryToRepositionCollisionGoingUp(int moveValue, MovableGameObject object)
	{
		GameObject possibleCollision = checkForSpaceGoingUp(moveValue, new IntegerRectangle(object.getCollisionRect()));
		if (possibleCollision == null)
			repositionCollisionGoingUp(moveValue, object);
		return object;
	}

	private GameObject checkForSpaceGoingLeft(int moveValue, IntegerRectangle collision)
	{
		for (int i = collision.x - 1; i >= collision.getRightBound() - moveValue; i--)
			for (int j = collision.y; j <= collision.getUpperBound(); j++)
				if (collisionMap[i][j] != null)
					return collisionMap[i][j];
		return null;
	}

	private GameObject checkForSpaceGoingRight(int moveValue, IntegerRectangle collision)
	{
		for (int i = collision.getRightBound() + 1; i <= collision.getRightBound() + moveValue; i++)
			for (int j = collision.y; j <= collision.getUpperBound(); j++)
				if (collisionMap[i][j] != null)
					return collisionMap[i][j];
		return null;
	}

	private GameObject checkForSpaceGoingDown(int moveValue, IntegerRectangle collision)
	{
		for (int i = collision.y - 1; i >= collision.y - moveValue; i--)
			for (int j = collision.x; j <= collision.getRightBound(); j++)
				if (collisionMap[j][i] != null)
					return collisionMap[j][i];
		return null;
	}

	private GameObject checkForSpaceGoingUp(int moveValue, IntegerRectangle collision)
	{
		for (int i = collision.getUpperBound(); i <= collision.getUpperBound() + moveValue; i++)
			for (int j = collision.x; j <= collision.getRightBound(); j++)
				if (collisionMap[j][i] != null)
					return collisionMap[j][i];
		return null;
	}

	private void repositionCollisionGoingLeft(int moveValue, GameObject object)
	{
		IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());

		for (int i = collision.y; i <= collision.getUpperBound(); i++)
			for (int j = collision.x - moveValue; j < collision.x; j++)
				collisionMap[j][i] = object;

		for (int i = collision.y; i <= collision.getUpperBound(); i++)
			for (int j = collision.getRightBound() - moveValue + 1; j <= collision.getRightBound(); j++)
				collisionMap[j][i] = null;
	}

	private void repositionCollisionGoingRight(int moveValue, GameObject object)
	{
		IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());

		for (int i = collision.y; i <= collision.getUpperBound(); i++)
			for (int j = collision.getRightBound() + 1; j <= collision.getRightBound() + moveValue; j++)
				collisionMap[j][i] = object;

		for (int i = collision.y; i <= collision.getUpperBound(); i++)
			for (int j = collision.x; j < collision.x + moveValue; j++)
				collisionMap[j][i] = null;
	}

	private void repositionCollisionGoingDown(int moveValue, GameObject object)
	{
		IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());

		for (int i = collision.getUpperBound() + 1; i <= collision.getUpperBound() + moveValue; i++)
			for (int j = collision.x; j <= collision.getRightBound(); j++)
				collisionMap[j][i] = object;

		for (int i = collision.y; i < collision.y + moveValue; i++)
			for (int j = collision.x; j <= collision.getRightBound(); j++)
				collisionMap[j][i] = null;
	}

	private void repositionCollisionGoingUp(int moveValue, GameObject object)
	{
		IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect());

		for (int i = collision.y - moveValue; i < collision.y; i++)
			for (int j = collision.x; j <= collision.getRightBound(); j++)
				collisionMap[j][i] = object;

		for (int i = collision.getUpperBound() - moveValue + 1; i <= collision.getUpperBound(); i++)
			for (int j = collision.x; j <= collision.getRightBound(); j++)
				collisionMap[j][i] = null;
	}

	public void render(SpriteBatch batch)
	{
		Pixmap pixmap = new Pixmap(collisionMap.length, collisionMap[0].length, Format.RGB888);
		pixmap.setColor(new Color(0.2f, 0.2f, 0.2f, 0.9f));
		for (int i = 0; i < collisionMap.length; i++)
			for (int j = 0; j < collisionMap[0].length; j++)
				if (collisionMap[i][j] != null)
					pixmap.drawPixel(i, j);

		Texture img = new Texture(pixmap);
		pixmap.dispose();
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
		img.dispose();
	}

	public GameObject isColliding(Rectangle rectangle)
	{
		IntegerRectangle collision = new IntegerRectangle(rectangle);

		for (int i = collision.x; i < collision.x + collision.width; i++)
			for (int j = collision.y; j < collision.y + collision.height; j++)
				if (collisionMap[i][j] != null)
					return collisionMap[i][j];
		return null;
	}

}
