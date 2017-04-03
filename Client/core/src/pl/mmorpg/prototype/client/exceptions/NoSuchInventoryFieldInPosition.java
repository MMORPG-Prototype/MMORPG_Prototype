package pl.mmorpg.prototype.client.exceptions;

import java.awt.Point;

public class NoSuchInventoryFieldInPosition extends GameException
{

	public NoSuchInventoryFieldInPosition(Point position)
	{
		super(position.toString());
	}

}
