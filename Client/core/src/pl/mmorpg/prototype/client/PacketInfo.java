package pl.mmorpg.prototype.client;

public class PacketInfo
{
	public int id;
	public Object packet;

	public PacketInfo(int id, Object packet)
	{
		this.id = id;
		this.packet = packet;
	}
}
