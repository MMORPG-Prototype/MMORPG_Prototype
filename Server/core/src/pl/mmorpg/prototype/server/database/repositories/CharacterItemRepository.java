package pl.mmorpg.prototype.server.database.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;

public interface CharacterItemRepository extends CrudRepository<CharacterItem, Integer>
{
	List<CharacterItem> findByCharacter(UserCharacter character);
	
	List<CharacterItem> findByCharacter_Id(Integer id);
}
