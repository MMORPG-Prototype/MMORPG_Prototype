package pl.mmorpg.prototype.client.resources;

import java.io.IOException;
import java.util.Properties;

public class ConnectionProperties
{	
	private static final Properties properties = new Properties();
	
	static 
	{
		tryLoadingProperties();
	}

	private static void tryLoadingProperties()
	{
		try
		{
			properties.load(ConnectionProperties.class.getResourceAsStream("/connection.properties"));
		} catch (IOException e)
		{
			System.err.println("Cannot load maven properties!");
		}
	}
	
	public static String get(String name)
	{
		return properties.getProperty(name);
	}
	
	public static String getConnectionIp()
	{
		return properties.getProperty("server.ip");
	}
}
