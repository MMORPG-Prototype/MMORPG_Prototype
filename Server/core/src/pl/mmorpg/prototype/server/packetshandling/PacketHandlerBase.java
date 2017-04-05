package pl.mmorpg.prototype.server.packetshandling;


import com.esotericsoftware.kryonet.Connection;

public abstract class PacketHandlerBase<T> implements PacketHandler<T>
{
	@Override
	public void handle(Object object, Connection connection)
	{
		@SuppressWarnings("unchecked")
		T packet = (T) object;
		handle(connection, packet);
	}
	
	public abstract void handle(Connection connection, T packet);

}
