package pl.mmorpg.prototype.client.items;

import pl.mmorpg.prototype.client.exceptions.GameException;

public class UnknownItemOrNoTextureException extends GameException
{

	public UnknownItemOrNoTextureException(String itemIdentifier)
	{
		super(itemIdentifier);
	}

}
