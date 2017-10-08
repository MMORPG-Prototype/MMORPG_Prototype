package pl.mmorpg.prototype.clientservercommon.packets.playeractions;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class NpcContinueDialogPacket
{
	private long npcId;
	private String speech;
	private String[] possibleAnswers;
}
