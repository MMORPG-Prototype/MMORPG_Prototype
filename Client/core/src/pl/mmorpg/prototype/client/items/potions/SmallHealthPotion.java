package pl.mmorpg.prototype.client.items.potions;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.client.items.ItemIdentifier;
import pl.mmorpg.prototype.client.resources.Assets;

public class SmallHealthPotion extends Potion
{
    private static final Texture LOOKOUT = Assets.get(Assets.Textures.Items.SMALL_HEALTH_POTION);

	public SmallHealthPotion(long id)
    {
        super(LOOKOUT, id);
    }
    
    public SmallHealthPotion(long id, int itemCount)
	{
    	super(LOOKOUT, id, itemCount);
	}


    @Override
    public String getIdentifier()
    {
        return ItemIdentifier.getObjectIdentifier(SmallHealthPotion.class);
    }

}