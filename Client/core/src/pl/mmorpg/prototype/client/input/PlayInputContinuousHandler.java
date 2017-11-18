package pl.mmorpg.prototype.client.input;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.client.objects.Player;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveDownPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveLeftPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveRightPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveUpPacket;

public class PlayInputContinuousHandler extends InputProcessorAdapter
{
	private Connection clientConnection;
	private Player player;
	private UserInterface userInterface;

	public PlayInputContinuousHandler(UserInterface userInterface, Connection clientConnection, Player player)
	{
		this.userInterface = userInterface;
		this.clientConnection = clientConnection;
		this.player = player;
	}
	
	@Override
    protected boolean shouldAvoidInput()
    {
    	return userInterface.isTextFieldFocused();
    }

	public class WKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			MoveUpPacket packet = new MoveUpPacket();
			packet.id = player.getId();
			clientConnection.sendTCP(packet);
		}
	}

	public class AKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			MoveLeftPacket packet = new MoveLeftPacket();
			packet.id = player.getId();
			clientConnection.sendTCP(packet);
		}
	}

	public class SKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			MoveDownPacket packet = new MoveDownPacket();
			packet.id = player.getId();
			clientConnection.sendTCP(packet);
		}
	}

	public class DKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			MoveRightPacket packet = new MoveRightPacket();
			packet.id = player.getId();
			clientConnection.sendTCP(packet);
		}
	}
}
