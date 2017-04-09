package pl.mmorpg.prototype.server.objects.monsters;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
			attackHandle(deltaTime);	
	}
	
	private boolean isTargetingAnotherMonster()
	{
		return targetedMonster != null;
	}

	
	private void attackHandle(float deltaTime)
	{
		hitTime += deltaTime;
		if(hitTime >= properties.getAttackSpeed())
		{
			hitTime = 0.0f;
			normalAttack(targetedMonster);
		}
	}

	private void normalAttack(Monster target)
	{
		int damage = DamageCalculator.getDamage(this, target);
		target.properties.hp -= damage;
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
	
	private void isTargetedBy(Monster source)
	{
		targetedBy.add(source);
	}
	
	
	
	private void die(Monster source)
	{
		linkedState.remove(getId());
		linkedState.send(PacketsMaker.makeRemovalPacket(getId()));
	}
	
	protected void killed(Monster target)
	{
		targetedMonster = null;
		System.out.println(this + " killed " + target);
	}
	
	public MonsterProperties getProperites()
	{
		return properties;
	}

}