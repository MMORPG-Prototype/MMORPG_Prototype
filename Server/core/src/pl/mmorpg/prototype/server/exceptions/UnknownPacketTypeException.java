package pl.mmorpg.prototype.server.exceptions;

public class UnknownPacketTypeException extends GameException
{

	public UnknownPacketTypeException(Object object)
	{
		super(object.toString());
	}

}
