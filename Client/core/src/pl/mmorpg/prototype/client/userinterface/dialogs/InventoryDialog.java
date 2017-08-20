package pl.mmorpg.prototype.client.userinterface.dialogs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.exceptions.NoFreeFieldException;
import pl.mmorpg.prototype.client.exceptions.NoSuchFieldException;
import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.items.ItemReference;
import pl.mmorpg.prototype.client.items.ItemUseable;
import pl.mmorpg.prototype.client.items.StackableItem;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.CloseButton;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryField;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryPage;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryTextField;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.StringValueLabel;

public class InventoryDialog extends Dialog
{
	private static final int numberOfPages = 5;
	private int currentPageIndex = 0;
	private final List<InventoryPage> inventoryPages = new ArrayList<>(numberOfPages);
	private final VerticalGroup currentPageButtons = new VerticalGroup();
	private final List<InventoryTextField> switchPageButtons = new ArrayList<>(numberOfPages);
	private final UserInterface linkedInterface;
	private InventoryField lastFieldWithItemClicked = null;
	private final StringValueLabel<Integer> goldLabel = new StringValueLabel<>("Gold: ", Settings.DEFAULT_SKIN);

	public InventoryDialog(UserInterface linkedInterface, Integer linkedFieldGold)
	{
		super("Inventory", Settings.DEFAULT_SKIN);
		this.linkedInterface = linkedInterface;

		Button closeButton = new CloseButton(this);
		getTitleTable().add(closeButton).size(15, 15).padRight(-5).top().right();

		for (int i = 0; i < numberOfPages; i++)
			inventoryPages.add(new InventoryPage(this, i));
		currentPageButtons.addActor(inventoryPages.get(0));
		addActor(currentPageButtons);

		Table contentTable = this.getContentTable();
		contentTable.add(currentPageButtons);
		VerticalGroup switchButtons = new VerticalGroup().space(0).pad(0).top().padTop(-8).fill();

		for (int i = 0; i < numberOfPages; i++)
		{
			InventoryTextField switchButton = createSwitchButton(i);
			switchButtons.addActor(switchButton);
			switchPageButtons.add(switchButton);
		}
		switchPageButtons.get(0).setColor(0.5f, 0.5f, 0.5f, 1);

		contentTable.add(switchButtons);
		contentTable.row();
		goldLabel.setValue(linkedFieldGold);
		goldLabel.update();
		contentTable.add(goldLabel).left();
		contentTable.row();

		this.setX(1230);
		this.setY(43);
		this.pack();
	}

	private InventoryTextField createSwitchButton(int pageIndex)
	{
		InventoryTextField switchButton = new InventoryTextField(String.valueOf(pageIndex + 1));
		switchButton.setTextShiftX(-4);
		switchButton.setTextShiftY(6);
		switchButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				switchButtonClicked(pageIndex);
			}
		});
		return switchButton;
	}

	public void buttonClicked(ItemInventoryPosition cellPosition)
	{
		InventoryField field = getField(cellPosition);
		if (field.hasItem())
			lastFieldWithItemClicked = field;
		linkedInterface.inventoryFieldClicked(field, cellPosition);
	}

	private void switchButtonClicked(int pageIndex)
	{
		for (InventoryTextField button : switchPageButtons)
			button.setColor(1, 1, 1, 1);

		switchPageButtons.get(pageIndex).setColor(0.5f, 0.5f, 0.5f, 1);
		currentPageButtons.clear();
		currentPageButtons.addActor(inventoryPages.get(pageIndex));
		currentPageIndex = pageIndex;
	}

	public Item getItem(Point position)
	{
		InventoryField field = inventoryPages.get(currentPageIndex).getField(position);
		return field.getItem();
	}

	public void addItem(StackableItem item)
	{
		for (InventoryPage inventoryPage : inventoryPages)
		{
			for (InventoryField field : inventoryPage.getAllFields())
			{
				Item fieldItem = field.getItem();
				if (fieldItem != null && fieldItem.getIdentifier().equals(item.getIdentifier()))
				{
					((StackableItem) fieldItem).stackWith(item);
					return;
				}
			}
		}

		addItem((Item) item);
	}

	private InventoryField getField(ItemInventoryPosition fieldPosition)
	{
		return inventoryPages.get(fieldPosition.getPageNumber()).getField(new Point(fieldPosition.getPosition().x, fieldPosition.getPosition().y));
	}

	public void addItem(Item item)
	{
		ItemInventoryPosition freeFieldPosition = getFreeInventoryPosition();
		InventoryField freeField = getField(freeFieldPosition);
		freeField.put(new ItemReference(item));
	}

	public void addItem(Item newItem, ItemInventoryPosition inventoryPosition)
	{
		getField(inventoryPosition).put(new ItemReference(newItem));
	}

	public void updateGoldValue(int goldAmount)
	{
		goldLabel.setValue(goldAmount);
		goldLabel.update();
	}

	@Override
	public void setVisible(boolean visible)
	{
		if (visible)
		{
			this.setX(1230);
			this.setY(43);
		}
		super.setVisible(visible);
	}

	public InventoryField getLastFieldWithItemClicked()
	{
		return lastFieldWithItemClicked;
	}

	public boolean removeIfHas(Item item)
	{
		for (InventoryPage inventoryPage : inventoryPages)
			if (inventoryPage.removeIfHas(item))
				return true;
		return false;
	}

	public ItemUseable useItem(long itemId)
	{
		ItemUseable item = null;
		for (InventoryPage inventoryPage : inventoryPages)
			if ((item = inventoryPage.useIfHas(itemId)) != null)
				return item;
		return item;
	}
	
	public ItemInventoryPosition getFreeInventoryPosition()
	{
		for(int pageNumber=0; pageNumber<inventoryPages.size(); pageNumber++)
		{
			InventoryPage currentPage = inventoryPages.get(pageNumber);
			for(int i=0; i<currentPage.getInventoryFieldsHeightNumber(); i++)
				for(int j=0; j<currentPage.getInventoryFieldsWidthNumber(); j++)
				{
					Point fieldPosition = new Point(j, i);
					InventoryField field = currentPage.getField(fieldPosition);
					if(!field.hasItem())
						return new ItemInventoryPosition(pageNumber, fieldPosition);
				}
		}
		throw new NoFreeFieldException();
	}

	public ItemInventoryPosition getDesiredItemPositionFor(Item item)
	{
		if(item instanceof StackableItem)
			return getFieldWithSuiteTypeStackableItemFor(item);
		else
			return getFreeInventoryPosition();
	}
	
	public ItemInventoryPosition getFieldWithSuiteTypeStackableItemFor(Item item)
	{
		try
		{
			return getFieldWithSameTypeItemOnCurrentPage(item);
		}
		catch(NoSuchFieldException e)
		{
			return getFreeInventoryPosition();
		}
	}

	private ItemInventoryPosition getFieldWithSameTypeItemOnCurrentPage(Item item)
	{
		// May want to change code to stack with items on other pages 
		InventoryPage currentPage = inventoryPages.get(0);
		for(int i=0; i<currentPage.getInventoryFieldsHeightNumber(); i++)
			for(int j=0; j<currentPage.getInventoryFieldsWidthNumber(); j++)
			{
				Point fieldPosition = new Point(i, j);
				InventoryField field = currentPage.getField(fieldPosition);
				Item fieldItem = field.getItem();
				if(fieldItem != null && fieldItem.getIdentifier().equals(item.getIdentifier()))
					return new ItemInventoryPosition(0, fieldPosition);
			}
		throw new NoSuchFieldException();
	}

	public void repositionItem(ItemInventoryPosition sourcePosition, ItemInventoryPosition destinationPosition)
	{
		InventoryField sourceField = getField(sourcePosition);
		InventoryField destinationField = getField(destinationPosition);
		Item targetItem = sourceField.getItem();
		sourceField.removeItem();
		destinationField.put(new ItemReference(targetItem));
	}

	public void swapItems(ItemInventoryPosition firstPosition, ItemInventoryPosition secondPosition)
	{
		InventoryField firstField = getField(firstPosition);
		InventoryField secondField = getField(secondPosition);
		Item firstItem = firstField.getItem();
		Item secondItem = secondField.getItem();
		firstField.put(new ItemReference(secondItem));
		secondField.put(new ItemReference(firstItem));
	}

}
