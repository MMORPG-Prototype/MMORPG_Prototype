package pl.mmorpg.prototype.client.objects;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.packets.movement.Directions;

@Data
public class MoveInfo
{
	private int moveDirection;
	private float currentMovementSpeed;
	
	public MoveInfo reset()
	{
		currentMovementSpeed = 0.0f;
		moveDirection = Directions.NONE;
		return this;
	}

	public MoveInfo withoutSliding()
	{
		currentMovementSpeed = 1.0f;
		return this;
	}
}
