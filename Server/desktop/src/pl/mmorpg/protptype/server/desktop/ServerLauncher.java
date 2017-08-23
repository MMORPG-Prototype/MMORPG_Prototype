package pl.mmorpg.protptype.server.desktop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import pl.mmorpg.prototype.clientservercommon.Settings;
import pl.mmorpg.prototype.server.GameServer;

@SpringBootApplication(scanBasePackages="pl.mmorpg.prototype")
public class ServerLauncher implements CommandLineRunner
{
	public static void main(String[] args) 
	{
		SpringApplication.run(ServerLauncher.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception
	{

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Settings.GAME_WIDTH;
		config.height = Settings.GAME_HEIGHT; 
		new LwjglApplication(new GameServer(), config);

	}
}
