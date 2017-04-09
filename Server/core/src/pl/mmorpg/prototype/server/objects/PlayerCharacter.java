package pl.mmorpg.prototype.server.objects;

import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.resources.Assets;

public class PlayerCharacter extends MovableGameObject
{
	private UserCharacter userCharacter;

	public PlayerCharacter(UserCharacter userCharacter, PacketsSender packetsSender)
	{
		super(Assets.get("MainChar.png"), userCharacter.getId(), packetsSender);
		this.userCharacter = userCharacter;
		setPacketSendingInterval(0.0f);
	}

	@Override
	public String getIdentifier()
	{
		return ObjectsIdentifiers.PLAYER;
	}
}
