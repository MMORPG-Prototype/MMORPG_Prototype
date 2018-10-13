package pl.mmorpg.prototype.client.items;

import pl.mmorpg.prototype.client.exceptions.UnknownItemIdentifierException;
import pl.mmorpg.prototype.client.items.equipment.Armor;
import pl.mmorpg.prototype.client.items.equipment.Sword;
import pl.mmorpg.prototype.client.items.food.BlueBerry;
import pl.mmorpg.prototype.client.items.food.Fish;
import pl.mmorpg.prototype.client.items.potions.SmallHealthPotion;
import pl.mmorpg.prototype.client.items.potions.SmallManaPotion;
import pl.mmorpg.prototype.client.objects.icons.items.Item;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;

public class ItemFactory
{
	public static Item produceItem(CharacterItemDataPacket itemData)
	{
		String identifier = itemData.getIdentifier();
		long itemId = itemData.getId();
		if (identifier.equalsIgnoreCase(ItemIdentifiers.SMALL_HP_POTION.toString()))
			return new SmallHealthPotion(itemId, itemData.getCount());
		if (identifier.equalsIgnoreCase(ItemIdentifiers.SMALL_MP_POTION.toString()))
			return new SmallManaPotion(itemId, itemData.getCount());
		if (identifier.equalsIgnoreCase(ItemIdentifiers.BLUE_BERRY.toString()))
			return new BlueBerry(itemId, itemData.getCount());
		if (identifier.equalsIgnoreCase(ItemIdentifiers.FISH.toString()))
			return new Fish(itemId, itemData.getCount());
		if (identifier.equalsIgnoreCase(ItemIdentifiers.SWORD.toString()))
			return new Sword(itemId);
		if (identifier.equalsIgnoreCase(ItemIdentifiers.ARMOR.toString()))
			return new Armor(itemId);

		throw new UnknownItemIdentifierException(identifier);
	}
}
