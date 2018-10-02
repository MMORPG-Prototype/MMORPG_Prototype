package pl.mmorpg.prototype.client.packethandlers;

public abstract class UserInterfacePacketHandlerBase<T> extends PacketHandlerBase<T>
{
	@Override
	public PacketHandlerCategory getCategory()
	{
		return PacketHandlerCategory.USER_INTERFACE;
	}
}
