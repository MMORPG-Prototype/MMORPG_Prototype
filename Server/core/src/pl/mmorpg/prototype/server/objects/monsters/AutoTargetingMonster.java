package pl.mmorpg.prototype.server.objects.monsters;

import java.awt.Point;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.math.Rectangle;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.path.search.BestFirstPathFinder;
import pl.mmorpg.prototype.server.path.search.PathFinder;
import pl.mmorpg.prototype.server.path.search.PathSimplifier;
import pl.mmorpg.prototype.server.path.search.collisionDetectors.RestrictedAreaGameObjectCollisionDetector;
import pl.mmorpg.prototype.server.path.search.distanceComparators.DistanceComparator;
import pl.mmorpg.prototype.server.path.search.distanceComparators.ManhattanDistanceComparator;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class AutoTargetingMonster extends WalkingMonster
{
	private static final int rangeOfCheckingForTarget = 200;
	private static final int checkInterval = 20;
	private static final float recalculatePathTime = 1.0f;

	private final PixelCollisionMap<GameObject> collisionMap;
	private float timeLeftForRecalculation = recalculatePathTime;

	protected AutoTargetingMonster(Texture lookout, long id, MonsterProperties properties, Rectangle walkingBounds,
			PixelCollisionMap<GameObject> collisionMap, PlayState playState)
	{
		super(lookout, id, properties, walkingBounds, collisionMap, playState);
		this.collisionMap = collisionMap;
	}

	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		timeLeftForRecalculation -= deltaTime;
		if (!isTargetingAnotherMonster())
			whenHasNoTarget();
		else
			whenHasTarget(deltaTime);
	}

	private void whenHasNoTarget()
	{
		setMovingRandomly(true);
		Monster monster = tryToFindTarget();
		if (monster != null)
		{
			targetMonster(monster);
			findPathTo((int) monster.getX(), (int) monster.getY());
			setMovingRandomly(false);
		}
	}

	private void whenHasTarget(float deltaTime)
	{
		chaseTarget(deltaTime, collisionMap, getTargetedMonster());
		setMovingRandomly(false);
	}

	@Override
	protected void chaseTarget(float deltaTime, PixelCollisionMap<GameObject> collisionMap, Monster target)
	{
		if (timeLeftForRecalculation <= 0.0f)
		{
			findPathTo((int) target.getX(), (int) target.getY());
			timeLeftForRecalculation = recalculatePathTime;
		}
		if (!isFollowingPath())
			super.chaseTarget(deltaTime, collisionMap, target);
	}

	private Monster tryToFindTarget()
	{
		int numberOfCheckingPointPerDimension = rangeOfCheckingForTarget / checkInterval;
		int searchingStartingPointX = (int) getX() - rangeOfCheckingForTarget / 2;
		int searchingStartingPointY = (int) getY() - rangeOfCheckingForTarget / 2;
		for (int i = 0; i < numberOfCheckingPointPerDimension; i++)
			for (int j = 0; j < numberOfCheckingPointPerDimension; j++)
			{
				GameObject gameObject = collisionMap.getTopObject(searchingStartingPointX + i * checkInterval,
						searchingStartingPointY + j * checkInterval);
				if (canBeTargeted(gameObject))
					return (Monster) gameObject;
			}
		return null;
	}

	private boolean canBeTargeted(GameObject gameObject)
	{
		return gameObject instanceof Monster && gameObject != this
				&& shouldTargetOn((Monster) gameObject);
	}

	protected abstract boolean shouldTargetOn(Monster monster);
}
