package pl.mmorpg.prototype.server.objects.containers;


import java.util.Collection;

import pl.mmorpg.prototype.clientservercommon.Identifiable;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;
import pl.mmorpg.prototype.server.objects.items.Item;

@Registerable
public class GameContainer implements Identifiable
{
	private long id;
	private Collection<Item> items;

	public GameContainer(long id, Collection<Item> items)
	{
		this.id = id;
		this.items = items;
	}
	
	@Override
	public long getId()
	{
		return id;
	}
	
	public Collection<Item> getItems()
	{
		return items;
	}

}
