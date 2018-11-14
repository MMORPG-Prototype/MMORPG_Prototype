package pl.mmorpg.prototype.clientservercommon.packets.quest.event;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class NpcDialogEventPacket extends EventPacket
{
	private long npcId;
	private String speech;
	private String[] possibleAnswers;
}
