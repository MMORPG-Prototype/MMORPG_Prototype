package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.clientservercommon.EquipmentPosition;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.exceptions.UnknownItemTypeException;
import pl.mmorpg.prototype.server.objects.items.equipment.Armor;
import pl.mmorpg.prototype.server.objects.items.equipment.EquipableItem;
import pl.mmorpg.prototype.server.objects.items.equipment.Sword;
import pl.mmorpg.prototype.server.objects.items.food.BlueBerry;
import pl.mmorpg.prototype.server.objects.items.food.Fish;
import pl.mmorpg.prototype.server.objects.items.potions.SmallHpPotion;
import pl.mmorpg.prototype.server.objects.items.potions.SmallMpPotion;

public class GameItemsFactory
{

    public static Item produce(CharacterItem item, long gameId)
    {
        return produce(item.getIdentifier(), item.getCount(), gameId, item.getInventoryPosition(), item.getEquipmentPosition());
    }
    
    public static Item produce(ItemIdentifiers identifier, int itemCount, long gameId,
			InventoryPosition inventoryPosition, EquipmentPosition equipmentPosition)
    {
    	Item result;
        if(identifier.equals(ItemIdentifiers.SMALL_HP_POTION))
        	result = new SmallHpPotion(gameId, itemCount);
        else if(identifier.equals(ItemIdentifiers.SMALL_MP_POTION))
        	result = new SmallMpPotion(gameId, itemCount);
        else if(identifier.equals(ItemIdentifiers.BLUE_BERRY))
        	result = new BlueBerry(gameId, itemCount);
        else if(identifier.equals(ItemIdentifiers.FISH))
        	result = new Fish(gameId, itemCount);
        else if (identifier.equals(ItemIdentifiers.SWORD))
        	result = new Sword(gameId);
        else if(identifier.equals(ItemIdentifiers.ARMOR))
        	result = new Armor(gameId);
        else
        	throw new UnknownItemTypeException(identifier);
        
        result.setInventoryPosition(inventoryPosition);
        if(result instanceof EquipableItem)
			((EquipableItem) result).setEquipmentPosition(equipmentPosition);
        return result;
    }

}
