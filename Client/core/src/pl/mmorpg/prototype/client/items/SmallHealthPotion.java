package pl.mmorpg.prototype.client.items;

import pl.mmorpg.prototype.client.exceptions.NotImplementedException;
import pl.mmorpg.prototype.client.objects.GameCharacter;
import pl.mmorpg.prototype.client.resources.Assets;

public class SmallHealthPotion extends Potion
{
	private GameCharacter character;

	public SmallHealthPotion(GameCharacter character)
	{
		super(Assets.get(Assets.Textures.Items.SMALL_HEALTH_POTION), character);
		this.character = character;
	}

	@Override
	public void use()
	{
		throw new NotImplementedException();
	}

}
