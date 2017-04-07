package pl.mmorpg.prototype.server.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.server.collision.CollisionMap;


public abstract class MovableGameObject extends GameObject
{
    private float moveSpeed = 100.0f;
	private float movementX = 0.0f;
	private float movementY = 0.0f;

	protected MovableGameObject(Texture lookout, long id)
    {
		super(lookout, id);
    }

	public GameObject moveRight(CollisionMap collisionMap)
	{
		movementX += getMoveSpeed() * Gdx.graphics.getDeltaTime();
		int collisionMoveValue = (int) movementX;
		movementX -= (int) movementX;
		GameObject collision = moveRight(collisionMap, collisionMoveValue);
		if (collision != null)
			while (moveRight(collisionMap, 1) == null)
				;
		return collision;
	}

	public GameObject moveLeft(CollisionMap collisionMap)
	{
		movementX -= getMoveSpeed() * Gdx.graphics.getDeltaTime();
		int collisionMoveValue = (int) -movementX;
		movementX -= (int) movementX;
		GameObject collision = moveLeft(collisionMap, collisionMoveValue);
		if (collision != null)
			while (moveLeft(collisionMap, 1) == null)
				;
		return collision;
	}

	public GameObject moveDown(CollisionMap collisionMap)
	{
		movementY -= getMoveSpeed() * Gdx.graphics.getDeltaTime();
		int collisionMoveValue = (int) -movementY;
		movementY -= (int) movementY;
		GameObject collision = moveDown(collisionMap, collisionMoveValue);
		if (collision != null)
			while (moveDown(collisionMap, 1) == null)
				;
		return collision;
	}

	public GameObject moveUp(CollisionMap collisionMap)
	{
		movementY += getMoveSpeed() * Gdx.graphics.getDeltaTime();
		int collisionMoveValue = (int) movementY;
		movementY -= (int) movementY;
		GameObject collision = moveUp(collisionMap, collisionMoveValue);
		if (collision != null)
			while (moveUp(collisionMap, 1) == null)
				;
		return collision;
	}

	public GameObject moveRight(CollisionMap collisionMap, int moveValue)
    {
		GameObject collision = collisionMap.tryToRepositionCollisionGoingRight(moveValue, this);
		if (collision == null)
			setX(getX() + moveValue*collisionMap.getScale());
        return collision;
    }

    private void fixPositionOnCollisionAfterMovingRight(GameObject collision)
    {
        moveLeft();
		/*
		 * if (collision.getY() > getY()) setY(getY() - getMoveSpeed() *
		 * Gdx.graphics.getDeltaTime() / 6.0f); else setY(getY() +
		 * getMoveSpeed() * Gdx.graphics.getDeltaTime() / 6.0f);
		 */
    }

	public GameObject moveLeft(CollisionMap collisionMap, int moveValue)
    {
		GameObject collision = collisionMap.tryToRepositionCollisionGoingLeft(moveValue, this);
		if (collision == null)
			setX(getX() - moveValue*collisionMap.getScale());
		return collision;
    }

    private void fixPositionOnCollisionAfterMovingLeft(GameObject collision)
    {
        moveRight();
		/*
		 * if (collision.getY() > getY()) setY(getY() - getMoveSpeed() *
		 * Gdx.graphics.getDeltaTime() / 6.0f); else setY(getY() +
		 * getMoveSpeed() * Gdx.graphics.getDeltaTime() / 6.0f);
		 */
    }

	public GameObject moveUp(CollisionMap collisionMap, int moveValue)
    {
		GameObject collision = collisionMap.tryToRepositionCollisionGoingUp(moveValue, this);
		if (collision == null)
			setY(getY() + moveValue*collisionMap.getScale());
		return collision;
    }

    private void fixPositionOnCollisionAfterMovingUp(GameObject collision)
    {
        moveDown();
		/*
		 * if (collision.getX() > getX()) setX(getX() - getMoveSpeed() *
		 * Gdx.graphics.getDeltaTime() / 6.0f); else setX(getX() +
		 * getMoveSpeed() * Gdx.graphics.getDeltaTime() / 6.0f);
		 */
    }

	public GameObject moveDown(CollisionMap collisionMap, int moveValue)
    {
		GameObject collision = collisionMap.tryToRepositionCollisionGoingDown(moveValue, this);
		if (collision == null)
			setY(getY() - moveValue*collisionMap.getScale());
		return collision;
    }

    private void fixPositionOnCollisionAfterMovingDown(GameObject collision)
    {
        moveUp();
		/*
		 * if (collision.getX() > getX()) setX(getX() - getMoveSpeed() *
		 * Gdx.graphics.getDeltaTime() / 6.0f); else setX(getX() +
		 * getMoveSpeed() * Gdx.graphics.getDeltaTime() / 6.0f);
		 */
    }

    public void moveLeft()
    {
        setX(getX() - getMoveSpeed() * Gdx.graphics.getDeltaTime());
    }

    public void moveRight()
    {
        setX(getX() + getMoveSpeed() * Gdx.graphics.getDeltaTime());
    }

    public void moveDown()
    {
        setY(getY() - getMoveSpeed() * Gdx.graphics.getDeltaTime());
    }

    public void moveUp()
    {
        setY(getY() + getMoveSpeed() * Gdx.graphics.getDeltaTime());
    }

    public float getMoveSpeed()
    {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed)
    {
        this.moveSpeed = moveSpeed;
    }

}
