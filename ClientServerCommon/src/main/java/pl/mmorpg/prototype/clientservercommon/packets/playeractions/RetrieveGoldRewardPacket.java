package pl.mmorpg.prototype.clientservercommon.packets.playeractions;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class RetrieveGoldRewardPacket
{
    private String questName;
    private long retrieveItemDialogId;
}
