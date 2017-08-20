package pl.mmorpg.prototype.client.items;

import java.awt.Point;

public class ItemInventoryPosition
{
	private final int pageNumber;
	private final Point position;
	
	public ItemInventoryPosition(int pageNumber, Point position)
	{
		this.pageNumber = pageNumber;
		this.position = position;
	}

	public int getPageNumber()
	{
		return pageNumber;
	}

	public Point getPosition()
	{
		return position;
	}
}
