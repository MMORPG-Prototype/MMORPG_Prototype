package pl.mmorpg.prototype.clientservercommon.packets;

import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class RegisterationReplyPacket
{
	public boolean isRegistered = false;
	public String errorMessage = "";
}
