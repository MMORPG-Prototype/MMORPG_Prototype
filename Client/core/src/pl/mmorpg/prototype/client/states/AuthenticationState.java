package pl.mmorpg.prototype.client.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.communication.UserInfo;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.userinterface.dialogs.AuthenticationDialog;
import pl.mmorpg.prototype.clientservercommon.packets.AuthenticationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.AuthenticationReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.DisconnectPacket;

public class AuthenticationState extends PacketHandlingState
{
	private Stage stage = Assets.getStage();
	private Client client;
	private StateManager states;
	private AuthenticationDialog authenticationDialog;
	private PacketHandlerRegisterer registerer;

	public AuthenticationState(Client client, StateManager states, PacketHandlerRegisterer registerer)
	{
		this.client = client;
		this.states = states;
		this.registerer = registerer;
		authenticationDialog = new AuthenticationDialog(this);
		authenticationDialog.show(stage);
		Gdx.input.setInputProcessor(stage);
		try
		{
			Thread.sleep(100);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		registerHandler(registerer, new AuthenticationReplyPacketHandler());
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

	public void sendAuthenticationRequest(String username, String password)
	{
		AuthenticationPacket authenticatePacket = new AuthenticationPacket();
		authenticatePacket.clientId = client.getID();
		authenticatePacket.username = username;
		authenticatePacket.password = password;
		client.sendTCP(authenticatePacket);
	}

	@Override
	public void reactivate()
	{
		authenticationDialog.show(stage);
		Gdx.input.setInputProcessor(stage);
	}

	public void userWantsToRegister()
	{
		states.push(new RegisterationState(client, states));
	}

	public void userPressedCancel()
	{
		client.sendTCP(new DisconnectPacket());
		stage.dispose();
		states.set(new SettingsChoosingState(client, states, registerer));
	}
	
	
	private class AuthenticationReplyPacketHandler extends PacketHandlerBase<AuthenticationReplyPacket>
	{
		@Override
		protected void doHandle(AuthenticationReplyPacket replyPacket)
		{
			if(replyPacket.isAuthenticated)
			{
				UserInfo.username = authenticationDialog.getUsername();
				stage.dispose();
				states.set(new ChoosingCharacterState(client, states, registerer));
			}
			else
			{
				authenticationDialog.setErrorMessage(replyPacket.message);
				authenticationDialog.show(stage);
			}
		}
	}

}
