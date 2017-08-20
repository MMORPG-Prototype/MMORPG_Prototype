package pl.mmorpg.prototype.server.packetshandling;

import java.util.Map;

import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.exceptions.UserIsNotInGameException;

public class GameDataRetriever
{
	private final Map<Integer, UserInfo> loggedUsersKeyUserId;
	private final Map<Integer, User> authenticatedClientsKeyClientId;
	
	public GameDataRetriever(Map<Integer, UserInfo> loggedUsersKeyUserId, Map<Integer, User> authenticatedClientsKeyClientId)
	{
		this.loggedUsersKeyUserId = loggedUsersKeyUserId;
		this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
	}
	
	public UserCharacter getUserCharacterByConnectionId(int clientId)
	{
		if(!authenticatedClientsKeyClientId.containsKey(clientId))
			return null;
		User user = authenticatedClientsKeyClientId.get(clientId);
		Integer userId = user.getId();
		if(!loggedUsersKeyUserId.containsKey(userId))
			return null;
		return loggedUsersKeyUserId.get(userId).userCharacter;
	}
	
	public int getCharacterIdByConnectionId(int id)
    {
        UserCharacter userCharacter = getUserCharacterByConnectionId(id);
        if (userCharacter == null)
            throw new UserIsNotInGameException();

        int characterId = userCharacter.getId();
        return characterId;
    }
}
