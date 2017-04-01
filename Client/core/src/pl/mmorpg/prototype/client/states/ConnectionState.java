package pl.mmorpg.prototype.client.states;

import java.io.IOException;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.clientservercommon.Settings;

public class ConnectionState implements State
{
	private StateManager states;
	private Client client;
	private int maxTryouts = 3;
	private int currentTryout = 0;

	private Thread connectThread;

	public ConnectionState(Client client, StateManager states, String ip)
	{
		this.client = client;
		this.states = states;
		connectThread = new Thread(() -> tryConnecting(client, ip));
		connectThread.start();
	}

	private void tryConnecting(Client client, String ip)
	{
		try
		{
			client.start();
			client.connect(3000, ip, Settings.TCP_PORT, Settings.UDP_PORT);
		} catch (IOException e)
		{
			currentTryout++;
			if (currentTryout < maxTryouts)
				tryConnecting(client, ip);
		}

	}

	@Override
	public void render(SpriteBatch batch)
	{
	}

	@Override
	public void update(float deltaTime)
	{
		if (client.isConnected())
			states.pop();
		else if (!connectThread.isAlive())
			states.set(new SettingsChoosingState(client, states));
	}

}
