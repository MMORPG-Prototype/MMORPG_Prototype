package pl.mmorpg.prototype.client.userinterface.dialogs;

import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.userinterface.ShopItem;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.AutoCleanupOnCloseButtonDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.ShopPage;

public class ShopDialog extends AutoCleanupOnCloseButtonDialog
{
	public ShopDialog(String title, ActorManipulator linkedContainer, long id, ShopItem[] items)
	{
		super(title, linkedContainer, id);
		
		ShopPage shopPage = new ShopPage(items);
		this.getContentTable().add(shopPage);
		this.row();
	}

}
