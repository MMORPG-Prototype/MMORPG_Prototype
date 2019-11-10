package pl.mmorpg.prototype.server.database.seeders;

import com.esotericsoftware.minlog.Log;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class DatabaseSeeder
{
	public void seed()
	{
		Collection<TableSeeder> seeders = getAllSeeders();
		assertAllSeedersAreUsed(seeders);
		seeders.forEach(TableSeeder::seed);
	}

	private Collection<TableSeeder> getAllSeeders()
	{
		return Arrays.asList(new UsersTableSeeder(),
				new CharacterSpellsTableSeeder(),
				new CharactersTableSeeder(),
				new CharacterItemsTableSeeder(),
				new QuestTableSeeder(),
				new CharactersQuestsTableSeeder());
	}

	private void assertAllSeedersAreUsed(Collection<TableSeeder> seeders)
	{
		Reflections reflections = new Reflections(TableSeeder.class.getPackage().getName());
		Set<Class<? extends TableSeeder>> subTypes = reflections.getSubTypesOf(TableSeeder.class);
		if (subTypes.size() != seeders.size())
			Log.warn("Used seeders: " + seeders.size() + ", but there are total " + subTypes.size());
	}
}
