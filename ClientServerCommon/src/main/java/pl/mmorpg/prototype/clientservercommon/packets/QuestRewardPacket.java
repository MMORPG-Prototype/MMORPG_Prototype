package pl.mmorpg.prototype.clientservercommon.packets;

import java.util.List;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class QuestRewardPacket
{
    private String questName;
    
    private int goldReward;
    
    private List<ItemRewardPacket> itemReward;
}
