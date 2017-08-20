package pl.mmorpg.prototype.client.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.userinterface.dialogs.ItemCounter;

public class QuickAccesIcon extends InventoryIcon
{
    private final BitmapFont font = Assets.getFont();
    
	private String itemIdentifier;
	private ItemCounter itemCounter;
    private int numberOfItems;

	public QuickAccesIcon(String itemIdentifier, ItemCounter itemCounter)
	{
		super(ItemTextureRetriever.getTexture(itemIdentifier));
		this.itemCounter = itemCounter;
		this.setItemIdenfier(itemIdentifier);
		numberOfItems = 0;
		recalculateItemNumber();
	}
	
	public void recalculateItemNumber()
	{
		numberOfItems = itemCounter.countItems(itemIdentifier);
	}

	public void setItemIdenfier(String itemIdenfier)
	{
		this.itemIdentifier = itemIdenfier;
	}
	
	public String getItemIdenfier()
	{
		return itemIdentifier;
	}
	
	@Override
	public void draw(Batch batch, float x, float y)
    {
		super.draw(batch, x, y);
        font.getData().setScale(1.0f);
        if(numberOfItems > 1)
        	font.draw(batch, String.valueOf(numberOfItems), x + 22, y + 12);
    }

	
}
