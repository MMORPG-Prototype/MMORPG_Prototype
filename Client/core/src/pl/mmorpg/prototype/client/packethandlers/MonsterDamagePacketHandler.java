package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.damage.NormalDamagePacket;

public class MonsterDamagePacketHandler extends PacketHandlerBase<NormalDamagePacket>
{
	private PlayState playState;

	public MonsterDamagePacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handlePacket(NormalDamagePacket packet)
	{
		playState.monsterDamagePacketReceived(packet);
	}
	
	@Override
	public boolean canBeHandled(Object packet)
	{
		return playState.isInitialized() && playState.has(((NormalDamagePacket)packet).getTargetId());
	}

}
