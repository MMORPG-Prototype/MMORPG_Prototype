package pl.mmorpg.prototype.client.packethandlers;

public class SimplePacketHandlerRegisterer implements PacketHandlerRegisterer
{
	private PacketHandlerDispatcher packetHandlerDispatcher;

	public SimplePacketHandlerRegisterer(PacketHandlerDispatcher packetHandlerDispatcher)
	{
		this.packetHandlerDispatcher = packetHandlerDispatcher;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	
}
