package pl.mmorpg.prototype.client.packethandlers;

public class SimplePacketHandlerRegisterer implements PacketHandlerRegisterer
{
	private PacketHandlerDispatcher packetHandlerDispatcher;

	public SimplePacketHandlerRegisterer(PacketHandlerDispatcher packetHandlerDispatcher)
	{
		this.packetHandlerDispatcher = packetHandlerDispatcher;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void register(PacketHandler packetHandler)
	{
		packetHandlerDispatcher.registerHandler(packetHandler);
	}

	@Override
	public void registerPrivateClassPacketHandlers(Object objectContainingDefinitionOfPrivatePacketHandlers)
	{
		packetHandlerDispatcher.registerPrivateClassPacketHandlers(objectContainingDefinitionOfPrivatePacketHandlers);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void unregister(PacketHandler packetHandler)
	{
		packetHandlerDispatcher.removeHandler(packetHandler);
	}

	@Override
	public void unregister(PacketHandlerCategory category)
	{
		packetHandlerDispatcher.unregister(category);
	}

}
