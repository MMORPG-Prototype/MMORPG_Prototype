package pl.mmorpg.prototype.server.database.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.server.database.entities.QuickAccessBarConfigurationElement;

public interface QuickAccessBarConfigurationElementRepository
		extends CrudRepository<QuickAccessBarConfigurationElement, Integer>
{

}
