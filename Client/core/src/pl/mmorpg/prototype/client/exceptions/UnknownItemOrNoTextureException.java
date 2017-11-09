package pl.mmorpg.prototype.client.exceptions;

public class UnknownItemOrNoTextureException extends GameException
{

	public UnknownItemOrNoTextureException(String itemIdentifier)
	{
		super(itemIdentifier);
	}

}
