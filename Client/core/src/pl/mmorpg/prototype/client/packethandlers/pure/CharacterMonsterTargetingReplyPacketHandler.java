package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.MonsterTargetingReplyPacket;

public class CharacterMonsterTargetingReplyPacketHandler extends PacketHandlerBase<MonsterTargetingReplyPacket>
{
	private PlayState playState;

	public CharacterMonsterTargetingReplyPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void doHandle(MonsterTargetingReplyPacket packet)
	{
		playState.monsterTargeted(packet.monsterId);	
	}

}
