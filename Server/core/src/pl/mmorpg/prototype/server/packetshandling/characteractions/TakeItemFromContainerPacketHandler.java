package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TakeItemFromContainerPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.containers.GameContainer;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlingHelper;
import pl.mmorpg.prototype.server.states.PlayState;

public class TakeItemFromContainerPacketHandler extends PacketHandlerBase<TakeItemFromContainerPacket>
{
	private Map<Integer, UserInfo> loggedUsersKeyUserId;
	private Map<Integer, User> authenticatedClientsKeyClientId;
	private PlayState playState;
	private Server server;

	public TakeItemFromContainerPacketHandler(Map<Integer, UserInfo> loggedUsersKeyUserId,
			Map<Integer, User> authenticatedClientsKeyClientId, Server server, PlayState playState)
	{
		this.loggedUsersKeyUserId = loggedUsersKeyUserId;
		this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
		this.server = server;
		this.playState = playState;
	}
	
	@Override
	public void handle(Connection connection, TakeItemFromContainerPacket packet)
	{
		long characterId = PacketHandlingHelper.getCharacterIdByConnectionId(connection.getID(), loggedUsersKeyUserId, authenticatedClientsKeyClientId);
		PlayerCharacter player = (PlayerCharacter)playState.getObject(characterId);
		GameContainer container = playState.getContainer(packet.getContainerId());
		Item item = container.removeItem(packet.getItemId());
		server.sendToAllTCP(PacketsMaker.makeContainerItemRemovalPacket(packet.getContainerId(), packet.getItemId()));
		player.addItem(item);
		connection.sendTCP(PacketsMaker.makeItemPacket(item));
		
		
	}

}
