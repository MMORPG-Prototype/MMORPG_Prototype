package pl.mmorpg.prototype.server.objects.items.food;

public class Fish extends FoodItem
{
	private final static float FISH_REGENERATION_TIME = 20.0f;
	
	public Fish(long id)
	{
		super(id, FISH_REGENERATION_TIME);
	}

	public Fish(long id, int count)
	{
		super(id, count, FISH_REGENERATION_TIME);
	}
}
