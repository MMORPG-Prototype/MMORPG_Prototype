package pl.mmorpg.prototype.server.database.seeders;

import java.util.Collection;

import pl.mmorpg.prototype.server.database.HibernateUtil;

public abstract class TableSeeder
{

	public void seed()
	{
		Collection<Object> recordsToSeed = getRecords();
		HibernateUtil.makeOperation((session) ->
		{
			for (Object record : recordsToSeed)
				session.save(record);
		});
	}

	public abstract Collection<Object> getRecords();
}
