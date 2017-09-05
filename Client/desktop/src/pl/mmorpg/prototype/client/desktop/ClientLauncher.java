package pl.mmorpg.prototype.client.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import pl.mmorpg.prototype.client.GameClient;
import pl.mmorpg.prototype.clientservercommon.Settings;

public class ClientLauncher
{
    public static void main(String[] args)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Settings.GAME_WIDTH;
        config.height = Settings.GAME_HEIGHT;
        config.vSyncEnabled = false;
        new LwjglApplication(new GameClient(), config);
        System.out.println("Jar file hash test");
    }
}
