package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.ItemUsagePacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.exceptions.OutOfStockExcpetion;
import pl.mmorpg.prototype.server.exceptions.PlayerUsingItemNotFoundException;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlingHelper;
import pl.mmorpg.prototype.server.states.PlayState;

public class ItemUsagePacketHandler extends PacketHandlerBase<ItemUsagePacket>
{
	private Map<Integer, User> authenticatedClientsKeyClientId;
	private Map<Integer, UserInfo> loggedUsersKeyUserId;
	private PlayState playState;

	public ItemUsagePacketHandler(Map<Integer, UserInfo> loggedUsersKeyUserId,
			Map<Integer, User> authenticatedClientsKeyClientId, PlayState playState, Server server)
	{
		this.loggedUsersKeyUserId = loggedUsersKeyUserId;
		this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
		this.playState = playState;
	}
	
	@Override
	public void handle(Connection connection, ItemUsagePacket packet)
	{
		int characterId = PacketHandlingHelper.getCharacterIdByConnectionId(connection.getID(), loggedUsersKeyUserId, authenticatedClientsKeyClientId);
		PlayerCharacter itemUser = (PlayerCharacter)playState.getObject(characterId);
		if(itemUser == null)
			throw new PlayerUsingItemNotFoundException();
		Monster target = (Monster)playState.getObject(packet.getTargetId());
		try
		{
			itemUser.useItem(packet.getItemId(), target, (PacketsSender)playState);
			connection.sendTCP(packet);
		}
		catch(OutOfStockExcpetion e)
		{
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("Your item stack was depleted"));
		}
	}

}
