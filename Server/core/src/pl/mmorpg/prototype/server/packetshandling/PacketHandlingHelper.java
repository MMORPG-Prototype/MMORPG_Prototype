package pl.mmorpg.prototype.server.packetshandling;

import java.util.Map;

import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;

public class PacketHandlingHelper
{	
	public static UserCharacter getUserCharacterByConnectionId(int clientId,
			Map<Integer, UserInfo> loggedUsersKeyUserId,
			Map<Integer, User> authenticatedClientsKeyClientId)
	{
		if(!authenticatedClientsKeyClientId.containsKey(clientId))
			return null;
		User user = authenticatedClientsKeyClientId.get(clientId);
		Integer userId = user.getId();
		if(!loggedUsersKeyUserId.containsKey(userId))
			return null;
		return loggedUsersKeyUserId.get(userId).userCharacter;
	}
}
