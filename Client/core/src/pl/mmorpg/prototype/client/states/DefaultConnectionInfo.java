package pl.mmorpg.prototype.client.states;

import pl.mmorpg.prototype.client.resources.ConnectionProperties;

public class DefaultConnectionInfo extends ConnectionInfo
{
	public DefaultConnectionInfo()
	{
		super(ConnectionProperties.getConnectionIp());
	}
}
