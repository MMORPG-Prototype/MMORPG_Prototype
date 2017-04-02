package pl.mmorpg.prototype.server.helpers;

import java.util.Map;

import javax.persistence.NoResultException;

import pl.mmorpg.prototype.clientservercommon.packets.AuthenticationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.AuthenticatonReplyPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.managers.UserTableManager;
import pl.mmorpg.prototype.server.exceptions.NotAuthenticatedException;

public class Authenticator
{
	private Map<Integer, UserInfo> authenticatedUsers;
	private AuthenticationPacket packet;

	private AuthenticatonReplyPacket replyPacket;
	private User authenticatedUser;

	public Authenticator(Map<Integer, UserInfo> authenticatedUsers, AuthenticationPacket packet)
	{
		this.authenticatedUsers = authenticatedUsers;
		this.packet = packet;
	}


	public AuthenticatonReplyPacket tryAuthenticating()
	{
		replyPacket = new AuthenticatonReplyPacket();
		User user = null;
		try
		{
			user = UserTableManager.getUser(packet.username);
		}
		catch(NoResultException e)
		{
			replyPacket.message = "Wrong username";
			return replyPacket;
		}
		
		if (user.getPassword().compareTo(packet.password) == 0)
		{
			if (authenticatedUsers.containsKey(user.getId()))
				replyPacket.message = "User is already logged in!";
			else
			{
				replyPacket.userId = user.getId();
				replyPacket.isAuthenticated = true;
				authenticatedUser = user;
			}
		} 
		else
			replyPacket.message = "Wrong password";
		return replyPacket;
	}

	public boolean isAuthenticated()
	{
		return replyPacket.isAuthenticated;
	}

	public User getUser()
	{
		if (!isAuthenticated())
			throw new NotAuthenticatedException();
		return authenticatedUser;
	}

	public AuthenticatonReplyPacket getReplyPacket()
	{
		return replyPacket;
	}
}
