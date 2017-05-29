package pl.mmorpg.prototype.server.objects.monsters;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class AutoTargetingMonster extends WalkingMonster
{
    private static final int rangeOfCheckingForTarget = 200;
    private static final int checkInterval = 20;
    private static final float stopChasingDistance = 3.0f;
    private PixelCollisionMap<GameObject> collisionMap;

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
		    setMovingRandomly(false);
		}
	}
	
	private void whenHasTarget(float deltaTime)
	{
		chaseTarget(deltaTime, collisionMap, getTargetedMonster());
		setMovingRandomly(false);
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

    protected abstract boolean shouldTargetOn(Monster monster);
}
