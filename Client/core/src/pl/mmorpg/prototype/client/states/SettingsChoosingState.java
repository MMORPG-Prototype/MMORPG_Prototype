package pl.mmorpg.prototype.client.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.dialogs.AskForIpDialog;

public class SettingsChoosingState implements State
{
	private Client client;
	private Stage stage = Assets.getStage();
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
		stage.draw();
	}

	@Override
	public void update(float deltaTime)
	{
		stage.act();
		if(ip != null)
		{
			stage.dispose();
			states.set(new ConnectionState(client, states, ip));
		}

	}

	public void connect(String ip)
	{
		this.ip = ip;
	}

	@Override
	public void reactivate()
	{
	}

}
