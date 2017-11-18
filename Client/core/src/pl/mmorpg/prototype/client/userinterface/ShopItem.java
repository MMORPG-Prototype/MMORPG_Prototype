package pl.mmorpg.prototype.client.userinterface;

import pl.mmorpg.prototype.client.objects.icons.items.ItemIcon;

public class ShopItem
{
	private final ItemIcon item;
	private final int price;
	
	public ShopItem(ItemIcon item, int price)
	{
		this.item = item;
		this.price = price;
	}

	public ItemIcon getItem()
	{
		return item;
	}

	public int getPrice()
	{
		return price;
	}
}
