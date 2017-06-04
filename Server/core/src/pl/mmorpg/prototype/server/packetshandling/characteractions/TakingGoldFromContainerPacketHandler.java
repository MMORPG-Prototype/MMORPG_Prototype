package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TakingGoldFromContainerPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.containers.GameContainer;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlingHelper;
import pl.mmorpg.prototype.server.states.PlayState;

public class TakingGoldFromContainerPacketHandler extends PacketHandlerBase<TakingGoldFromContainerPacket>
{
	private Map<Integer, UserInfo> loggedUsersKeyUserId;
	private Map<Integer, User> authenticatedClientsKeyClientId;
	private PlayState playState;
	private Server server;

	public TakingGoldFromContainerPacketHandler(Map<Integer, UserInfo> loggedUsersKeyUserId,
			Map<Integer, User> authenticatedClientsKeyClientId, Server server, PlayState playState)
	{
		this.loggedUsersKeyUserId = loggedUsersKeyUserId;
		this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
		this.server = server;
		this.playState = playState;
	}
	
	
	@Override
	public void handle(Connection connection, TakingGoldFromContainerPacket packet)
	{
		long characterId = PacketHandlingHelper.getCharacterIdByConnectionId(connection.getID(), loggedUsersKeyUserId, authenticatedClientsKeyClientId);
		PlayerCharacter player = (PlayerCharacter)playState.getObject(characterId);
		GameContainer container = playState.getContainer(packet.getContainerId());
		int gold = container.getGoldAmount();
		if(gold > 0)
		{
			connection.sendTCP(PacketsMaker.makeGoldReceivePacket(gold));
			server.sendToAllTCP(PacketsMaker.makeContainerGoldRemovalPacket(packet.getContainerId(), gold));
			container.setGoldAmount(0);
			player.addGold(gold);
		}
		else
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("There is no gold left in this container"));
		
		
	}

}
