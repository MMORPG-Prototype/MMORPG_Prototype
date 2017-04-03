package pl.mmorpg.prototype.client.items;

import pl.mmorpg.prototype.client.objects.GameCharacter;

@FunctionalInterface
public interface ItemUseable
{
	void use(GameCharacter character);
}
