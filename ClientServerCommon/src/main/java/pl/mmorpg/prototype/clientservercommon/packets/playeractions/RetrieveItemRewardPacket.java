package pl.mmorpg.prototype.clientservercommon.packets.playeractions;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class RetrieveItemRewardPacket
{
    private String itemIdentifier;
    private int numberOfItems;
    private int desiredInventoryX;
    private int desiredInventoryY;
    private int desiredInventoryPage;
    private String questName;
    private long retrieveItemDialogId;
}
