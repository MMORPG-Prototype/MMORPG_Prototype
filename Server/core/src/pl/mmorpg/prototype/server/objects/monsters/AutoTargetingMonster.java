package pl.mmorpg.prototype.server.objects.monsters;

import java.awt.Point;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.path.search.BestFirstPathFinder;
import pl.mmorpg.prototype.server.path.search.PathFinder;
import pl.mmorpg.prototype.server.path.search.PathSimplifier;
import pl.mmorpg.prototype.server.path.search.collisionDetectors.RestrictedGameObjectCollisionDetector;
import pl.mmorpg.prototype.server.path.search.distanceComparators.DistanceComparator;
import pl.mmorpg.prototype.server.path.search.distanceComparators.ManhattanDistanceComparator;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class AutoTargetingMonster extends WalkingMonster
{
	private static final int rangeOfCheckingForTarget = 200;
	private static final int checkInterval = 20;
	private static final float recalculatePathTime = 1.0f;
	private static final PathFinder pathFinder = new BestFirstPathFinder();

	private PixelCollisionMap<GameObject> collisionMap;
	private LinkedList<Point> currentPath = new LinkedList<>();
	private RestrictedGameObjectCollisionDetector collisionDetector;
	private float timeLeftForRecalculation = recalculatePathTime;

	protected AutoTargetingMonster(Texture lookout, long id, MonsterProperties properties,
			PixelCollisionMap<GameObject> collisionMap, PlayState playState)
	{
		super(lookout, id, properties, collisionMap, playState);
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
			findPathTo(monster);
			setMovingRandomly(false);
		}
	}

	private void findPathTo(Monster monster)
	{
		//FIX modulos
		int modulo = 3;
		Point startPoint = new Point((int) getX() - (int)getX()%modulo, (int) getY() - (int)getY()%modulo);
		Point endPoint = new Point((int) monster.getX() - (int)monster.getX()% modulo, (int) monster.getY() - (int)monster.getY()%modulo);
		DistanceComparator distanceComparator = new ManhattanDistanceComparator(endPoint);
		this.collisionDetector = new RestrictedGameObjectCollisionDetector(collisionMap, this);
		Collection<? extends Point> path = pathFinder.find(startPoint, endPoint, distanceComparator, collisionDetector);
		System.out.println("Path");
		currentPath = new PathSimplifier().simplify(path);
		Collections.reverse(currentPath);
		//currentPath.pollFirst();
	}

	private void whenHasTarget(float deltaTime)
	{
		chaseTarget(deltaTime, collisionMap, getTargetedMonster());
		setMovingRandomly(false);
	}

	@Override
	protected void chaseTarget(float deltaTime, PixelCollisionMap<GameObject> collisionMap, Monster target)
	{
		if(timeLeftForRecalculation <= 0.0f)
		{
			findPathTo(target);
			timeLeftForRecalculation = recalculatePathTime;
		}
		if(currentPath.isEmpty())
		{
			super.chaseTarget(deltaTime, collisionMap, target);
			return;
		}
		Point nearestTarget = currentPath.getFirst();
		makeStepToPoint(deltaTime, collisionMap, nearestTarget.x, nearestTarget.y);
		if(nearPoint(nearestTarget))
			currentPath.pollFirst();
	}

	private boolean nearPoint(Point nearestTarget)
	{
		return Math.abs(getX() - nearestTarget.x) + Math.abs(getY() - nearestTarget.y) < 2.0f;
	}

	private Monster tryToFindTarget()
	{
		int numberOfCheckingPointPerDimension = rangeOfCheckingForTarget / checkInterval;
		int searchingStartingPointX = (int) getX() - numberOfCheckingPointPerDimension / 2;
		int searchingStartingPointY = (int) getY() - numberOfCheckingPointPerDimension / 2;
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
		return gameObject != null && gameObject != this && gameObject instanceof Monster
				&& shouldTargetOn((Monster) gameObject);
	}
	
	@Override
	public void render(Batch batch)
	{
		Texture debugTexture = Assets.get("debug.png");
		for(Point point : currentPath)
			batch.draw(debugTexture, point.x, point.y, 3, 3);
		super.render(batch);
	}

	protected abstract boolean shouldTargetOn(Monster monster);
}
