package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.InventoryItemRepositionRequestPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.InventoryRepositionableItemsOwner;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlingHelper;
import pl.mmorpg.prototype.server.states.PlayState;

public class InventoryItemRepositionRequestPacketHandler extends PacketHandlerBase<InventoryItemRepositionRequestPacket>
{
	private Map<Integer, User> authenticatedClientsKeyClientId;
	private Map<Integer, UserInfo> loggedUsersKeyUserId;
	private PlayState playState;

	public InventoryItemRepositionRequestPacketHandler(Map<Integer, UserInfo> loggedUsersKeyUserId,
			Map<Integer, User> authenticatedClientsKeyClientId, PlayState playState)
	{
		this.loggedUsersKeyUserId = loggedUsersKeyUserId;
		this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
		this.playState = playState;
	}

	@Override
	public void handle(Connection connection, InventoryItemRepositionRequestPacket packet)
	{
		int characterId = PacketHandlingHelper.getCharacterIdByConnectionId(connection.getID(), loggedUsersKeyUserId,
				authenticatedClientsKeyClientId);
		InventoryRepositionableItemsOwner playerCharacter = (InventoryRepositionableItemsOwner)playState.getObject(characterId);
		Item item = playerCharacter.getItem(packet.getTargetItemId());
		InventoryPosition desiredPosition = new InventoryPosition(packet.getInventoryPageNumber(), packet.getInventoryPageX(), packet.getInventoryPageY());
		if(!playerCharacter.hasItemInPosition(desiredPosition))
		{
			connection.sendTCP(PacketsMaker.makeInventoryItemRepositionPacket(item.getInventoryPosition(), desiredPosition));
			item.setInventoryPosition(desiredPosition);
		}
		else
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("Not implemented yet"));
			
		
	}

}
