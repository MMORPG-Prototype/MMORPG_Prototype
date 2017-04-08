package pl.mmorpg.prototype.client.items;

import com.badlogic.gdx.graphics.g2d.Batch;

import pl.mmorpg.prototype.client.exceptions.NotImplementedException;

public class ItemReference extends Item
{
	private Item item;

	public ItemReference(Item item)
	{
		super(item.getTexture());
		putItem(item);
	}

	@Override
	public String getIdentifier()
	{
		throw new NotImplementedException();
	}
	
	public void removeItem()
	{
		item = null;
	}
	
	public void putItem(Item item)
	{
		setTexture(item.getTexture());
		this.item = item;
		setWidth(item.getWidth());
		setHeight(item.getHeight());
	}

	public Item getItem()
	{
		return item;
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		item.draw(batch, getX(), getY());
	}
}
