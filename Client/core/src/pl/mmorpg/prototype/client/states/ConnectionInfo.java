package pl.mmorpg.prototype.client.states;

import pl.mmorpg.prototype.clientservercommon.Settings;

public class ConnectionInfo
{
	private final String ip;
	private final int tcpPort;
	private final int udpPort;
	
	public ConnectionInfo(String ip)
	{
		this(ip, Settings.TCP_PORT, Settings.UDP_PORT);
	}
	
	public ConnectionInfo(String ip, int tcpPort, int udpPort)
	{
		this.ip = ip;
		this.tcpPort = tcpPort;
		this.udpPort = udpPort;
	}

	public String getIp()
	{
		return ip;
	}
	
	public int getTcpPort()
	{
		return tcpPort;
	}
	
	public int getUdpPort()
	{
		return udpPort;
	}
	
}
