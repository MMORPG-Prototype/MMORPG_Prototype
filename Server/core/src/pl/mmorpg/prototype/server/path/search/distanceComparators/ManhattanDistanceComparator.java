package pl.mmorpg.prototype.server.path.search.distanceComparators;

import java.awt.Point;

public class ManhattanDistanceComparator extends DistanceComparator
{
	public ManhattanDistanceComparator(Point destination)
	{
		super(destination);
	}

	@Override
	public double getDistance(Point from, Point to)
	{
		return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
	}

}
