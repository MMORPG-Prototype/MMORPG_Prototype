package pl.mmorpg.prototype.server.objects.monsters.loot;

import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Range;

import pl.mmorpg.prototype.clientservercommon.EquipmentPosition;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.objects.items.GameItemsFactory;
import pl.mmorpg.prototype.server.objects.items.Item;

public abstract class MonsterLootGeneratorBase implements MonsterLootGenerator 
{
	private static final Random random = new Random();
	
	public abstract Range<Integer> getGoldRange();
	
	public int generateGold()
	{
		Range<Integer> goldRange = getGoldRange();
		return randomValueIn(goldRange);
	}
	
	private int randomValueIn(Range<Integer> range)
	{
		int result = random.nextInt(range.getMaximum() - range.getMinimum() + 1) + range.getMinimum();
		return result;
	}
	
	public abstract Collection<ItemLootInfo> getItemLootInfo();
	
	public Collection<Item> generateItems()
	{
		Collection<ItemLootInfo> itemLootInfo = getItemLootInfo();
		return itemLootInfo.stream()
				.filter(this::willLootRandom)
				.map(this::convertToItem)
				.collect(Collectors.toList());				
	}


	private boolean willLootRandom(ItemLootInfo itemLoot)
	{
		boolean willLoot = random.nextFloat() <= itemLoot.getChancesOfDropping();
		return willLoot;
	}

	private Item convertToItem(ItemLootInfo itemLoot)
	{
		int numberOfItems = randomValueIn(itemLoot.getItemNumberRange());
		//Warning, that null not sure
		Item newItem = GameItemsFactory.produce(itemLoot.getItemIdentifier(), numberOfItems, IdSupplier.getId(), null, EquipmentPosition.NONE);
		return newItem;
	}

}
