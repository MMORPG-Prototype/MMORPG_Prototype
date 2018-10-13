package pl.mmorpg.prototype.client.items.potions;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.client.items.ItemIdentifier;
import pl.mmorpg.prototype.client.resources.Assets;

public class SmallManaPotion extends Potion
{
    private static final Texture LOOKOUT = Assets.get("Items/SmallManaPotion.png");

	public SmallManaPotion(long id)
    {
        super(LOOKOUT, id);
    }
    
    public SmallManaPotion(long id, int itemCount)
	{
    	super(LOOKOUT, id, itemCount);
	}
}
