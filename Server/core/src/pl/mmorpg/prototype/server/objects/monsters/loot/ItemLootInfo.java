package pl.mmorpg.prototype.server.objects.monsters.loot;

import org.apache.commons.lang3.Range;

import com.esotericsoftware.minlog.Log;

import lombok.Getter;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;


public class ItemLootInfo
{
	@Getter
	private ItemIdentifiers itemIdentifier;
	@Getter
	private float chancesOfDropping = 0;
	@Getter
	private Range<Integer> itemNumberRange;
	
	private ItemLootInfo()
	{		
	}
	
	public ItemLootInfo(ItemIdentifiers itemIdentifier, float chancesOfDropping, Range<Integer> itemNumberRange)
	{
		this.itemIdentifier = itemIdentifier;
		this.chancesOfDropping = chancesOfDropping;
		this.itemNumberRange = itemNumberRange;
	}
	
	public static class Builder
	{
		private ItemLootInfo itemLootInfo;

		public Builder()
		{
			itemLootInfo = new ItemLootInfo();
		}
		
		public Builder itemIdentifier(ItemIdentifiers identifier)
		{
			itemLootInfo.itemIdentifier = identifier;
			return this;
		}
		
		public Builder chancesOfDropping(float chancesOfDropping)
		{
			itemLootInfo.chancesOfDropping = chancesOfDropping;
			return this;
		}
		
		public Builder itemNumberRange(Range<Integer> itemNumberRange)
		{
			itemLootInfo.itemNumberRange = itemNumberRange;
			return this;
		}
		
		public ItemLootInfo build()
		{
			if(itemLootInfo.itemIdentifier == null)
				Log.error("Item identifier was not set!");
			if(itemLootInfo.chancesOfDropping == 0)
				Log.warn("Chances of dropping item was not set, default 0");
			if(itemLootInfo.itemNumberRange == null)
				Log.error("ItemNumberRange was not set!");
					
			return itemLootInfo;
		}
	}
	
	
	
}
