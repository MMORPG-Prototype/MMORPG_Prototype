package pl.mmorpg.prototype.server.packetshandling;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.data.entities.QuestTaskWrapper;
import pl.mmorpg.prototype.data.entities.User;
import pl.mmorpg.prototype.data.entities.Character;
import pl.mmorpg.prototype.data.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.exceptions.UserIsNotInGameException;
import pl.mmorpg.prototype.server.exceptions.UserNotConnectedException;
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
	
	public Character getUserCharacterByConnectionId(int connectionId)
	{
		User user = getUserByConnectionId(connectionId);
		Integer userId = user.getId();
		if(!loggedUsersKeyUserId.containsKey(userId))
			return null;
		return loggedUsersKeyUserId.get(userId).userCharacter;
	}

	public User getUserByConnectionId(int connectionId)
	{
		if(!authenticatedClientsKeyClientId.containsKey(connectionId))
			throw new UserNotConnectedException(connectionId);
		return authenticatedClientsKeyClientId.get(connectionId);
	}
	
	public int getCharacterIdByConnectionId(int id)
    {
        Character userCharacter = getUserCharacterByConnectionId(id);
        if (userCharacter == null)
            throw new UserIsNotInGameException();

		return userCharacter.getId();
    }
	
	public Collection<CharactersQuests> getCharacterQuestsByConnectionId(int id)
	{
	    Character userCharacter = getUserCharacterByConnectionId(id);
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
				.map(task -> (QuestTask) task)
				.collect(Collectors.toList());
	}
}
