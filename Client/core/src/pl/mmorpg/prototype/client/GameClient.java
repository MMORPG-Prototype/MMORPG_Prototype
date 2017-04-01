package pl.mmorpg.prototype.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.SettingsChoosingState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.registering.PacketsRegisterer;

public class GameClient extends ApplicationAdapter
{
	private SpriteBatch batch;
	private Client client;
	private StateManager states;

	@Override
	public void create()
	{
		client = initizlizeClient();
		batch = new SpriteBatch();
		states = new StateManager();
		states.push(new SettingsChoosingState(client));
	}

	private Client initizlizeClient()
	{
		client = new Client();
		Kryo kryo = client.getKryo();
		kryo = PacketsRegisterer.registerAllAnnotated(kryo);
		client.addListener(new ClientListener(this));
		client.start();
		return client;
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		states.render(batch);
		batch.end();
	}

	@Override
	public void dispose()
	{
		Assets.dispose();
	}
}
