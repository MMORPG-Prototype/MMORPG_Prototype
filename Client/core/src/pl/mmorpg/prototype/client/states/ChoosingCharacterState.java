package pl.mmorpg.prototype.client.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.communication.UserInfo;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.userinterface.dialogs.ChoosingCharacterDialog;
import pl.mmorpg.prototype.clientservercommon.packets.GetUserCharactersPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class ChoosingCharacterState implements State
{
	private Stage stage = Assets.getStage();
	private ChoosingCharacterDialog dialog;
	private Client client;
	private StateManager states;

	public ChoosingCharacterState(Client client, StateManager states)
	{
		this.client = client;
		this.states = states;
		GetUserCharactersPacket getUserCharactersPacket = new GetUserCharactersPacket();
		getUserCharactersPacket.username = UserInfo.username;
		client.sendTCP(getUserCharactersPacket);
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
	}

	@Override
	public void reactivate()
	{
	}

	public void userCharactersDataReceived(UserCharacterDataPacket[] userCharacters)
	{
		dialog = new ChoosingCharacterDialog(this, userCharacters);
		dialog.show(stage);
		Gdx.input.setInputProcessor(stage);
	}


	public void characterChoosen(UserCharacterDataPacket userCharacterDataPacket)
	{
		states.find(PlayState.class).initialize(userCharacterDataPacket);
		client.sendTCP(userCharacterDataPacket);
		states.pop();
	}

}
