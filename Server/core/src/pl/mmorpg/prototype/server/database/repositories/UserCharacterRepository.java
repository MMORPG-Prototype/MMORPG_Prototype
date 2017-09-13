package pl.mmorpg.prototype.server.database.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import pl.mmorpg.prototype.server.database.entities.UserCharacter;

public interface UserCharacterRepository extends CrudRepository<UserCharacter, Integer> 
{
	UserCharacter findByNickname(String nickname);

	List<UserCharacter> findByUser_Username(String username);
	
	@Transactional
	default UserCharacter findOneAndFetchQuickAccessBarConfig(Integer id)
	{
		UserCharacter result = findOne(id);
		result.getQuickAccessBarConfig().size();
		return result;
	}

}
