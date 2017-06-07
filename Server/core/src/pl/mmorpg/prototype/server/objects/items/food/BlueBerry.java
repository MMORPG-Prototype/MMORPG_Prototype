package pl.mmorpg.prototype.server.objects.items.food;

public class BlueBerry extends FoodItem
{
	private final static float BLUE_BERRY_REGENERATION_TIME = 30.0f;
	
	public BlueBerry(long id, float regenerationTime)
	{
		super(id, regenerationTime);
	}

	public BlueBerry(long id, int count, float regenerationTime)
	{
		super(id, count, regenerationTime);
	}
}
