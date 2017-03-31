package pl.mmorpg.prototype.server.database;

import org.hibernate.Session;

@FunctionalInterface
public interface SessionAction
{
	void make(Session session);
}
