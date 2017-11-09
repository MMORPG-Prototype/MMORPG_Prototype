package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TakeItemFromContainerPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.containers.GameContainer;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public class TakeItemFromContainerPacketHandler extends PacketHandlerBase<TakeItemFromContainerPacket>
{
	private PlayState playState;
	private Server server;
	private GameDataRetriever gameData;

	public TakeItemFromContainerPacketHandler(GameDataRetriever gameData, Server server, PlayState playState)
	{
		this.gameData = gameData;
		this.server = server;
		this.playState = playState;
	}
	
	@Override
	public void handle(Connection connection, TakeItemFromContainerPacket packet)
	{
		long characterId = gameData.getCharacterIdByConnectionId(connection.getID());
		PlayerCharacter player = (PlayerCharacter)playState.getObject(characterId);
		GameContainer container = playState.getContainer(packet.getContainerId());
		Item item = container.removeItem(packet.getItemId());
		server.sendToAllTCP(PacketsMaker.makeContainerItemRemovalPacket(packet.getContainerId(), packet.getItemId()));
		InventoryPosition desiredPosition = new InventoryPosition(packet.getDesiredInventoryPage(), packet.getDesiredInventoryX(), packet.getDesiredInventoryY());
		item.setInventoryPosition(desiredPosition);		
		player.addItemAllowStacking(item);
		connection.sendTCP(PacketsMaker.makeItemPacket(item));
	}

}
