package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class ItemRewardPacket
{
    private String itemIdentifier;
    
    private int numberOfItems;
}
