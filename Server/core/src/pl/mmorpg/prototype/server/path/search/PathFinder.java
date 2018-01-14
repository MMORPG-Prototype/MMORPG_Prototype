package pl.mmorpg.prototype.server.path.search;

import java.awt.Point;
import java.util.Collection;

import pl.mmorpg.prototype.server.path.search.collisionDetectors.CollisionDetector;
import pl.mmorpg.prototype.server.path.search.distanceComparators.DistanceComparator;

public interface PathFinder
{
	Collection<? extends Point> find(Point from, Point to, DistanceComparator distanceComparator,
			CollisionDetector collisionDetector);
}
