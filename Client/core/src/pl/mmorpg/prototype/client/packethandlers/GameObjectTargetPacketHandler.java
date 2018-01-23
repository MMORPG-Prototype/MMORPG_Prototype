package pl.mmorpg.prototype.client.packethandlers;

public interface GameObjectTargetPacketHandler<T> extends PacketHandler<T>
{
	long getObjectId();
}
