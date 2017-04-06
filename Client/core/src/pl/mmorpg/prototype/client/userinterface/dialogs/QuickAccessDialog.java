package pl.mmorpg.prototype.client.userinterface.dialogs;

import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.TextInventoryField;

public class QuickAccessDialog extends Dialog
{
	private Map<Integer, Button> quickAccessButtons = new TreeMap<>();
	private UserInterface linkedState;

	public QuickAccessDialog(UserInterface linkedInterface)
	{
		super("Quick access", Settings.DEFAULT_SKIN);
		this.linkedState = linkedInterface;

		HorizontalGroup buttons = new HorizontalGroup().padBottom(8).space(4).padTop(0).fill();
		for (int i = 0; i < 12; i++)
		{
			TextInventoryField button = createButton(i);
			quickAccessButtons.put(i, button);
			buttons.addActor(button);
		}
		add(buttons);
		row();
		pack();
		this.setHeight(80);
		this.setX(400);
		this.setMovable(false);
	}

	private TextInventoryField createButton(int cellPosition)
	{
		TextInventoryField inventoryField = new TextInventoryField("F" + String.valueOf(cellPosition));
		inventoryField.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				buttonClicked(cellPosition);
			}
		});
		return inventoryField;
	}
	
	private void buttonClicked(int cellPosition)
	{
		linkedState.quickAccesButtonClicked(cellPosition);
	}
}
