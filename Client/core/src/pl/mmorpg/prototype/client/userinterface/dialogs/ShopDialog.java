package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.Stage;

import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.userinterface.ShopItem;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.AutoCleanupOnCloseButtonDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.ShopPage;

public class ShopDialog extends AutoCleanupOnCloseButtonDialog
{
	public ShopDialog(String title, long id, ShopItem[] items,
			Stage stageForPopUpInfos, UserInterface linkedInterface, PacketsSender packetSender)
	{
		super(title, linkedInterface.getDialogs(), id);

		ShopPage shopPage = new ShopPage(items, stageForPopUpInfos, linkedInterface, packetSender, id);
		this.getContentTable().add(shopPage);
		this.row();
	}

}
