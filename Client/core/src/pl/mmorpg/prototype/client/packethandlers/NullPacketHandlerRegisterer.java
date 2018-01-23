package pl.mmorpg.prototype.client.packethandlers;

public class NullPacketHandlerRegisterer implements PacketHandlerRegisterer
{
	@Override
	public void register(@SuppressWarnings("rawtypes") PacketHandler packetHandler)
	{
	}

	@Override
	public void registerPrivateClassPacketHandlers(Object objectContainingDefinitionOfPrivatePacketHandlers)
	{
	}

	@Override
	public void unregister(@SuppressWarnings("rawtypes") PacketHandler packetHandler)
	{
	}
}
