package pl.mmorpg.prototype.server.database.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.server.database.entities.components.keys.CharactersQuestsKey;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;

public interface CharactersQuestsRepository extends CrudRepository<CharactersQuests, CharactersQuestsKey>
{

}
