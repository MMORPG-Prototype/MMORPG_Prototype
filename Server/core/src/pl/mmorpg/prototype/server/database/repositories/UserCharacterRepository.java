package pl.mmorpg.prototype.server.database.repositories;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;

public interface UserCharacterRepository extends CrudRepository<UserCharacter, Integer> 
{
	UserCharacter findByNickname(String nickname);

	List<UserCharacter> findByUser_Username(String username);
	
	@Transactional
	default UserCharacter findOneAndFetchEverythingRelated(Integer id)
	{
		UserCharacter result = findOne(id);
		result.getItemQuickAccessBarConfig().size();
		result.getSpellQuickAccessBarConfig().size();
		result.getSpells().size();
		Collection<CharactersQuests> quests = result.getQuests();
        quests.forEach(q -> q.getQuestTasks().size());
        quests.forEach(q -> q.getQuest().getId());
        quests.forEach(q -> q.getQuest().getItemsReward().size());
        quests.forEach(q -> q.getItemsReward().size());
		return result;
	}

}
