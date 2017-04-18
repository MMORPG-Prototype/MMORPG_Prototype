package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class ContainerContentPacket
{
	private CharacterItemDataPacket[] contentItems;
}
