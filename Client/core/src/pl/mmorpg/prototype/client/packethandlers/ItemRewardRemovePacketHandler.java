package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemRewardRemovePacket;

public class ItemRewardRemovePacketHandler extends PacketHandlerAdapter<ItemRewardRemovePacket>
{
    private PlayState playState;

    public ItemRewardRemovePacketHandler(PlayState playState)
    {
        this.playState = playState;
    }
    
    @Override
    public void handle(ItemRewardRemovePacket packet)
    {
        playState.itemRewardRemovePacketReceived(packet);
    }

}
