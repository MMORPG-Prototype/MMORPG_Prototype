package pl.mmorpg.prototype.client.path.search;

import java.awt.Point;
import java.util.Collection;

import pl.mmorpg.prototype.client.path.search.collisionDetectors.CollisionDetector;
import pl.mmorpg.prototype.client.path.search.distanceComparators.DistanceComparator;

public interface PathFinder
{
	Collection<? extends Point> find(Point from, Point to, DistanceComparator distanceComparator,
			CollisionDetector collisionDetector);
}
