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
	private ClientListener clientListener;

	@Override
	public void create()
	{
		batch = Assets.getBatch();
		states = new StateManager();
		client = new Client();
		playState = new PlayState(states, client);
		client = initizlizeClient();
		states.push(playState);
		states.push(new SettingsChoosingState(client, states));
	}

	private Client initizlizeClient()
	{
		Kryo kryo = client.getKryo();
		kryo = PacketsRegisterer.registerAllAnnotated(kryo);
		clientListener = new ClientListener(client, playState, states);
		client.addListener(clientListener);
		client.start();
		return client;
	}

	@Override
	public void render()
	{
		update();
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		states.render(batch);
	}

	private void update()
	{
		states.update(Gdx.graphics.getDeltaTime());
		clientListener.tryHandlingUnhandledPackets();
	}

	@Override
	public void dispose()
	{
		client.close();
		Assets.dispose();
	}
}
