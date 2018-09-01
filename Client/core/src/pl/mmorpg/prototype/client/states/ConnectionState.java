package pl.mmorpg.prototype.client.states;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.userinterface.dialogs.ConnectingInProgressDialog;

public class ConnectionState implements State
{
	private final Stage stage = Assets.getStage();
	private final ConnectingInProgressDialog connectionDialog = new ConnectingInProgressDialog();
	private final StateManager states;
	private final Client client;
	private final int maxTryouts = 3;
	private int currentTryout = 0;

	private Thread connectThread;
	private PacketHandlerRegisterer registerer;

	public ConnectionState(Client client, StateManager states, PacketHandlerRegisterer registerer,
			ConnectionInfo connectionInfo)
	{
		this.client = client;
		this.states = states;
		this.registerer = registerer;
		client.start();
		connectThread = new Thread(() -> tryConnecting(client, connectionInfo));
		connectThread.start();
		connectionDialog.show(stage);
		Gdx.input.setInputProcessor(stage);
	}

	private void tryConnecting(Client client, ConnectionInfo connectionInfo)
	{
		try
		{
			client.connect(3000, connectionInfo.getIp(), connectionInfo.getTcpPort(), connectionInfo.getUdpPort());
		} catch (IOException e)
		{
			currentTryout++;
			if (currentTryout < maxTryouts)
				tryConnecting(client, connectionInfo);
		}

	}

	@Override
	public void render(SpriteBatch batch)
	{
		stage.draw();
	}

	@Override
	public void update(float deltaTime)
	{
		stage.act(deltaTime);
		if (client.isConnected())
			states.set(new AuthenticationState(client, states, registerer));
		else if (!connectThread.isAlive())
		{
			states.set(new SettingsChoosingState(client, states, registerer));
			states.push(new CannotConnectToServerInfoState(states));
		}
			
	}

	@Override
	public void reactivate()
	{
		Gdx.input.setInputProcessor(stage);
	}

}
