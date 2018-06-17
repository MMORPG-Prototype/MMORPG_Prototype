package pl.mmorpg.protptype.server.desktop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import pl.mmorpg.prototype.clientservercommon.Settings;
import pl.mmorpg.prototype.server.GameServer;

@SpringBootApplication(scanBasePackages="pl.mmorpg.prototype")
public class ServerLauncher
{

	public static void main(String[] args)  
	{
		initializeSpring(args);
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Settings.WINDOW_WIDTH;
		config.height = Settings.WINDOW_HEIGHT; 
		new LwjglApplication(new GameServer(), config);
	}

	private static void initializeSpring(String[] args)
	{
		try {
			SpringApplication.run(ServerLauncher.class, args);
		}
		catch(Exception e){
			new MySqlLauncher().launch();
			SpringApplication.run(ServerLauncher.class, args);
		}
	}
}
