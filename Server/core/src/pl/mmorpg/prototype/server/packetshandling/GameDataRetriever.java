package pl.mmorpg.prototype.server.packetshandling;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.database.entities.QuestTaskWrapper;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.exceptions.UserIsNotInGameException;
import pl.mmorpg.prototype.server.quests.QuestTask;

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
	
	public Collection<CharactersQuests> getCharacterQuestsByConnectionId(int id)
	{
	    UserCharacter userCharacter = getUserCharacterByConnectionId(id);
        if (userCharacter == null)
            throw new UserIsNotInGameException();
        return userCharacter.getQuests();
	}
	
	public Collection<QuestTask> getCharacterQuestTasksByConnectionId(int id)
    {
        Collection<CharactersQuests> quests = getCharacterQuestsByConnectionId(id);
        return getQuestTasks(quests);
    }
	
	private Collection<QuestTask> getQuestTasks(Collection<CharactersQuests> quests)
    {
        return quests.stream()
            .map(CharactersQuests::getQuestTasks)
            .flatMap(Collection::stream)
            .map(QuestTaskWrapper::getQuestTask)
            .collect(Collectors.toList());
    }
}
