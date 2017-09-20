package pl.mmorpg.prototype.clientservercommon.packets.playeractions;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class QuestRewardGoldRemovalPacket
{
    private int goldAmount;
    private long questFinishedDialogId;
}
