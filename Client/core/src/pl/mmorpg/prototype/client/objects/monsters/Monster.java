package pl.mmorpg.prototype.client.objects.monsters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.mmorpg.prototype.client.objects.AttackingGameObject;
import pl.mmorpg.prototype.client.objects.CustomAnimation;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;

public abstract class Monster extends AttackingGameObject
{
	protected MonsterProperties properties;

	protected Monster(TextureSheetAnimationInfo sheetInfo, long id, MonsterProperties properties)
	{
		super(sheetInfo, id);
		this.properties = properties;
	}

	public Monster(long id, CustomAnimation<TextureRegion> moveLeftAnimation,
			CustomAnimation<TextureRegion> moveRightAnimation, CustomAnimation<TextureRegion> moveDownAnimation,
			CustomAnimation<TextureRegion> moveUpAnimation, MonsterProperties properties)
	{
		super(id, moveLeftAnimation, moveRightAnimation, moveDownAnimation, moveUpAnimation);
		this.properties = properties;
	}
	
	public MonsterProperties getProperties()
	{
		return properties;
	}

	public void gotHitBy(int damage)
	{
		properties.hp -= damage;	
	}

	public void setProperties(MonsterProperties properties)
	{
		this.properties = properties;		
	}

}
