package pl.mmorpg.prototype.server.path.search;

import java.awt.Point;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class PathSimplifier
{
	private static final float nearZero = 0.01f;
	
	public LinkedList<Point> simplify(Collection<? extends Point> path)
	{
		if(path.isEmpty())
			return new LinkedList<>();
		LinkedList<Point> simplifiedPath = new LinkedList<>();
		Iterator<? extends Point> it = path.iterator();
		Point previousPoint = it.next();
		simplifiedPath.add(previousPoint);
		float previousAngleRatio = -999;
		while (it.hasNext())
		{
			Point nextPoint = it.next();
			float nextAngleRatio;
			if(nextPoint.y == previousPoint.y)
				nextAngleRatio = 999;
			else
				nextAngleRatio = Math.abs(nextPoint.x - previousPoint.x) / Math.abs(nextPoint.y - previousPoint.y);

			if(!equals(previousAngleRatio, nextAngleRatio))
				simplifiedPath.add(nextPoint);
			previousPoint = nextPoint;
			previousAngleRatio = nextAngleRatio;
		}
		return simplifiedPath;
	}

	private boolean equals(float previousAngleRatio, float nextAngleRatio)
	{
		return Math.abs(previousAngleRatio - nextAngleRatio) <= nearZero;
	}
}
