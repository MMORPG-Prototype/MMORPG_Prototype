package pl.mmorpg.prototype.clientservercommon.packets;

import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class RegisterationPacket
{
	public String username;
	public String password;
	public String passwordRepeat;

	public RegisterationPacket()
	{
	}

	public RegisterationPacket(String username, String password, String passwordRepeat)
	{
		this.username = username;
		this.password = password;
		this.passwordRepeat = passwordRepeat;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public String getPasswordRepeat()
	{
		return passwordRepeat;
	}
}
