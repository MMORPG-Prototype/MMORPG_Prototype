package pl.mmorpg.prototype.clientservercommon.packets;

import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class MonsterCreationPacket extends ObjectCreationPacket
{
	public MonsterProperties properties;
}
