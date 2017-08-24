package pl.mmorpg.prototype.server.database.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.server.database.entities.UserCharacter;

public interface UserCharacterRepository extends CrudRepository<UserCharacter, Integer> 
{
	UserCharacter findByNickname(String nickname);

	List<UserCharacter> findByUser_Username(String username);
}
