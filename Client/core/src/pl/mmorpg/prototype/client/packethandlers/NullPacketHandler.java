package pl.mmorpg.prototype.client.packethandlers;

public class NullPacketHandler implements PacketHandler
{
	@Override
	public void handle(Object object)
	{
	}

	@Override
	public boolean canBeHandled(Object packet)
	{
		return true;
	}

}
