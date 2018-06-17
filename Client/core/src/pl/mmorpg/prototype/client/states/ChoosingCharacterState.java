package pl.mmorpg.prototype.client.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.communication.UserInfo;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.userinterface.dialogs.ChoosingCharacterDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.CreatingCharacterDialog;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.GetUserCharactersPacket;
import pl.mmorpg.prototype.clientservercommon.packets.LogoutPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class ChoosingCharacterState extends PacketHandlingState
{
	private Stage stage = Assets.getStage();
	private ChoosingCharacterDialog choosingDialog;
	private CreatingCharacterDialog creatingDialog;
	private Client client;
	private StateManager states;
	private PacketHandlerRegisterer packetHandlerRegisterer;

	public ChoosingCharacterState(Client client, StateManager states, PacketHandlerRegisterer packetHandlerRegisterer)
	{
		this.client = client;
		this.states = states;
		this.packetHandlerRegisterer = packetHandlerRegisterer;
		registerHandler(packetHandlerRegisterer, new CharacterCreationReplyPacketHandler());
		registerHandler(packetHandlerRegisterer, new UserCharacterDataArrayPacketHandler());
		
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

	public void userCancelledChoosing()
	{
		client.sendTCP(new LogoutPacket());
		states.set(new AuthenticationState(client, states, packetHandlerRegisterer));
	}
	
	private class CharacterCreationReplyPacketHandler extends PacketHandlerBase<CharacterCreationReplyPacket>
	{
		@Override
		protected void doHandle(CharacterCreationReplyPacket creationPacket)
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
	}
	
	private class UserCharacterDataArrayPacketHandler extends PacketHandlerBase<UserCharacterDataPacket[]>
	{
		@Override
		protected void doHandle(UserCharacterDataPacket[] userCharacters)
		{
			choosingDialog = new ChoosingCharacterDialog(ChoosingCharacterState.this, userCharacters);
			choosingDialog.show(stage);
			Gdx.input.setInputProcessor(stage);
		}
	}

}
