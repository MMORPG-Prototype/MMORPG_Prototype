package pl.mmorpg.prototype.server.database.seeders;

import java.util.Collection;

import pl.mmorpg.prototype.server.database.HibernateUtil;

public abstract class TableSeeder
{
	private Collection<Object> recordsToSeed;

	public TableSeeder()
	{
		recordsToSeed = getRecords();
	}

	public void seed()
	{
		HibernateUtil.makeOperation((session) ->
		{
			for (Object record : recordsToSeed)
				session.save(record);
		});
	}

	public abstract Collection<Object> getRecords();
}
