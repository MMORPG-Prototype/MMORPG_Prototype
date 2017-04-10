package pl.mmorpg.prototype.server.objects.monsters;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.objects.MovableGameObject;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class Monster extends MovableGameObject
{
	private static final BitmapFont font = Assets.getFont();
	protected final MonsterProperties properties;
	private Monster targetedMonster = null;
	private float hitTime = 1000.0f;
	protected PlayState linkedState;
	
	private boolean isAlive = true;
	
	private List<Monster> targetedBy = new LinkedList<>();

	public Monster(Texture lookout, long id, PlayState playState, MonsterProperties properties)
	{
		super(lookout, id, playState);
		linkedState = playState;
		font.setColor(new Color(1,0,0,1));
		this.properties = properties;
	}

	

	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		if(isTargetingAnotherMonster())
		{
			if(targetIsAlive())
				attackHandle(deltaTime);
			else
				targetedMonster = null;
		}
	
	}

	protected boolean isTargetingAnotherMonster()
	{
		return targetedMonster != null;
	}
	
	private boolean targetIsAlive()
	{
		return targetedMonster.isAlive;
	}

	
	private void attackHandle(float deltaTime)
	{
		hitTime += deltaTime;
		if(canAttackTarget())
		{
			hitTime = 0.0f;
			normalAttack(targetedMonster);
		}
	}


	private boolean canAttackTarget()
	{
		return hitTime >= properties.attackSpeed && 
				distance(targetedMonster) <= properties.attackRange;
	}

	private double distance(Monster targetedMonster)
	{
		float selfCenterX = getX() + getWidth()/2;
		float targetCenterX = targetedMonster.getX() + targetedMonster.getWidth()/2;
		float deltaX = targetCenterX - selfCenterX;
		float selfCenterY = getY() + getHeight()/2;
		float targetCenterY = targetedMonster.getY() + targetedMonster.getHeight()/2;
		float deltaY = targetCenterY - selfCenterY;
		double distance = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
		return distance;
	}



	private void normalAttack(Monster target)
	{
		int damage = DamageCalculator.getDamage(this, target);
		target.properties.hp -= damage;
		linkedState.send(PacketsMaker.makeDamagePacket(target.getId(), damage));
		if(target.properties.hp <= 0)
		{
			this.killed(target);
			target.die(this);
		}
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		super.render(batch);
		font.draw(batch, String.valueOf(properties.hp), getX() + 3, getY() + 40);
	}
	
	
	public void targetMonster(Monster target)
	{
		targetedMonster = target;
		target.isTargetedBy(this);
	}
	
	protected Monster getTargetedMonster()
	{
		return targetedMonster;
	}
	
	private void isTargetedBy(Monster source)
	{
		targetedBy.add(source);
	}

	
	private void die(Monster source)
	{
		linkedState.remove(getId());
		linkedState.send(PacketsMaker.makeRemovalPacket(getId()));
		isAlive = false;
	}
	
	protected void killed(Monster target)
	{
		targetedMonster = null;
	}
	
	public MonsterProperties getProperites()
	{
		return properties;
	}

}