package pl.mmorpg.prototype.client.objects.monsters.npcs;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.monsters.Monster;
import pl.mmorpg.prototype.client.objects.monsters.TextureSheetAnimationInfo;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.NpcDefaultPropertiesBuilder;

public abstract class Npc extends Monster
{
	private final String name;
	
	protected Npc(String name, TextureSheetAnimationInfo sheetInfo, long id, CollisionMap<GameObject> collisionMap)
	{
		super(sheetInfo, id, new NpcDefaultPropertiesBuilder().build(), collisionMap);
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
