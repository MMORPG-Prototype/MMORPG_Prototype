package pl.mmorpg.prototype.client.states;

import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.input.InputProcessorAdapter;
import pl.mmorpg.prototype.client.input.KeyHandler;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveDownPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveLeftPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveRightPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveUpPacket;

public class PlayInputHandler extends InputProcessorAdapter
{
	private Client client;

	public PlayInputHandler(Client client)
	{
		this.client = client;
	}

	public class WKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			MoveUpPacket packet = new MoveUpPacket();
			packet.id = client.getID();
			client.sendTCP(packet);
		}
	}

	public class AKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			MoveLeftPacket packet = new MoveLeftPacket();
			packet.id = client.getID();
			client.sendTCP(packet);
		}
	}

	public class SKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			MoveDownPacket packet = new MoveDownPacket();
			packet.id = client.getID();
			client.sendTCP(packet);
		}
	}

	public class DKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			MoveRightPacket packet = new MoveRightPacket();
			packet.id = client.getID();
			client.sendTCP(packet);
		}
	}
}
