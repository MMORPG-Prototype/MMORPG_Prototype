package pl.mmorpg.prototype.client.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.userinterface.dialogs.AskForIpDialog;

public class SettingsChoosingState implements State
{
	private final Client client;
	private final Stage stage = Assets.getStage();
	private final StateManager states;
	private ConnectionInfo connectionInfo = null;

	public SettingsChoosingState(Client client, StateManager states)
	{
		this.client = client;
		this.states = states;
		Gdx.input.setInputProcessor(stage);

		AskForIpDialog dialog = new AskForIpDialog(this, new ConnectionInfo("localhost"));
		dialog.show(stage);
	}


	@Override
	public void render(SpriteBatch batch)
	{
		stage.draw();
	}

	@Override
	public void update(float deltaTime)
	{
		stage.act();
		if(connectionInfo != null)
		{
			stage.dispose();
			states.set(new ConnectionState(client, states, connectionInfo));
		}

	}

	public void connect(ConnectionInfo connectionInfo)
	{
		this.connectionInfo = connectionInfo;
	}

	@Override
	public void reactivate()
	{
	}

}
