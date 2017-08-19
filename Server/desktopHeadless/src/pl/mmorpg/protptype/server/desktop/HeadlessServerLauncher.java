package pl.mmorpg.protptype.server.desktop;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.backends.headless.HeadlessNativesLoader;
import com.badlogic.gdx.backends.headless.HeadlessNet;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.badlogic.gdx.backends.lwjgl.LwjglNativesLoader;

import pl.mmorpg.prototype.server.GameServer;
import pl.mmorpg.prototype.server.ServerSettings;
import pl.mmorpg.prototype.server.headless.NullGL20;
import pl.mmorpg.prototype.server.headless.NullGL30;

public class HeadlessServerLauncher
{
    public static void main(String[] arg)
    {
    	ServerSettings.isHeadless = true;
    	LwjglNativesLoader.load();
		Gdx.files = new LwjglFiles();
		HeadlessNativesLoader.load();
		MockGraphics mockGraphics = new MockGraphics();
		Gdx.graphics = mockGraphics;
		HeadlessNet headlessNet = new HeadlessNet();
		Gdx.net = headlessNet;
		Gdx.gl = new NullGL20();
		Gdx.gl20 = new NullGL20();
		Gdx.gl30 = new NullGL30();
		HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
		new HeadlessApplication(new GameServer(), config);
    }
}
