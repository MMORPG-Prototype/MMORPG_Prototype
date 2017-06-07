package pl.mmorpg.prototype.server.objects.monsters.npcs;

import pl.mmorpg.prototype.server.objects.items.Item;

public class ShopItemWrapper
{
	private final int price;
	private final Item item;

	public ShopItemWrapper(Item item, int price)
	{
		this.item = item;
		this.price = price;
	}

	public int getPrice()
	{
		return price;
	}

	public Item getItem()
	{
		return item;
	}
}
  