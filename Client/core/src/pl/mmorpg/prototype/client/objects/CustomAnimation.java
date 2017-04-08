package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

public class CustomAnimation<T> extends Animation<T>
{
	private float stateTime = 0.0f;
	
	public CustomAnimation(float frameDuration, Array<? extends T> keyFrames,
			com.badlogic.gdx.graphics.g2d.Animation.PlayMode playMode)
	{
		super(frameDuration, keyFrames, playMode);
	}

	public CustomAnimation(float frameDuration, Array<? extends T> keyFrames)
	{
		super(frameDuration, keyFrames);
	}

	public CustomAnimation(float frameDuration, T... keyFrames)
	{
		super(frameDuration, keyFrames);
	}
	
	public void update(float deltaTime)
	{
		stateTime += deltaTime;
	}
	
	public T getKeyFrame()
	{
		return getKeyFrame(stateTime);
	}
}
