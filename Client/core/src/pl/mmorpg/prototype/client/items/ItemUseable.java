package pl.mmorpg.prototype.client.items;

import pl.mmorpg.prototype.client.objects.WalkingGameObject;

@FunctionalInterface
public interface ItemUseable
{
	void use(WalkingGameObject character);
}
