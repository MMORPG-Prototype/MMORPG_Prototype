package pl.mmorpg.prototype.client.packethandlers;

public abstract class PacketHandlerBase<T> implements PacketHandler
{
	@Override
	public void handle(Object object)
	{
		T packet = (T) object;
		handlePacket(packet);
	}

	@Override
	public boolean canBeHandled(Object packet)
	{
		return canHandle((T)packet);
	}
	
	public boolean canHandle(T packet)
	{
		return true;
	}

	public abstract void handlePacket(T packet);

}
