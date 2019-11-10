package pl.mmorpg.prototype.server.objects.monsters.npcs;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.badlogic.gdx.math.Rectangle;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class ShopNpc extends Npc implements Shop
{
	private Map<Long, ShopItemWrapper> availableItems;

	public ShopNpc(String name, long id, Rectangle walkingBounds, PixelCollisionMap<GameObject> collisionMap, PlayState playState,
			Collection<ShopItemWrapper> availableItems)
	{
		super(name, id, walkingBounds, collisionMap, playState);
		this.availableItems = availableItems.stream()
				.collect(Collectors.toMap((ShopItemWrapper wrapper) -> wrapper.getItem().getId(), Function.identity()));
	}

	@Override
	public Collection<ShopItemWrapper> getAvailableItems()
	{
		return availableItems.values();
	}

	@Override
	public ShopItemWrapper getItemWrapper(long itemId)
	{
		return availableItems.get(itemId);
	}

}
