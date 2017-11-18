package pl.mmorpg.prototype.server.database.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.server.database.entities.SpellQuickAccessBarConfigurationElement;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;

public interface SpellQuickAccessBarConfigurationElementRepository
		extends CrudRepository<SpellQuickAccessBarConfigurationElement, Integer>
{
	List<SpellQuickAccessBarConfigurationElement> findByCharacter(UserCharacter character);
}
