package pl.mmorpg.prototype.server.helpers;

import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationReplyPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.managers.UserCharacterTableManager;

public class CharacterCreator
{

	public static CharacterCreationReplyPacket tryCreatingCharacter(CharacterCreationPacket packet, int userId)
	{
		CharacterCreationReplyPacket replyPacket = new CharacterCreationReplyPacket();
		if(!UserCharacterTableManager.hasUserCharacter(packet.getNickname()))
		{
			UserCharacter userCharacter = UserCharacterTableManager.createCharacter(packet.getNickname(), userId);
			replyPacket.setCharacter(PacketsMaker.makeCharacterPacket(userCharacter));
			replyPacket.setCreated(true);
		}
		else
			replyPacket.setErrorMessage("There is already player with this nickname!");
			
		return replyPacket;
	}
}
