package pl.mmorpg.protptype.client.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import pl.mmorpg.prototype.client.GameClient;
import pl.mmorpg.prototype.clientservercommon.Settings;

public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Settings.GAME_WIDTH;
		config.height = Settings.GAME_HEIGHT;
		new LwjglApplication(new GameClient(), config);
	}
}
