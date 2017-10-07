package pl.mmorpg.prototype.server.objects.monsters.npcs;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class ShopNpc extends Npc
{
	private Map<Long, ShopItemWrapper> availableItems;

	public ShopNpc(long id, PixelCollisionMap<GameObject> collisionMap, PlayState playState,
			Collection<ShopItemWrapper> availableItems)
	{
		super(id, collisionMap, playState);
		this.availableItems = availableItems.stream()
				.collect(Collectors.toMap((ShopItemWrapper wrapper) -> wrapper.getItem().getId(), Function.identity()));
	}

	public Collection<ShopItemWrapper> getAvailableItems()
	{
		return availableItems.values();
	}

	public ShopItemWrapper getItemWrapper(long itemId)
	{
		return availableItems.get(itemId);
	}

}
