package pl.mmorpg.prototype.client.items;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.client.exceptions.UnknownItemOrNoTextureException;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;

public class ItemTextureRetriever
{
	public static Texture getTexture(String itemIdentifier)
	{
		ItemIdentifiers identifier = ItemIdentifiers.valueOf(itemIdentifier);
		if(identifier.equals(ItemIdentifiers.BLUE_BERRY))
			return Assets.get("Items/BlueBerry.png");
		if(identifier.equals(ItemIdentifiers.FISH))
			return Assets.get("Items/fish.png");
		if(identifier.equals(ItemIdentifiers.SMALL_HP_POTION))
			return Assets.get("Items/SmallHealthPotion.png");
		if(identifier.equals(ItemIdentifiers.SMALL_MP_POTION))
			return Assets.get("Items/SmallManaPotion.png");
		if(identifier.equals(ItemIdentifiers.SWORD))
			return Assets.get("Items/metal-sword.png");
		
		throw new UnknownItemOrNoTextureException(itemIdentifier);
	}
}
