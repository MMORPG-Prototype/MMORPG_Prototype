package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class AttackingGameObject extends WalkingGameObject
{
	private GameObject attackTarget = null; 
	
	protected AttackingGameObject(Texture textureSheet, int sheetStartX, int sheetStartY, long id)
	{
		super(textureSheet, sheetStartX, sheetStartY, id);
	}

	public AttackingGameObject(long id, CustomAnimation<TextureRegion> moveLeftAnimation,
			CustomAnimation<TextureRegion> moveRightAnimation, CustomAnimation<TextureRegion> moveDownAnimation,
			CustomAnimation<TextureRegion> moveUpAnimation)
	{
		super(id, moveLeftAnimation, moveRightAnimation, moveDownAnimation, moveUpAnimation);
	}
	
	public void lockOnTarget(GameObject target)
	{
		attackTarget = target;
	}
	
	public boolean hasLockedOnTarget()
	{
		return attackTarget != null;
	}
	
	public GameObject getTarget()
	{
		return attackTarget;
	}

}
