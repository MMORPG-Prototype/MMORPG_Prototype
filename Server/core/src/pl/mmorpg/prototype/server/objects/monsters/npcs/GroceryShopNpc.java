package pl.mmorpg.prototype.server.objects.monsters.npcs;

import java.util.Collection;
import java.util.LinkedList;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.NpcDefaultPropertiesBuilder;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.items.potions.SmallHpPotion;
import pl.mmorpg.prototype.server.objects.items.potions.SmallMpPotion;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public class GroceryShopNpc extends ShopNpc
{
	public GroceryShopNpc(long id, PixelCollisionMap<GameObject> collisionMap,
			PlayState playState)
	{
		super(Assets.get("npc.png"), id, new NpcDefaultPropertiesBuilder().build(), collisionMap, playState,
				getShopItems());
	}

	private static Collection<ShopItemWrapper> getShopItems()
	{
		Collection<ShopItemWrapper> availableItems = new LinkedList<>();
		availableItems.add(new ShopItemWrapper(new SmallHpPotion(IdSupplier.getId()), 100));
		availableItems.add(new ShopItemWrapper(new SmallMpPotion(IdSupplier.getId()), 100));
		return availableItems;
	}
	
	

}
