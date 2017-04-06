package pl.mmorpg.prototype.client.exceptions;

public class UnknownPacketTypeException extends GameException
{

	public UnknownPacketTypeException(Object object)
	{
		super(object.toString());
	}

}
