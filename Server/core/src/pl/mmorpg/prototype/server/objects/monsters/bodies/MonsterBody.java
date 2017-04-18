package pl.mmorpg.prototype.server.objects.monsters.bodies;

import java.util.Collection;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.items.Item;

public class MonsterBody extends GameObject
{
	private Collection<Item> loot;
	private int gold;

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

}
