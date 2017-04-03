package pl.mmorpg.prototype.clientservercommon.packets.entities;

import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class CharacterItemDataPacket
{
	public Integer id;
	public String name;
	public String type;
	public Integer characterId;
}
