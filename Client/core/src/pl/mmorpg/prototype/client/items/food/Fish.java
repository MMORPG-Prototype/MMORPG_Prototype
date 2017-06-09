package pl.mmorpg.prototype.client.items.food;

import pl.mmorpg.prototype.client.items.ItemIdentifier;
import pl.mmorpg.prototype.client.resources.Assets;

public class Fish extends FoodItem
{

	public Fish(long id)
	{
		this(id, 1);
	}
	
	public Fish(long id, int itemCount)
	{
    	super(Assets.get("Items/fish.png"), id, itemCount);
	}

	@Override
	public String getIdentifier()
	{
		return ItemIdentifier.getObjectIdentifier(Fish.class);
	}
	
}
