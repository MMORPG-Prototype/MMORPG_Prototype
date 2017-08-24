package pl.mmorpg.prototype.server.packetshandling;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.CharacterChangePacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.CharacterDatabaseSaver;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.states.PlayState;

public class CharacterChangePacketHandler extends PacketHandlerBase<CharacterChangePacket>
{
	private Map<Integer, UserInfo> loggedUsersKeyUserId;
	private Map<Integer, User> authenticatedClientsKeyClientId;
	private PlayState playState;
	private Server server;

	public CharacterChangePacketHandler(Map<Integer, UserInfo> loggedUsersKeyUserId,
			Map<Integer, User> authenticatedClientsKeyClientId, PlayState playState, Server server)
	{
		this.loggedUsersKeyUserId = loggedUsersKeyUserId;
		this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
		this.playState = playState;
		this.server = server;
	}

	@Override
	public void handle(Connection connection, CharacterChangePacket packet)
	{
		int userId = authenticatedClientsKeyClientId.get(connection.getID()).getId();
		UserInfo userInfo = loggedUsersKeyUserId.get(userId);
		Integer characterId = userInfo.userCharacter.getId();
		if(playState.has(characterId))
		{
			PlayerCharacter removedCharacter = (PlayerCharacter)playState.remove(characterId);
			new CharacterDatabaseSaver().save(removedCharacter);
		} 
		userInfo.userCharacter = null;
		server.sendToAllExceptTCP(connection.getID(), PacketsMaker.makeRemovalPacket(characterId));
	}

}
