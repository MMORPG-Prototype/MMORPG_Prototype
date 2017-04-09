package pl.mmorpg.prototype.server.collision;

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
    private int scale;

    public CollisionMap(int width, int height)
    {
        this(width, height, 1);
    }
    
    public CollisionMap(int width, int height, int scale)
    {
        this.scale = scale;
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
            collisionMap[i][collisionMap[0].length - 1] = nullObject;
        }

        for (int j = 0; j < collisionMap[0].length; j++)
        {
            collisionMap[0][j] = nullObject;
            collisionMap[collisionMap.length - 1][j] = nullObject;
        }
        return collisionMap;
    }
    
    public void setScale(int scale)
    {
        this.scale = scale;
    }
    
    public int getScale()
    {
        return scale;
    }

    public void insert(GameObject object)
    {
        IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect(), scale);

        for (int i = collision.x; i <= collision.x + collision.width; i++)
            for (int j = collision.y; j <= collision.y + collision.height; j++)
                collisionMap[i][j] = object;

    }

    public void undefinedObjectInsert(Rectangle rectangle)
    {
        MapCollisionUnknownObject unknownObject = new MapCollisionUnknownObject(rectangle);
        IntegerRectangle collision = new IntegerRectangle(rectangle, scale);
        for (int i = collision.x; i <= collision.x + collision.width; i++)
            for (int j = collision.y; j <= collision.y + collision.height; j++)
                collisionMap[i][j] = unknownObject;
    }

    public void remove(GameObject object)
    {
        IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect(), scale);

        for (int i = collision.x; i <= collision.x + collision.width; i++)
            for (int j = collision.y; j <= collision.y + collision.height; j++)
                collisionMap[i][j] = null;
    }

    public GameObject tryToRepositionCollisionGoingLeft(int moveValue, MovableGameObject object)
    {
        GameObject possibleCollision = checkForSpaceGoingLeft(moveValue,
                new IntegerRectangle(object.getCollisionRect(), scale));
        if (possibleCollision == null)
            repositionCollisionGoingLeft(moveValue, object);
        return possibleCollision;
    }

    public GameObject tryToRepositionCollisionGoingRight(int moveValue, MovableGameObject object)
    {
        GameObject possibleCollision = checkForSpaceGoingRight(moveValue,
                new IntegerRectangle(object.getCollisionRect(), scale));
        if (possibleCollision == null)
            repositionCollisionGoingRight(moveValue, object);
        return possibleCollision;
    }

    public GameObject tryToRepositionCollisionGoingDown(int moveValue, MovableGameObject object)
    {
        GameObject possibleCollision = checkForSpaceGoingDown(moveValue,
                new IntegerRectangle(object.getCollisionRect(), scale));
        if (possibleCollision == null)
            repositionCollisionGoingDown(moveValue, object);
        return possibleCollision;
    }

    public GameObject tryToRepositionCollisionGoingUp(int moveValue, MovableGameObject object)
    {
        GameObject possibleCollision = checkForSpaceGoingUp(moveValue, new IntegerRectangle(object.getCollisionRect(), scale));
        if (possibleCollision == null)
            repositionCollisionGoingUp(moveValue, object);
        return possibleCollision;
    }

    private GameObject checkForSpaceGoingLeft(int moveValue, IntegerRectangle collision)
    {
        for (int i = collision.x - 1; i >= collision.x - moveValue; i--)
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
        for (int i = collision.getUpperBound() + 1; i <= collision.getUpperBound() + moveValue; i++)
            for (int j = collision.x; j <= collision.getRightBound(); j++)
                if (collisionMap[j][i] != null)
                    return collisionMap[j][i];
        return null;
    }

    private void repositionCollisionGoingLeft(int moveValue, GameObject object)
    {
        IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect(), scale);

        for (int i = collision.y; i <= collision.getUpperBound(); i++)
            for (int j = collision.x - moveValue; j < collision.x; j++)
                collisionMap[j][i] = object;

        for (int i = collision.y; i <= collision.getUpperBound(); i++)
            for (int j = collision.getRightBound() - moveValue + 1; j <= collision.getRightBound(); j++)
                collisionMap[j][i] = null;
    }

    private void repositionCollisionGoingRight(int moveValue, GameObject object)
    {
        IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect(), scale);

        for (int i = collision.y; i <= collision.getUpperBound(); i++)
            for (int j = collision.getRightBound() + 1; j <= collision.getRightBound() + moveValue; j++)
                collisionMap[j][i] = object;

        for (int i = collision.y; i <= collision.getUpperBound(); i++)
            for (int j = collision.x; j < collision.x + moveValue; j++)
                collisionMap[j][i] = null;
    }

    private void repositionCollisionGoingDown(int moveValue, GameObject object)
    {
        IntegerRectangle collision = new IntegerRectangle(object.getCollisionRect(), scale);

        for (int i = collision.y - 1; i >= collision.y - moveValue; i--)
            for (int j = collision.x; j <= collision.getRightBound(); j++)
                collisionMap[j][i] = object;

        for (int i = collision.y + collision.height; i >= collision.y + collision.height - moveValue + 1; i--)
            for (int j = collision.x; j <= collision.getRightBound(); j++)
                collisionMap[j][i] = null;
    }

    private void repositionCollisionGoingUp(int moveValue, GameObject object)
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
        Pixmap drawable = new Pixmap(1, 1, Format.Alpha);
        Pixmap.setBlending(Pixmap.Blending.None);
        for (int i = 0; i < collisionMap.length; i += 1)
            for (int j = 0; j < collisionMap[0].length; j += 1)
                if (collisionMap[i][j] != null)
                {
                    drawable.setColor(1.0f, 1.0f, 0.0f, 0.5f);
                    drawable.drawRectangle(10, 10, 100, 100);
                    Texture img = new Texture(drawable);
                    batch.draw(img, i, j);
                    img.dispose();
                }
        drawable.dispose();

    }

    public GameObject isColliding(Rectangle rectangle)
    {
        IntegerRectangle collision = new IntegerRectangle(rectangle, scale);

        for (int i = collision.x; i < collision.x + collision.width; i++)
            for (int j = collision.y; j < collision.y + collision.height; j++)
                if (collisionMap[i][j] != null)
                    return collisionMap[i][j];
        return null;
    }

	public GameObject get(int gameX, int gameY)
	{
		return collisionMap[gameX / scale][gameY / scale];
	}

}
