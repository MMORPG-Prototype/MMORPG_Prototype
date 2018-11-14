package pl.mmorpg.prototype.clientservercommon.packets.quest.event;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public abstract class EventPacket
{
	private String questName;
}
