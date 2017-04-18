package pl.mmorpg.prototype.server.objects.monsters.loot;

import java.util.Collection;

import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public interface MonsterLootGenerator
{
	int generateGold();
	
	Collection<Item> generateItems();
	
	default void generateAndApplyLoot(Monster monster)
	{
		monster.getProperties().gold = generateGold();
		Collection<Item> items = generateItems();
		monster.addItems(items);
	}
}
