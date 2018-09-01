package pl.mmorpg.prototype.server.exceptions;

public class UserNotConnectedException extends GameException
{
	public UserNotConnectedException(int connectionId)
	{
		super("There is no logged user with connection id: " + connectionId);
	}
}
