package pl.mmorpg.prototype.server.packetshandling;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.RegisterationPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.states.PlayState;

public class LogoutPacketHandler extends PacketHandlerBase<RegisterationPacket>
{
	private Map<Integer, UserInfo> loggedUsersKeyUserId;
	private Map<Integer, User> authenticatedClientsKeyClientId;
	private PlayState playState;
	private Server server;

	public LogoutPacketHandler(Map<Integer, UserInfo> loggedUsersKeyUserId,
			Map<Integer, User> authenticatedClientsKeyClientId, 
			Server server, 
			PlayState playState)
	{
		this.loggedUsersKeyUserId = loggedUsersKeyUserId;
		this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
		this.server = server;
		this.playState = playState;
	}
	
	@Override
	public void handle(Connection connection, RegisterationPacket packet)
	{
		userLoggedOut(connection);
	}

	private void userLoggedOut(Connection connection)
	{
		User user = authenticatedClientsKeyClientId.remove(connection.getID());
		UserCharacter userCharacter = loggedUsersKeyUserId.remove(user.getId()).userCharacter;
		if (userCharacter != null)
		{
			int characterId = userCharacter.getId();
			if (playState.has(characterId))
			{
				playState.remove(characterId);
				server.sendToAllTCP(PacketsMaker.makeRemovalPacket(characterId));
			}
		}
	}
}
