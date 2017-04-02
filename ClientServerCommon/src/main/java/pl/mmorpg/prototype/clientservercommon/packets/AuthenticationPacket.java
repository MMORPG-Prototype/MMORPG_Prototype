package pl.mmorpg.prototype.clientservercommon.packets;

import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class AuthenticationPacket
{
	public long clientId;
	public String username;
	public String password;
}
