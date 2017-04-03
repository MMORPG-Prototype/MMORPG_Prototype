package pl.mmorpg.prototype.client.items;

import pl.mmorpg.prototype.client.objects.GameCharacter;
import pl.mmorpg.prototype.client.resources.Assets;

public class SmallManaPotion extends Potion
{

	public SmallManaPotion()
	{
		super(Assets.get(Assets.Textures.Items.SMALL_MANA_POTION));
	}

	@Override
	public void use(GameCharacter character)
	{

	}

	@Override
	public String getIdentifier()
	{
		return ItemIdentifier.getObjectIdentifier(SmallManaPotion.class);
	}

}
