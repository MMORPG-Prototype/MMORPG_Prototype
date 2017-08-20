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
import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.items.ItemReference;
import pl.mmorpg.prototype.client.items.ItemUseable;
import pl.mmorpg.prototype.client.userinterface.dialogs.InventoryDialog;

public class InventoryPage extends VerticalGroup
{
	private static final int INVENTORY_FIELDS_HEIGHT_NUMBER = 5;
	private static final int INVENTORY_FIELDS_WIDTH_NUMBER = 5;
	
	private final Map<Point, InventoryField> inventoryFields = new HashMap<>();
	private final InventoryDialog inventoryDialog;

	public InventoryPage(InventoryDialog inventoryDialog, int pageIndex)
	{
		this.inventoryDialog = inventoryDialog;

		space(0).pad(0).padRight(5).fill();
		for (int i = 0; i < INVENTORY_FIELDS_HEIGHT_NUMBER; i++)
		{
			HorizontalGroup buttonRow = new HorizontalGroup().space(0).pad(0).fill();
			for (int j = 0; j < INVENTORY_FIELDS_WIDTH_NUMBER; j++)
			{
				Point cellPosition = new Point(j, i);
				ItemInventoryPosition inventoryPosition = new ItemInventoryPosition(pageIndex, cellPosition);
				InventoryField button = createField(inventoryPosition);
				inventoryFields.put(cellPosition, button);
				buttonRow.addActor(button);
			}
			addActor(buttonRow);
		}
		padBottom(8);
	}

	private InventoryField createField(ItemInventoryPosition cellPosition)
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

	private void buttonClicked(ItemInventoryPosition cellPosition)
	{
		inventoryDialog.buttonClicked(cellPosition);
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
		{
			Item fieldItem = field.getItem();
			if (fieldItem == item)	
			{
				field.removeItem();
				return true;
			}
		}
		return false;
	}
	
	public void put(Item item, Point position)
	{
		inventoryFields.get(position).put(new ItemReference(item));
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

	public int getInventoryFieldsHeightNumber()
	{
		return INVENTORY_FIELDS_HEIGHT_NUMBER;
	}

	public int getInventoryFieldsWidthNumber()
	{
		return INVENTORY_FIELDS_WIDTH_NUMBER;
	}
}
