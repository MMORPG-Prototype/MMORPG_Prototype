package pl.mmorpg.prototype.client.items;

import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.client.objects.WalkingGameObject;
import pl.mmorpg.prototype.client.resources.Assets;

public class SmallManaPotion extends Potion
{

    public SmallManaPotion(long id)
    {
        super(Assets.get(Assets.Textures.Items.SMALL_MANA_POTION), id);
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
