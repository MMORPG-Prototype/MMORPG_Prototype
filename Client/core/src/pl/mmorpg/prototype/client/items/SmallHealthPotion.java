package pl.mmorpg.prototype.client.items;

import pl.mmorpg.prototype.client.exceptions.NotImplementedException;
import pl.mmorpg.prototype.client.objects.WalkingGameObject;
import pl.mmorpg.prototype.client.resources.Assets;

public class SmallHealthPotion extends Potion
{
	public SmallHealthPotion()
	{
		super(Assets.get(Assets.Textures.Items.SMALL_HEALTH_POTION));
	}

	@Override
	public void use(WalkingGameObject character)
	{
		throw new NotImplementedException();
	}

	@Override
	public String getIdentifier()
	{
		return ItemIdentifier.getObjectIdentifier(SmallHealthPotion.class);
	}

}
