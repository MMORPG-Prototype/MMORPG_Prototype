package pl.mmorpg.prototype.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.SettingsChoosingState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.registering.PacketsRegisterer;

public class GameClient extends ApplicationAdapter
{
	private SpriteBatch batch;
	private Client client;
	private StateManager states;
	private PlayState playState;

	@Override
	public void create()
	{
		playState = new PlayState(states);
		client = initizlizeClient();
		batch = new SpriteBatch();
		states = new StateManager();
		states.push(playState);
		states.push(new SettingsChoosingState(client, states));
	}

	private Client initizlizeClient()
	{
		client = new Client();
		Kryo kryo = client.getKryo();
		kryo = PacketsRegisterer.registerAllAnnotated(kryo);
		client.addListener(new ClientListener(client, playState));
		client.start();
		return client;
	}

	@Override
	public void render()
	{
		states.update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		states.render(batch);
		batch.end();
	}

	@Override
	public void dispose()
	{
		client.close();
		Assets.dispose();
	}
}
