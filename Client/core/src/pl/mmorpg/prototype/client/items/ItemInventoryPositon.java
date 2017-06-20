package pl.mmorpg.prototype.client.items;

import java.awt.Point;

public class ItemInventoryPositon
{
	public int pageNumber;
	public Point position;

	public ItemInventoryPositon()
	{
	}
	
	public ItemInventoryPositon(int pageNumber, Point position)
	{
		this.pageNumber = pageNumber;
		this.position = position;
	}
}
