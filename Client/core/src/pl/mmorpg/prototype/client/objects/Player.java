package pl.mmorpg.prototype.client.objects;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;

public class Player extends GameObject
{
	public Player(long id)
	{
		super(Assets.get("MainChar.png"), id);
	}

	@Override
	public void update(float deltaTime)
	{
	}

	@Override
	public String getIdentifier()
	{
		return ObjectsIdentifiers.PLAYER;
	}

}
