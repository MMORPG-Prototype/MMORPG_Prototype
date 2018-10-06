package pl.mmorpg.prototype.clientservercommon.packets.quest;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.packets.ItemRewardPacket;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class QuestFinishedRewardPacket
{
    private String questName;
    
    private int goldReward;
    
    private ItemRewardPacket[] itemReward;
}
