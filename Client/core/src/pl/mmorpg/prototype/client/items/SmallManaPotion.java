package pl.mmorpg.prototype.client.items;

import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.client.objects.WalkingGameObject;
import pl.mmorpg.prototype.client.resources.Assets;

public class SmallManaPotion extends Potion
{
    private static final Texture LOOKOUT = Assets.get(Assets.Textures.Items.SMALL_MANA_POTION);

	public SmallManaPotion(long id)
    {
        super(LOOKOUT, id);
    }
    
    public SmallManaPotion(long id, int itemCount)
	{
    	super(LOOKOUT, id, itemCount);
	}

    @Override
    public void use(WalkingGameObject character)
    {
        Log.info("Mana potion used");
    }

    @Override
    public String getIdentifier()
    {
        return ItemIdentifier.getObjectIdentifier(SmallManaPotion.class);
    }

}
