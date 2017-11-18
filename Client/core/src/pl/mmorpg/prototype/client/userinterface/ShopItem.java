package pl.mmorpg.prototype.client.userinterface;

import pl.mmorpg.prototype.client.objects.icons.items.Item;

public class ShopItem
{
	private final Item item;
	private final int price;
	
	public ShopItem(Item item, int price)
	{
		this.item = item;
		this.price = price;
	}

	public Item getItem()
	{
		return item;
	}

	public int getPrice()
	{
		return price;
	}
}
