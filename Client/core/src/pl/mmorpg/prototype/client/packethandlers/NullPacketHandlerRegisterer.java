package pl.mmorpg.prototype.client.packethandlers;

public class NullPacketHandlerRegisterer implements PacketHandlerRegisterer
{
	@Override
	public void register(PacketHandler packetHandler)
	{
	}

	@Override
	public void registerPrivateClassPacketHandlers(Object objectContainingDefinitionOfPrivatePacketHandlers)
	{
	}

	@Override
	public void unregister(PacketHandler packetHandler)
	{
	}

	@Override
	public void unregister(PacketHandlerCategory category)
	{
	}
}
