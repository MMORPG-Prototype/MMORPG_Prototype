package pl.mmorpg.protptype.server.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.backends.headless.HeadlessNativesLoader;
import com.badlogic.gdx.backends.headless.HeadlessNet;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3NativesLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.mmorpg.prototype.server.GameServer;
import pl.mmorpg.prototype.server.ServerSettings;
import pl.mmorpg.prototype.server.headless.NullGL20;
import pl.mmorpg.prototype.server.headless.NullGL30;

@SpringBootApplication(scanBasePackages="pl.mmorpg.prototype")
public class HeadlessServerLauncher
{
    public static void main(String[] args)
    {
		SpringApplication.run(HeadlessServerLauncher.class, args);
    	
    	ServerSettings.isHeadless = true;
    	Lwjgl3NativesLoader.load();
		Gdx.files = new Lwjgl3Files();
		HeadlessNativesLoader.load();
		Gdx.graphics = new MockGraphics();
		Gdx.net = new HeadlessNet();
		Gdx.gl = new NullGL20();
		Gdx.gl20 = new NullGL20();
		Gdx.gl30 = new NullGL30();
		HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
		new HeadlessApplication(new GameServer(), config);
    }
}
