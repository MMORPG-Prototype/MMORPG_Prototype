package pl.mmorpg.prototype.server.commandUtils.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.reflections.Reflections;

import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.server.database.seeders.CharacterItemsTableSeeder;
import pl.mmorpg.prototype.server.database.seeders.CharacterSpellsTableSeeder;
import pl.mmorpg.prototype.server.database.seeders.CharactersQuestsTableSeeder;
import pl.mmorpg.prototype.server.database.seeders.CharactersTableSeeder;
import pl.mmorpg.prototype.server.database.seeders.QuestTableSeeder;
import pl.mmorpg.prototype.server.database.seeders.TableSeeder;
import pl.mmorpg.prototype.server.database.seeders.UsersTableSeeder;

public class DatabaseSeedCommand implements CommandAction
{

	@Override
	public void run(String args)
	{
		Collection<TableSeeder> seeders = getAllSeeders();
		assertAllSeedersAreUsed(seeders);
		seeders.forEach( seeder -> seeder.seed());
	}

	private void assertAllSeedersAreUsed(Collection<TableSeeder> seeders)
	{
		Reflections reflections = new Reflections(TableSeeder.class.getPackage().getName());
		Set<Class<? extends TableSeeder>> subTypes = reflections.getSubTypesOf(TableSeeder.class);
		if(subTypes.size() != seeders.size())
			Log.warn("Used seeders: " + seeders.size() + ", but there are total " + subTypes.size());
	}

	private Collection<TableSeeder> getAllSeeders()
	{
		Collection<TableSeeder> seeders = new ArrayList<>();
		seeders.add(new UsersTableSeeder());
		seeders.add(new CharacterSpellsTableSeeder());
		seeders.add(new CharactersTableSeeder());
		seeders.add(new CharacterItemsTableSeeder());
		seeders.add(new QuestTableSeeder());
		seeders.add(new CharactersQuestsTableSeeder());
		return seeders;
	}

	@Override
	public String getDescription()
	{
		return "Seed database with implemented seeders (to use particulary for empty database)";
	}

	@Override
	public String getName()
	{
		return "database-seed";
	}
}
