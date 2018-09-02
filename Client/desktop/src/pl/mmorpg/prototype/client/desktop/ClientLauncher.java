package pl.mmorpg.prototype.client.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import pl.mmorpg.prototype.client.GameLoader;
import pl.mmorpg.prototype.clientservercommon.Settings;

public class ClientLauncher
{
	public static void main(String[] args)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Settings.WINDOW_WIDTH;
		config.height = Settings.WINDOW_HEIGHT;
		config.title = "Game client";
		config.vSyncEnabled = false;
		new LwjglApplication(new GameLoader(), config);
	}
}
