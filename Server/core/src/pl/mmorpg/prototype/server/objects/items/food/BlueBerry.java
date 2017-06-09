package pl.mmorpg.prototype.server.objects.items.food;

public class BlueBerry extends FoodItem
{
	private final static float BLUE_BERRY_REGENERATION_TIME = 10.0f;
	
	public BlueBerry(long id)
	{
		super(id, BLUE_BERRY_REGENERATION_TIME);
	}

	public BlueBerry(long id, int count)
	{
		super(id, count, BLUE_BERRY_REGENERATION_TIME);
	}
}
