package pl.mmorpg.prototype.client.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.communication.UserInfo;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.userinterface.dialogs.ChoosingCharacterDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.CreatingCharacterDialog;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.GetUserCharactersPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class ChoosingCharacterState implements State
{
	private Stage stage = Assets.getStage();
	private ChoosingCharacterDialog choosingDialog;
	private CreatingCharacterDialog creatingDialog;
	private Client client;
	private StateManager states;

	public ChoosingCharacterState(Client client, StateManager states)
	{
		this.client = client;
		this.states = states;
		creatingDialog = new CreatingCharacterDialog(this);
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
		choosingDialog = new ChoosingCharacterDialog(this, userCharacters);
		choosingDialog.show(stage);
		Gdx.input.setInputProcessor(stage);
	}


	public void characterChoosen(UserCharacterDataPacket userCharacterDataPacket)
	{
		states.find(PlayState.class).initialize(userCharacterDataPacket);
		client.sendTCP(userCharacterDataPacket);
		states.pop();
	}

	public void userWantsToCreateCharacter()
	{
		choosingDialog.hide();
		creatingDialog.show(stage);		
	}

	public void userCharacterCreationReplyReceived(CharacterCreationReplyPacket creationPacket)
	{
		if(creationPacket.isCreated())
		{
			choosingDialog.add(creationPacket.getCharacter());
			choosingDialog.clearErrorMessage();
			choosingDialog.show(stage);
		}
		else
		{
			creatingDialog.setErrorMessage(creationPacket.getErrorMessage());
			creatingDialog.show(stage);
		}
	}
	
	public void userSubmitedCharacterCreation(String nickname)
	{
		CharacterCreationPacket packet = new CharacterCreationPacket();
		packet.setNickname(nickname);
		client.sendTCP(packet);
	}

	public void userCancelledCharacterCreation()
	{
		creatingDialog.hide();
		choosingDialog.show(stage);		
	}

}
