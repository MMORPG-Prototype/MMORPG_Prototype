package pl.mmorpg.prototype.client.states;

import java.util.ArrayList;
import java.util.Collection;

import pl.mmorpg.prototype.client.packethandlers.PacketHandler;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;

public abstract class PacketHandlingState implements State
{
	private final Collection<PacketHandler<?>> handlers = new ArrayList<>();

	public void registerHandler(PacketHandlerRegisterer packetHandlerRegisterer, PacketHandler<?> packetHandler)
	{
		handlers.add(packetHandler);
		packetHandlerRegisterer.register(packetHandler);
	}

	@Override
	public void unregisterHandlers(PacketHandlerRegisterer packetHandlerRegisterer)
	{
		handlers.forEach(packetHandlerRegisterer::unregister);
		handlers.clear();
	}

}
