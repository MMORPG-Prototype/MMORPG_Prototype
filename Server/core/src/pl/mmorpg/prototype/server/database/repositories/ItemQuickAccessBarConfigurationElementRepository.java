package pl.mmorpg.prototype.server.database.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.server.database.entities.ItemQuickAccessBarConfigurationElement;
import pl.mmorpg.prototype.server.database.entities.Character;

public interface ItemQuickAccessBarConfigurationElementRepository
		extends CrudRepository<ItemQuickAccessBarConfigurationElement, Integer>
{
	List<ItemQuickAccessBarConfigurationElement> findByCharacter(Character character);
}
