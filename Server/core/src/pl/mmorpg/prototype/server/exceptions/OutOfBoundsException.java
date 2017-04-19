package pl.mmorpg.prototype.server.exceptions;

import java.awt.Point;

public class OutOfBoundsException extends RuntimeException
{
	public OutOfBoundsException(Point point)
	{
		super(point.toString());
	}
}
