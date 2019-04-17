package pl.mmorpg.protptype.server.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.mmorpg.prototype.clientservercommon.Settings;
import pl.mmorpg.prototype.server.GameServer;

@SpringBootApplication(scanBasePackages="pl.mmorpg.prototype")
public class ServerLauncher
{

	public static void main(String[] args)  
	{
		SpringApplication.run(ServerLauncher.class, args);
		var config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
		new Lwjgl3Application(new GameServer(), config);
	}
}
