package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterMonsterTargetingReplyPacket;

public class CharacterMonsterTargetingReplyPacketHandler extends PacketHandlerBase<CharacterMonsterTargetingReplyPacket>
{
	private PlayState playState;

	public CharacterMonsterTargetingReplyPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handlePacket(CharacterMonsterTargetingReplyPacket packet)
	{
		playState.monsterTargeted(packet.monsterId);	
	}

}
