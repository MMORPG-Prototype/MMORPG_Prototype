package pl.mmorpg.prototype.server.objects.monsters.abilities;

import com.badlogic.gdx.Gdx;

public abstract class TimedAbility implements Ability
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
		currentTime += Gdx.graphics.getDeltaTime();
		if(currentTime >= timeInterval)
		{
			currentTime = 0.0f;
			return true;
		}
		return false;
	}

}
