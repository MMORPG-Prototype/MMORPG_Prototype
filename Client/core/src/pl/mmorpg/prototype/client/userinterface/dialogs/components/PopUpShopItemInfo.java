package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.ui.List;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.ShopItem;
import pl.mmorpg.prototype.client.userinterface.dialogs.DialogUtils;

public class PopUpShopItemInfo extends List<Object>
{
	public PopUpShopItemInfo(ShopItem item)
	{
		super(Settings.DEFAULT_SKIN);
		String name = DialogUtils.humanReadableFromItemIdentifier(item.getItem().getIdentifier());
		StringValueLabel<Integer> price = new StringValueLabel<>("Price: ", Settings.DEFAULT_SKIN, item.getPrice());
		setItems(name, price);
		setSize(8*name.length(), getItems().size*22);
		setColor(0.57f, 1f, 0.32f, 1.0f);
		this.getSelection().clear();
	}

}
