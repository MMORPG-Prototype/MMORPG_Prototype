package pl.mmorpg.prototype.client.input;

import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.objects.Player;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveDownPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveLeftPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveRightPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveUpPacket;

public class PlayInputContinuousHandler extends InputProcessorAdapter
{
	private Client client;
	private Player player;

	public PlayInputContinuousHandler(Client client, Player player)
	{
		this.client = client;
		this.player = player;
	}

	public class WKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			MoveUpPacket packet = new MoveUpPacket();
			packet.id = player.getId();
			client.sendTCP(packet);

		}
	}

	public class AKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			MoveLeftPacket packet = new MoveLeftPacket();
			packet.id = player.getId();
			client.sendTCP(packet);
		}
	}

	public class SKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{

			MoveDownPacket packet = new MoveDownPacket();
			packet.id = player.getId();
			client.sendTCP(packet);
		}
	}

	public class DKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			MoveRightPacket packet = new MoveRightPacket();
			packet.id = player.getId();
			client.sendTCP(packet);
		}
	}
}
