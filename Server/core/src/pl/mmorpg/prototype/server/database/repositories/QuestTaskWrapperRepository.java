package pl.mmorpg.prototype.server.database.repositories;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.server.database.entities.QuestTaskWrapper;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;

public interface QuestTaskWrapperRepository extends CrudRepository<QuestTaskWrapper, Integer>
{
    @Transactional
    Collection<QuestTaskWrapper> findByCharactersQuests(Collection<CharactersQuests> quests);
}
