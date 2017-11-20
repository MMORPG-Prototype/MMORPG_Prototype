package pl.mmorpg.prototype.server.database.repositories;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.server.database.entities.QuestTaskWrapper;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;

public interface QuestTaskWrapperRepository extends CrudRepository<QuestTaskWrapper, Integer>
{
    Collection<QuestTaskWrapper> findByCharactersQuests(Collection<CharactersQuests> quests);
}
