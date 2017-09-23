package pl.mmorpg.prototype.server.database.repositories;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.server.database.entities.Quest;

public interface QuestRepository extends CrudRepository<Quest, Integer>
{
    Quest findByName(String questName);
    
    @Transactional
    default Quest findByNameFetchItemsReward(String questName)
    {
        Quest quest = findByName(questName);
        quest.getItemsReward().size();
        return quest;
    }
}
