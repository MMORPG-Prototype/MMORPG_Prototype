package pl.mmorpg.prototype.clientservercommon.packets.playeractions;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class NpcStartDialogPacket
{
	private long npcId;
	private String speech;
	private String[] possibleAnswers;
}
