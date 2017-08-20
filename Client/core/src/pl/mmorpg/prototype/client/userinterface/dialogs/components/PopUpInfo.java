package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.ShopItem;

public class PopUpInfo extends List<StringValueLabel<Integer>>
{
	@SuppressWarnings("unchecked")
	public PopUpInfo(ShopItem item)
	{
		super(Settings.DEFAULT_SKIN);
		StringValueLabel<Integer> price = new StringValueLabel<Integer>("Price: ", Settings.DEFAULT_SKIN, item.getPrice());
		setColor(Color.LIME);
		setSize(100, 20);	
		setItems(price);
	}

}
