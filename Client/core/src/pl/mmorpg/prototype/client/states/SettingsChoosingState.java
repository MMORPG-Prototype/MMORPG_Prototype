package pl.mmorpg.prototype.client.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.input.dialogs.AskForIpDialog;

public class SettingsChoosingState implements State
{
	private Client client;
	private Stage stage = new Stage();
	private StateManager states;
	private String ip;

	public SettingsChoosingState(Client client, StateManager states)
	{
		this.client = client;
		this.states = states;
		Gdx.input.setInputProcessor(stage);

		AskForIpDialog dialog = new AskForIpDialog(this);
		dialog.show(stage);
	}


	@Override
	public void render(SpriteBatch batch)
	{
		stage.act();
		stage.draw();
	}

	@Override
	public void update(float deltaTime)
	{
		if(ip != null)
		{
			states.set(new ConnectionState(client, states, ip));
			stage.dispose();
		}
	}

	public void connect(String ip)
	{
		this.ip = ip;
	}

}
