package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
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
	public void doHandle(KnownSpellInfoPacket packet)
	{
		playState.knownSpellInfoReceived(packet.getSpellIdentifer());
	}

}
