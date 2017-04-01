package pl.mmorpg.prototype.server.objects;

import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.server.resources.Assets;

public class Player extends MovableGameObject
{
	public Player(long id)
	{
		super(Assets.get("MainChar.png"), id);
		setPosition(100, 100);
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
