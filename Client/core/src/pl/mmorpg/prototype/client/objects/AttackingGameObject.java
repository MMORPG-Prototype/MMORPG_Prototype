package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.monsters.TextureSheetAnimationInfo;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;

public abstract class AttackingGameObject extends WalkingGameObject
{
    private GameObject attackTarget = null;

    protected AttackingGameObject(TextureSheetAnimationInfo sheetInfo, long id, CollisionMap<GameObject> linkedCollisionMap, PacketHandlerRegisterer registerer)
    {
        super(sheetInfo, id, linkedCollisionMap, registerer);
    }

	public AttackingGameObject(long id, PacketHandlerRegisterer registerer, CustomAnimation<TextureRegion> moveLeftAnimation,
			CustomAnimation<TextureRegion> moveRightAnimation, CustomAnimation<TextureRegion> moveDownAnimation,
			CustomAnimation<TextureRegion> moveUpAnimation, CollisionMap<GameObject> linkedCollisionMap)
	{
		super(id, registerer, moveLeftAnimation, moveRightAnimation, moveDownAnimation, moveUpAnimation, linkedCollisionMap);
	}

    public void lockOnTarget(GameObject target)
    {
        attackTarget = target;
    }

    public boolean hasLockedOnTarget()
    {
        return attackTarget != null;
    }

    public boolean hasLockedOnTarget(GameObject target)
    {
        return attackTarget == target;
    }

    public GameObject getTarget()
    {
        return attackTarget;
    }

    public void releaseTarget()
    {
        attackTarget = null;
    }

}
