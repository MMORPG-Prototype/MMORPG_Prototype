
package pl.mmorpg.prototype.server.objects.monsters.loot;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.lang3.Range;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;


public class SkeletonLootGenerator extends MonsterLootGeneratorBase
{

	@Override
	public Range<Integer> getGoldRange()
	{
		return Range.between(20, 40);
	}

	@Override
	public Collection<ItemLootInfo> getItemLootInfo()
	{
		Collection<ItemLootInfo> itemLoot = new LinkedList<>();
		itemLoot.add(new ItemLootInfo.Builder()
				.itemIdentifier(ItemIdentifiers.SMALL_HP_POTION)
				.chancesOfDropping(0.5f)
				.itemNumberRange(Range.between(1, 2))
				.build());
		itemLoot.add(new ItemLootInfo.Builder()
				.itemIdentifier(ItemIdentifiers.SMALL_MP_POTION)
				.chancesOfDropping(0.5f)
				.itemNumberRange(Range.between(2, 3))
				.build());
		itemLoot.add(new ItemLootInfo.Builder()
				.itemIdentifier(ItemIdentifiers.FISH)
				.chancesOfDropping(1.0f)
				.itemNumberRange(Range.between(2, 3))
				.build());
		return itemLoot;
	}



}
