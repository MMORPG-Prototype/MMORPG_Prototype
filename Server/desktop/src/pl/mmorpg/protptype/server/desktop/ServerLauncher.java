package pl.mmorpg.protptype.server.desktop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import pl.mmorpg.prototype.clientservercommon.Settings;
import pl.mmorpg.prototype.server.quests.events.GameServer;

@SpringBootApplication(scanBasePackages="pl.mmorpg.prototype")
public class ServerLauncher
{
	public static void main(String[] args)  
	{
		SpringApplication.run(ServerLauncher.class, args);
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Settings.GAME_WIDTH;
		config.height = Settings.GAME_HEIGHT; 
		new LwjglApplication(new GameServer(), config);
	}
}
