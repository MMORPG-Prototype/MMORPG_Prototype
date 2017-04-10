package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.mmorpg.prototype.client.objects.CustomAnimation;

public class OneLoopAnimationGraphic extends InfiniteAnimationGraphic
{
	public OneLoopAnimationGraphic(CustomAnimation<TextureRegion> animation)
	{
		super(animation);
	}

	@Override
	public boolean shouldDelete()
	{
		return animation.isAnimationFinished();
	}
}
