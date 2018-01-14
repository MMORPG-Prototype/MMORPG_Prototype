package pl.mmorpg.protptype.server.desktop;

import java.io.IOException;

public class MySqlLauncher
{
	private static final String MYSQL_LAUNCH_PATH = "D:\\Programy\\XAMPP\\xampp_start.exe";
	
	public void launch()
	{
		try
		{
			Runtime.getRuntime().exec(MYSQL_LAUNCH_PATH);
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
