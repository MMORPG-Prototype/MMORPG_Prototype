package pl.mmorpg.prototype.server.objects.monsters;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;
import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class AutoTargetingMonster extends WalkingMonster
{
	private static final int rangeOfCheckingForTarget = 200;
	private static final int checkInterval = 20;
	private CollisionMap collisionMap;

	protected AutoTargetingMonster(Texture lookout, long id, MonsterProperties properties, CollisionMap collisionMap,
			PlayState playState)
	{
		super(lookout, id, properties, collisionMap, playState);
		this.collisionMap = collisionMap;
	}
	
	@Override
	public void update(float deltaTime)
	{
		if(!isTargetingAnotherMonster())
		{
			Monster monster = tryToFindTarget();
			if(monster != null)
			{
				targetMonster(monster);
				setMovingRandomly(false);
			}
		}
		else
			chaseTarget(deltaTime);
		super.update(deltaTime);
	}
	
	private void chaseTarget(float deltaTime)
	{
		Monster target = getTargetedMonster();
		if(target.getX() > getX())
			moveRight(collisionMap, deltaTime);
		else
			moveLeft(collisionMap, deltaTime);
		
		if(target.getY() > getY())
			moveUp(collisionMap, deltaTime);
		else
			moveDown(collisionMap, deltaTime);

	}

	private Monster tryToFindTarget()
	{
		int numberOfCheckingPointPerDimension = rangeOfCheckingForTarget/checkInterval;
		int searchingStartingPointX = (int)getX() - numberOfCheckingPointPerDimension/2;
		int searchingStartingPointY = (int)getX() - numberOfCheckingPointPerDimension/2;
		for(int i=0; i<numberOfCheckingPointPerDimension; i++)
			for(int j=0; j<numberOfCheckingPointPerDimension; j++)
			{
				GameObject gameObject = collisionMap.get(searchingStartingPointX + i*checkInterval, searchingStartingPointY + j*checkInterval);
				if(canBeTargeted(gameObject))
					return (Monster)gameObject;
			}
		return null;
	}

	private boolean canBeTargeted(GameObject gameObject)
	{
		return gameObject != null 
				&& gameObject != this 
				&& gameObject instanceof Monster 
				&& shouldTargetOn((Monster)gameObject);
	}

	protected abstract boolean shouldTargetOn(Monster monster);
}
