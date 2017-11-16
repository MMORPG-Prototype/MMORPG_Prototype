package pl.mmorpg.prototype.server.database.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.server.database.entities.UserCharacterSpell;

public interface UserCharacterSpellRepository extends CrudRepository<UserCharacterSpell, Integer>
{

}
