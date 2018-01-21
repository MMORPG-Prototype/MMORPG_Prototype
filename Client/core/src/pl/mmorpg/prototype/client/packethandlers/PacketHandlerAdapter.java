package pl.mmorpg.prototype.client.packethandlers;

public abstract class PacketHandlerAdapter<T extends Object> implements PacketHandler<T>
{
    @Override
	public boolean canBeHandled(Object packet)
	{
		return true;
	}

	@Override
	public boolean canBeOmmited(Object packet)
	{
		return false;
	}
}
