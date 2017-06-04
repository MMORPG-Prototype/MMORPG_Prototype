package pl.mmorpg.prototype.server.objects.containers;


import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pl.mmorpg.prototype.clientservercommon.Identifiable;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;
import pl.mmorpg.prototype.server.objects.items.Item;

@Registerable
public class GameContainer implements Identifiable
{
	private long id;
	private int goldAmount;
	private Map<Long, Item> items = new ConcurrentHashMap<>();
	
	public GameContainer(long id, Collection<Item> items, int goldAmount)
	{
		this.id = id;
		for (Item item : items)
			this.items.put(item.getId(), item);
		this.goldAmount = goldAmount;
	}
	
	@Override
	public long getId()
	{
		return id;
	}
	
	public Map<Long, Item> getItems()
	{
		return items;
	}

	public Item removeItem(long itemId)
	{
		return items.remove(itemId);
	}
	
	public int getGoldAmount()
	{
		return goldAmount;
	}
	
	public void setGoldAmount(int amount)
	{
		goldAmount = amount;
	}

}
