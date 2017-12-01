package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.KnownSpellInfoPacket;

public class KnownSpellInfoPacketHandler extends PacketHandlerBase<KnownSpellInfoPacket>
{
	private PlayState playState;

	public KnownSpellInfoPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}

	@Override
	public void handlePacket(KnownSpellInfoPacket packet)
	{
		playState.knownSpellInfoReceived(packet.getSpellIdentifer());
	}

}