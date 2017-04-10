package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.MonsterDamagePacket;

public class MonsterDamagePacketHandler extends PacketHandlerBase<MonsterDamagePacket>
{
	private PlayState playState;

	public MonsterDamagePacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handlePacket(MonsterDamagePacket packet)
	{
		playState.monsterDamagePacketReceived(packet);
	}

}
