package pl.mmorpg.protptype.server.desktop;

import org.mockito.Mockito;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.backends.headless.HeadlessNativesLoader;
import com.badlogic.gdx.backends.headless.HeadlessNet;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.badlogic.gdx.backends.lwjgl.LwjglNativesLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;

import pl.mmorpg.prototype.server.GameServer;

public class ServerLauncher
{
    public static void main(String[] arg)
    {
    	LwjglNativesLoader.load();
		com.badlogic.gdx.Gdx.files = new LwjglFiles();
		HeadlessNativesLoader.load();
		MockGraphics mockGraphics = new MockGraphics();
		com.badlogic.gdx.Gdx.graphics = mockGraphics;
		HeadlessNet headlessNet = new HeadlessNet();
		com.badlogic.gdx.Gdx.net = headlessNet;
		com.badlogic.gdx.Gdx.gl = (GL20) Mockito.mock(GL20.class);
		com.badlogic.gdx.Gdx.gl20 = (GL20) Mockito.mock(GL20.class);
		com.badlogic.gdx.Gdx.gl30 = (GL30) Mockito.mock(GL30.class);
		HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
		new HeadlessApplication(new GameServer(), config);
    }
}
