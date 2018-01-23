package pl.mmorpg.prototype.client.objects.monsters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.CustomAnimation;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.graphic.HealthBar;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;

public abstract class HealthBarMonster extends Monster
{
	private HealthBar healthBar;

	public HealthBarMonster(TextureSheetAnimationInfo sheetInfo, long id, MonsterProperties monsterProperties,
			CollisionMap<GameObject> collisionMap, PacketHandlerRegisterer registerer)
	{
		super(sheetInfo, id, monsterProperties, collisionMap, registerer);
		healthBar = new HealthBar(monsterProperties.maxHp, this);
	}

	public HealthBarMonster(long id, CustomAnimation<TextureRegion> moveLeftAnimation, PacketHandlerRegisterer registerer,
			CustomAnimation<TextureRegion> moveRightAnimation, CustomAnimation<TextureRegion> moveDownAnimation,
			CustomAnimation<TextureRegion> moveUpAnimation, MonsterProperties monster, CollisionMap<GameObject> collisionMap)
	{
		super(id, registerer, moveLeftAnimation, moveRightAnimation, moveDownAnimation, moveUpAnimation, monster, collisionMap);
		healthBar = new HealthBar(monster.maxHp, this);
	
	}
	@Override
	public void update(float deltaTime)
	{
		healthBar.update(deltaTime);
		healthBar.updateBar(properties.hp);
		super.update(deltaTime);
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		healthBar.render(batch);
		super.render(batch);
	}
	
	protected void recreateHealthBar()
	{
		healthBar = new HealthBar(getProperties().maxHp, this);
	}

}
