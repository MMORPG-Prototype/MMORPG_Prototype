package pl.mmorpg.prototype.server.objects.monsters.npcs;

import java.util.Collection;

public interface Shop
{
	Collection<ShopItemWrapper> getAvailableItems();

	ShopItemWrapper getItemWrapper(long itemId);
}
