package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class NpcConversationAnwserChoosenPacket
{
	private long npcId;
	private String anwser;
}
