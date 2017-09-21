package pl.mmorpg.prototype.server.database.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.server.database.entities.Quest;

public interface QuestRepository extends CrudRepository<Quest, Integer>
{
    Quest findByName(String questName);
}
