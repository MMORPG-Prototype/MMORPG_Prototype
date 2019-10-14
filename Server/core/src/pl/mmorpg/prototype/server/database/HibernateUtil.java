package pl.mmorpg.prototype.server.database;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;
import pl.mmorpg.prototype.server.exceptions.GameException;

public class HibernateUtil
{
	private static SessionFactory sessionFactory = null;

	private static void initialize()
	{
		try
		{
			Configuration config = createConfig();
			sessionFactory = config.buildSessionFactory();
		} catch (Throwable ex)
		{
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

    private static Configuration createConfig()
    {
        Configuration config = new Configuration();
        if(Settings.HIBERNATE_CONFIG_INTERNAL_PATH)
        	config.configure(Settings.HIBERNATE_CONFIG_FILE_PATH);
        else
        	config.configure(new File(Settings.HIBERNATE_CONFIG_FILE_PATH));
        registerEntityTypes(config);
        return config;
    }
	
	public static void recreateDatabase()
	{
		// try
		// {
		// 	String filePath = "hibernateRecreateDatabase.cfg.xml";
		// 	String s = new String(HibernateUtil.class.getResourceAsStream(filePath).readAllBytes());
		// } catch (IOException e)
		// {
		// 	throw new GameException("Could not read hibernate config file", e);
		// }

		Configuration config = new Configuration();
		Properties properties = new Properties();
		config.configure("hibernateRecreateDatabase.cfg.xml");
		properties.setProperty("hibernate.connection.username", "root");
		properties.setProperty("hibernate.connection.password", "root");
		config.addProperties(properties);
		registerEntityTypes(config);
		config.buildSessionFactory().openSession().close();
	}

	private static void registerEntityTypes(Configuration config)
	{
		Reflections reflections = new Reflections("pl.mmorpg.prototype.data.entities");
		Set<Class<?>> entityTypes = reflections.getTypesAnnotatedWith(Table.class);
		for (Class<?> type : entityTypes)
			config = config.addAnnotatedClass(type);
	}

	public static SessionFactory getSessionFactory()
	{
		if(sessionFactory == null)
			initialize();
		return sessionFactory;
	}

	public static Session openSession()
	{
		if(sessionFactory == null)
			initialize();
		return sessionFactory.openSession();
	}

	public static void makeOperation(SessionAction sessionAction)
	{
		if(sessionFactory == null)
			initialize();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		sessionAction.make(session);
		session.getTransaction().commit();
		session.close();
	}
}
