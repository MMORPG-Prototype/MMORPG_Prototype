package pl.mmorpg.protptype.server.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import pl.mmorpg.prototype.clientservercommon.Settings;
import pl.mmorpg.prototype.server.GameServer;

public class ServerLauncher
{
    public static void main(String[] arg)
    {
    	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    	config.width = Settings.GAME_WIDTH;
    	config.height = Settings.GAME_HEIGHT;
		new LwjglApplication(new GameServer(), config);
    }
}
