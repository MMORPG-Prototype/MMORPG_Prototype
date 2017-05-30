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
	private Map<Long, Item> items = new ConcurrentHashMap<>();

	public GameContainer(long id, Collection<Item> items)
	{
		this.id = id;
		for (Item item : items)
			this.items.put(item.getId(), item);
		
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

}
