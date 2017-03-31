package pl.mmorpg.prototype.server.database;

import java.util.Set;

import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;



public class HibernateUtil
{
	private static final SessionFactory sessionFactory;
	static
	{
		try
		{
			Configuration config = new Configuration().configure("hibernate.cfg.xml");
			registerEntityTypes(config);
			sessionFactory = config.buildSessionFactory();
		} catch (Throwable ex)
		{
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	private static void registerEntityTypes(Configuration config)
	{
		Reflections reflections = new Reflections("pl.mmorpg.prototype.server.database.entities");
		Set<Class<?>> entityTypes = reflections.getTypesAnnotatedWith(Table.class);
		for (Class<?> type : entityTypes)
			config = config.addAnnotatedClass(type);
	}

	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	public static Session openSession()
	{
		return sessionFactory.openSession();
	}

	public static void makeOperation(SessionAction sessionAction)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		sessionAction.make(session);
		session.getTransaction().commit();
		session.close();
	}
}