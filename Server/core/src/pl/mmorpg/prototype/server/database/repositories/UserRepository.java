package pl.mmorpg.prototype.server.database.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.server.database.entities.User;

public interface UserRepository extends CrudRepository<User, Integer>
{
	User findByUsername(String username);
}
