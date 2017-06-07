package pl.mmorpg.prototype.client.userinterface.dialogs;

import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.items.ItemReference;
import pl.mmorpg.prototype.client.userinterface.ShopItem;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.AutoCleanupOnCloseButtonDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryField;

public class ShopDialog extends AutoCleanupOnCloseButtonDialog
{

	public ShopDialog(String title, ActorManipulator linkedContainer, long id, ShopItem[] items)
	{
		super(title, linkedContainer, id);
		
		for(ShopItem shopItem : items)
		{
			InventoryField field = new InventoryField();
			field.put(new ItemReference(shopItem.getItem()));
			this.getContentTable().add(field);
			row();
		}
		
	}

}
