package pl.mmorpg.prototype.server.objects.monsters.spells.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.MovableGameObject;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.states.GameObjectsContainer;

public abstract class ThrowableObject extends MovableGameObject
{
	private Monster target = null;
	private GameObjectsContainer linkedContainer;
	private PacketsSender packetSender;
	private final Vector2 flyingVector = new Vector2();

	public ThrowableObject(Texture lookout, long id, GameObjectsContainer linkedContainer, PacketsSender packetSender)
	{
		super(lookout, id, packetSender);
		this.linkedContainer = linkedContainer;
		this.packetSender = packetSender;
		this.setCollideable(false);
	}

	@Override
	public void update(float deltaTime)
	{
		if (!hasTarget() || !targetIsAlive())
		{
			removeItself(linkedContainer, packetSender);
			return;
		}

		chaseTarget(deltaTime, target);
		if (isNearTarget(target))
		{
			removeItself(linkedContainer, packetSender);
			onHit(target);
			return;
		}
		super.update(deltaTime);
	}

	private boolean targetIsAlive()
	{
		return target.isInGame();
	}

	public abstract void onHit(Monster target);

	private boolean hasTarget()
	{
		return target != null;
	}

	public void setTarget(Monster target)
	{
		this.target = target;
	}

	@Override
	protected void chaseTarget(float deltaTime, Monster target)
	{
		flyingVector.x = getX() - target.getX();
		flyingVector.y = getY() - target.getY();
		float angle = flyingVector.angle();
		flyingVector.set(getMoveSpeed() / 70, getMoveSpeed() / 70);
		flyingVector.setAngle(angle);
		setPosition(getX() - flyingVector.x, getY() - flyingVector.y);
		sendRepositionPacket();
	}

}
