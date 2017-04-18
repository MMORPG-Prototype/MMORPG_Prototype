package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.items.ItemUseable;
import pl.mmorpg.prototype.client.userinterface.dialogs.InventoryDialog;

public class InventoryPage extends VerticalGroup
{
	private Map<Point, InventoryField> inventoryFields = new HashMap<>();
	private InventoryDialog inventoryDialog;

	public InventoryPage(InventoryDialog inventoryDialog)
	{
		this.inventoryDialog = inventoryDialog;

		space(0).pad(0).padRight(5).fill();
		for (int i = 0; i < 5; i++)
		{
			HorizontalGroup buttonRow = new HorizontalGroup().space(0).pad(0).fill();
			for (int j = 0; j < 5; j++)
			{
				Point cellPosition = new Point(i, j);
				InventoryField button = createField(cellPosition);
				inventoryFields.put(cellPosition, button);
				buttonRow.addActor(button);
			}
			addActor(buttonRow);
		}
		padBottom(8);
	}

	private InventoryField createField(Point cellPosition)
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

	private void buttonClicked(Point cellPosition)
	{
		inventoryDialog.buttonClicked(inventoryFields.get(cellPosition));
	}

	public InventoryField getField(Point point)
	{
		return inventoryFields.get(point);
	}

	public Collection<InventoryField> getAllFields()
	{
		return inventoryFields.values();
	}

	public boolean removeIfHas(Item item)
	{
		for (InventoryField field : inventoryFields.values())
			if (field.getItem() == item)
			{
				field.removeItem();
				return true;
			}
		return false;
	}

	public boolean removeIfHas(long itemId)
	{
		for (InventoryField field : inventoryFields.values())
		{
			Item item = field.getItem();
			if (item != null && item.getId() == itemId)
			{
				field.removeItem();
				return true;
			}
		}

		return false;
	}

	public Item getItem(long itemId)
	{
		for (InventoryField field : inventoryFields.values())
		{
			Item item = field.getItem();
			if (item != null && item.getId() == itemId)
				return item;
		}
		return null;
	}

	public ItemUseable useIfHas(long itemId)
	{
		for (InventoryField field : inventoryFields.values())
		{
			Item item = field.getItem();
			if (item != null && item.getId() == itemId)
			{
				ItemUseable itemUseable = (ItemUseable)item;
				itemUseable.useIterfaceUpdate();
				if(item.shouldBeRemoved())
					field.removeItem();
				return itemUseable;
			}
		}
		return null;
	}
}
