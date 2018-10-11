package pl.mmorpg.prototype.client.collision;

import com.badlogic.gdx.Game;
import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.GameObject;

public class NullCollisionMap implements CollisionMap<GameObject>
{
	@Override
	public void insert(GameObject object)
	{
	}

	@Override
	public void remove(GameObject object)
	{
	}

	@Override
	public boolean isValidPoint(int gameX, int gameY)
	{
		return false;
	}

	@Override
	public GameObject getObject(int gameX, int gameY)
	{
		return null;
	}

	@Override
	public void repositionGoingRight(int moveValue, GameObject object)
	{
	}

	@Override
	public void repositionGoingLeft(int moveValue, GameObject object)
	{
	}

	@Override
	public void repositionGoingUp(int moveValue, GameObject object)
	{
	}

	@Override
	public void repositionGoingDown(int moveValue, GameObject object)
	{
	}

	@Override
	public void clear()
	{
	}
}
