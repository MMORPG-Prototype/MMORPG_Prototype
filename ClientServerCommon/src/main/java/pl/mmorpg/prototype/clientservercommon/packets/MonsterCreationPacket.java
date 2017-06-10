package pl.mmorpg.prototype.clientservercommon.packets;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class MonsterCreationPacket extends ObjectCreationPacket
{
	public MonsterProperties properties;
}
