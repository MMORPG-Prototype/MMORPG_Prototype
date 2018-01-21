package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.MonsterTargetingReplyPacket;

public class CharacterMonsterTargetingReplyPacketHandler extends PacketHandlerAdapter<MonsterTargetingReplyPacket>
{
	private PlayState playState;

	public CharacterMonsterTargetingReplyPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handle(MonsterTargetingReplyPacket packet)
	{
		playState.monsterTargeted(packet.monsterId);	
	}

}
