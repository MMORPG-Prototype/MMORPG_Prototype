package pl.mmorpg.prototype.server.database.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.server.database.entities.QuickAccessBarConfigurationElement;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;

public interface QuickAccessBarConfigurationElementRepository
		extends CrudRepository<QuickAccessBarConfigurationElement, Integer>
{
	List<QuickAccessBarConfigurationElement> findByCharacter(UserCharacter character);
}
