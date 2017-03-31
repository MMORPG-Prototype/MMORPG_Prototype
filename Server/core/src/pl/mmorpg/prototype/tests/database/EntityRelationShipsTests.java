package pl.mmorpg.prototype.tests.database;

import org.junit.Test;

import pl.mmorpg.prototype.server.database.HibernateUtil;

public class EntityRelationShipsTests
{
	@Test
	public void UserCharacterOneToManyTest()
	{
		HibernateUtil.makeOperation((session) ->
		{
		});
	}
}
