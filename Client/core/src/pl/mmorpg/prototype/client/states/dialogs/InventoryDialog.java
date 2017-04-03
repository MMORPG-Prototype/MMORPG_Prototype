package pl.mmorpg.prototype.client.states.dialogs;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.exceptions.NoFreeFieldException;
import pl.mmorpg.prototype.client.exceptions.NoSuchInventoryFieldInPosition;
import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.dialogs.components.InventoryField;
import pl.mmorpg.prototype.client.states.helpers.Settings;

public class InventoryDialog extends CustomDialog
{
	private PlayState linkedState;

	private Map<Point, InventoryField> inventoryFields = new HashMap<>();

	public InventoryDialog(PlayState linkedState)
	{
		super("Inventory", Settings.DEFAULT_SKIN);
		this.linkedState = linkedState;
		VerticalGroup v = new VerticalGroup().space(0).pad(0).fill();
		for (int i = 0; i < 5; i++)
		{
			HorizontalGroup g = new HorizontalGroup().space(0).pad(0).fill();
			for (int j = 0; j < 5; j++)
			{
				Point cellPosition = new Point(i, j);
				InventoryField button = createButton(cellPosition);
				inventoryFields.put(cellPosition, button);
				g.addActor(button);
			}
			v.addActor(g);
		}
		v.padBottom(8);
		add(v);
		row();
	}

	private InventoryField createButton(Point cellPosition)
	{
		InventoryField button = new InventoryField();
		button.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				buttonClicked(cellPosition);
			}
		});

		return button;
	}

	@Override
	public CustomDialogs getIdentifier()
	{
		return CustomDialogs.INVENTORY;
	}

	public void buttonClicked(Point cellPosition)
	{
		linkedState.inventoryFieldClicked(inventoryFields.get(cellPosition));
	}

	public void put(Item item, Point position)
	{
		InventoryField field = inventoryFields.get(position);
		if (field == null)
			throw new NoSuchInventoryFieldInPosition(position);
		field.put(item);
	}

	public Item getItem(Point position)
	{
		InventoryField field = inventoryFields.get(position);
		return field.getItem();
	}

	public void addItem(Item item)
	{
		for(InventoryField field : inventoryFields.values())
			if(!field.hasItem())
			{
				field.put(item);
				return;
			}
		throw new NoFreeFieldException();
	}

}
