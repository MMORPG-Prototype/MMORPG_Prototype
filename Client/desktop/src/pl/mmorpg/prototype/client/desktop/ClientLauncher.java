package pl.mmorpg.prototype.client.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import pl.mmorpg.prototype.client.GameLoader;
import pl.mmorpg.prototype.clientservercommon.Settings;

public class ClientLauncher
{
	public static void main(String[] args)
	{
		var config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
		config.setTitle("Game client");
		config.useVsync(true);
		new Lwjgl3Application(new GameLoader(), config);
	}
}
