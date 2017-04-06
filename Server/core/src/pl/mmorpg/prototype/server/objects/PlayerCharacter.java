package pl.mmorpg.prototype.server.objects;

import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.resources.Assets;

public class PlayerCharacter extends MovableGameObject
{
	private UserCharacter userCharacter;

	public PlayerCharacter(UserCharacter userCharacter)
	{
		super(Assets.get("MainChar.png"), userCharacter.getId());
		this.userCharacter = userCharacter;
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
