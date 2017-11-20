package pl.mmorpg.prototype.server.database.repositories;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.server.database.entities.Character;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;

public interface CharacterRepository extends CrudRepository<Character, Integer> 
{
	Character findByNickname(String nickname);

	List<Character> findByUser_Username(String username);
	
	@Transactional
	default Character findOneAndFetchEverythingRelated(Integer id)
	{
		Character result = findOne(id);
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
