package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import pl.mmorpg.prototype.client.items.InventoryIcon;
import pl.mmorpg.prototype.client.items.ItemTextureRetriever;
import pl.mmorpg.prototype.client.resources.Assets;

public class QuestRewardIcon extends InventoryIcon
{
    private final BitmapFont font = Assets.getFont();
    
    protected int numberOfItems;
    private final String itemIdentifier;
    
    public QuestRewardIcon(String itemIdentifier, int numberOfItems)
    {
        super(ItemTextureRetriever.getTexture(itemIdentifier));
        this.itemIdentifier = itemIdentifier;
        this.numberOfItems = numberOfItems;
    }
    
    @Override
    public void draw(Batch batch, float x, float y)
    {
        super.draw(batch, x, y);
        font.getData().setScale(1.0f);
        font.draw(batch, String.valueOf(getNumberOfItems()), x + 22, y + 12);
    }
    
    public void decreaseNumberOfItems(int howMany)
    {
        numberOfItems -= howMany;
    }

    public String getItemIdentifier()
    {
        return itemIdentifier;
    }

    public int getNumberOfItems()
    {
        return numberOfItems;
    }

}
