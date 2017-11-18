package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.userinterface.dialogs.ItemCounter;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.quest.QuestRewardIcon;

public class ItemQuickAccessIcon extends QuestRewardIcon
{
    private final BitmapFont font = Assets.getFont();
    
	private final String itemIdentifier;
	private ItemCounter itemCounter;

	public ItemQuickAccessIcon(String itemIdentifier, ItemCounter itemCounter)
	{
		super(itemIdentifier, itemCounter.countItems(itemIdentifier));
		this.itemCounter = itemCounter;
		this.itemIdentifier = itemIdentifier;
	}
	
	public void recalculateItemNumber()
	{
		numberOfItems = itemCounter.countItems(itemIdentifier);
	}
	
	public void decreaseItemNumber()
	{
		numberOfItems--;
	}
	
	public void increaseItemNumber(int howMany)
	{
		numberOfItems += howMany;
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
        if(numberOfItems > 0)
        	font.draw(batch, String.valueOf(numberOfItems), x + 22, y + 12);
    }
	
}
