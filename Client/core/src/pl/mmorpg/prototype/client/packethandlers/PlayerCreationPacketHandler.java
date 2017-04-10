package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.objects.Player;
import pl.mmorpg.prototype.client.objects.monsters.MonstersFactory;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.PlayerCreationPacket;

public class PlayerCreationPacketHandler extends PacketHandlerBase<PlayerCreationPacket>
{
	private PlayState playState;

	public PlayerCreationPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	
	@Override
	public void handlePacket(PlayerCreationPacket packet)
	{
		Player player = (Player)new MonstersFactory().produce(packet);
		player.initialize(packet.data);
		playState.add(player);
	}

}
