package pl.mmorpg.prototype.client.packethandlers;

public abstract class PacketHandlerBase<T> implements PacketHandler
{
	@Override
	public void handle(Object object)
	{
		@SuppressWarnings("unchecked")
        T packet = (T) object;
		handlePacket(packet);
	}

    @SuppressWarnings("unchecked") 
    @Override
	public boolean canBeHandled(Object packet)
	{
		return canHandle((T)packet);
	}
	
	public boolean canHandle(T packet)
	{
		return true;
	}

    @SuppressWarnings("unchecked") 
	@Override
	public boolean canBeOmmited(Object packet)
	{
		return canOmmit((T) packet);
	}
	
	public boolean canOmmit(T packet)
	{
		return false;
	}

	public abstract void handlePacket(T packet);

}
