package pl.mmorpg.prototype.clientservercommon.packets;

import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class AuthenticationReplyPacket
{
	public int userId;
	public boolean isAuthenticated = false;
	public String message = "";
}
