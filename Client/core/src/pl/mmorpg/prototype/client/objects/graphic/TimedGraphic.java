package pl.mmorpg.prototype.client.objects.graphic;

public abstract class TimedGraphic extends GraphicGameObject
{
	private final float maxLivingTime;
	private float currentLivingTime = 0.0f;

	public TimedGraphic(float maxLivingTime)
	{
		this.maxLivingTime = maxLivingTime;
	}
	
	@Override
	public void update(float deltaTime)
	{
		this.currentLivingTime += deltaTime;
		super.update(deltaTime);
	}
	
	@Override
	public boolean shouldDelete()
	{
		return currentLivingTime > maxLivingTime;
	}

}
