package pl.mmorpg.prototype.clientservercommon.packets.quest.event;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.packets.quest.QuestStateInfoPacket;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class QuestAcceptedEventPacket extends EventPacket
{
	private QuestStateInfoPacket questStatePacket;
}
