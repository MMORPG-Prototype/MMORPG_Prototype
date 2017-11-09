package pl.mmorpg.prototype.server.objects.monsters.abilities;

import com.badlogic.gdx.Gdx;

public abstract class TimedAbility implements TimingAbility
{
	private final float timeInterval;
	private float currentTime = 0.0f;
	
	public TimedAbility(float timeInterval)
	{
		this.timeInterval = timeInterval;
	}
	

	@Override
	public boolean shouldUse()
	{
		if(currentTime >= timeInterval)
		{
			currentTime = 0.0f;
			return true;
		}
		return false;
	}
	
	@Override
	public void updateWithDeltaTime(float deltaTime)
	{	
		currentTime += Gdx.graphics.getDeltaTime();
	}

}
