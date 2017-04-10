package pl.mmorpg.prototype.server.objects;

import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.PlayerPropertiesBuilder;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public class PlayerCharacter extends Monster
{
	private UserCharacter userCharacter;

	public PlayerCharacter(UserCharacter userCharacter, PlayState linkedState)
	{
		super(Assets.get("MainChar.png"), userCharacter.getId(), linkedState,
				new PlayerPropertiesBuilder(PacketsMaker.makeCharacterPacket(userCharacter)).build());
		this.userCharacter = userCharacter;
		setPacketSendingInterval(0.0f);
	}

	@Override
	public String getIdentifier()
	{
		return ObjectsIdentifiers.PLAYER;
	}
	
	@Override
	protected void killed(Monster target)
	{
		linkedState.playerKilled(this, target);
		userCharacter.setExperience(userCharacter.getExperience() + target.getProperites().experienceGain);
		super.killed(target);
	}
}
