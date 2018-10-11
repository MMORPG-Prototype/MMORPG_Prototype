package pl.mmorpg.prototype.client.objects.monsters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.AttackingGameObject;
import pl.mmorpg.prototype.client.objects.CustomAnimation;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.clientservercommon.StatisticsCalculator;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.Statistics;

public abstract class Monster extends AttackingGameObject
{
	private final MonsterProperties properties;
	private final Statistics statistics;

	protected Monster(TextureSheetAnimationInfo sheetInfo, long id, MonsterProperties properties,
			CollisionMap<GameObject> collisionMap, PacketHandlerRegisterer registerer)
	{
		super(sheetInfo, id, collisionMap, registerer);
		this.properties = properties;
		this.statistics = StatisticsCalculator.calculateStatistics(properties);
	}

	public Monster(long id, PacketHandlerRegisterer registerer, CustomAnimation<TextureRegion> moveLeftAnimation,
			CustomAnimation<TextureRegion> moveRightAnimation, CustomAnimation<TextureRegion> moveDownAnimation,
			CustomAnimation<TextureRegion> moveUpAnimation, MonsterProperties properties, CollisionMap<GameObject> collisionMap)
	{
		super(id, registerer, moveLeftAnimation, moveRightAnimation, moveDownAnimation, moveUpAnimation, collisionMap);
		this.properties = properties;
		this.statistics = StatisticsCalculator.calculateStatistics(properties);
	}
	
	public MonsterProperties getProperties()
	{
		return properties;
	}

	public void gotHitBy(int damage)
	{
		properties.hp -= damage;	
	}

	public Statistics getStatistics()
	{
		return statistics;
	}
}
