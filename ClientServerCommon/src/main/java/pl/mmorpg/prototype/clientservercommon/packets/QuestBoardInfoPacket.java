package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.packets.entities.QuestDataPacket;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;


@Registerable
@Data
public class QuestBoardInfoPacket
{
	private QuestDataPacket[] quests;
	
	private long questBoardId;
}
