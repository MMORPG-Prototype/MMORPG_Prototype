package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.ShopItem;

public class PopUpInfo extends Dialog
{
	
	public PopUpInfo(ShopItem item)
	{
		super("Dialog", Settings.DEFAULT_SKIN);
		StringValueLabel<Integer> price = new StringValueLabel<Integer>("Price: ", getSkin(), item.getPrice());
		this.getContentTable().add(price);
	}

}
