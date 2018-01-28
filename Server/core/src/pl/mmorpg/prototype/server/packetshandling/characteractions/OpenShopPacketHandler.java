package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.OpenShopPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.objects.monsters.npcs.Shop;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public class OpenShopPacketHandler extends PacketHandlerBase<OpenShopPacket>
{
	private PlayState playState;

	public OpenShopPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}

	@Override
	public void handle(Connection connection, OpenShopPacket packet)
	{
		Shop shop = (Shop) playState.getObject(packet.getShopNpcId());
		connection.sendTCP(PacketsMaker.makeShopItemsPacket(shop.getAvailableItems(), packet.getShopNpcId()));
	}

}
