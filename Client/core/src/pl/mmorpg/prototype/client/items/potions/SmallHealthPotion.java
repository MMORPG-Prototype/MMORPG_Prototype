package pl.mmorpg.prototype.client.items.potions;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.client.items.ItemIdentifier;
import pl.mmorpg.prototype.client.resources.Assets;

public class SmallHealthPotion extends Potion
{
    private static final Texture LOOKOUT = Assets.get("Items/SmallHealthPotion.png");

	public SmallHealthPotion(long id)
    {
        super(LOOKOUT, id);
    }
    
    public SmallHealthPotion(long id, int itemCount)
	{
    	super(LOOKOUT, id, itemCount);
	}

}
