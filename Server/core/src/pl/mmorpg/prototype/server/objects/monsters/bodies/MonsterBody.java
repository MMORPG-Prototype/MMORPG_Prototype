package pl.mmorpg.prototype.server.objects.monsters.bodies;

import java.awt.Point;
import java.util.Collection;

import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.server.collision.interfaces.RectangleCollisionObject;
import pl.mmorpg.prototype.server.collision.interfaces.StackableCollisionObject;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.items.Item;

public class MonsterBody extends GameObject implements StackableCollisionObject
{
	private Collection<Item> loot;
	private int gold;
	private int containerId = -1;

	public MonsterBody(Texture lookout, long id, int gold, Collection<Item> loot)
	{
		super(lookout, id);
		this.gold = gold;
		this.loot = loot;
	}

	@Override
	public void update(float deltaTime)
	{
	}
	
	public Collection<Item> getLoot()
	{
		return loot;
	}
	
	public int getGold()
	{
		return gold;
	}

	@Override
	public Point getCenter()
	{
		return new Point((int)(getX() + getWidth()/2), (int)(getY() + getHeight()/2));
	}

	@Override
	public boolean isColliding(RectangleCollisionObject object)
	{
		return getCollisionRect().overlaps(object.getCollisionRect());
	}

	@Override
	public int getCollisionContainerId()
	{
		if(containerId == -1)
			Log.error("Container id was not set, but it is used");
		return containerId;
	}

	@Override
	public void setCollisionContainerId(int id)
	{
		containerId = id;	
	}

}
