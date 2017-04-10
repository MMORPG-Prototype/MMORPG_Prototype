package pl.mmorpg.prototype.clientservercommon.packets;

import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class PlayerCreationPacket extends MonsterCreationPacket
{
	public UserCharacterDataPacket data;
}
