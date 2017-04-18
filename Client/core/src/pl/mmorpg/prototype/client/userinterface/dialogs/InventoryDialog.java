package pl.mmorpg.prototype.client.userinterface.dialogs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.exceptions.NoFreeFieldException;
import pl.mmorpg.prototype.client.exceptions.NoSuchInventoryFieldInPosition;
import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.items.ItemReference;
import pl.mmorpg.prototype.client.items.ItemUseable;
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
			inventoryPages.add(new InventoryPage(this));
		currentPageButtons.addActor(inventoryPages.get(0));
		addActor(currentPageButtons);

		this.getContentTable().add(currentPageButtons);
		VerticalGroup switchButtons = new VerticalGroup().space(0).pad(0).top().padTop(-8).fill();

		for (int i = 0; i < numberOfPages; i++)
		{
			InventoryTextField switchButton = createSwitchButton(i);
			switchButtons.addActor(switchButton);
			switchPageButtons.add(switchButton);
		}
		switchPageButtons.get(0).setColor(0.5f, 0.5f, 0.5f, 1);

		this.getContentTable().add(switchButtons);
		this.getContentTable().row();
		goldLabel.setValue(linkedFieldGold);
		goldLabel.update();
		this.getContentTable().add(goldLabel).left();
		this.getContentTable().row();

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

	public void buttonClicked(InventoryField field)
	{
		if(field.hasItem())
			lastFieldWithItemClicked = field;
		linkedInterface.inventoryFieldClicked(field);
	}

	private void switchButtonClicked(int pageIndex)
	{
		for(InventoryTextField button : switchPageButtons)
			button.setColor(1, 1, 1, 1);
		
		switchPageButtons.get(pageIndex).setColor(0.5f, 0.5f, 0.5f, 1);
		currentPageButtons.clear();
		currentPageButtons.addActor(inventoryPages.get(pageIndex));
		currentPageIndex = pageIndex;
	}

	public void put(Item item, Point position)
	{
		InventoryField field = inventoryPages.get(currentPageIndex).getField(position);
		if (field == null)
			throw new NoSuchInventoryFieldInPosition(position);
		field.put(new ItemReference(item));
	}

	public Item getItem(Point position)
	{
		InventoryField field = inventoryPages.get(currentPageIndex).getField(position);
		return field.getItem();
	}

	public void addItem(Item item)
	{
		for (InventoryField field : inventoryPages.get(currentPageIndex).getAllFields())
			if (!field.hasItem())
			{
				field.put(new ItemReference(item));
				return;
			}
		throw new NoFreeFieldException();
	}

	public void updateGoldValue()
	{
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
		for(InventoryPage inventoryPage : inventoryPages)
			if(inventoryPage.removeIfHas(item))
				return true;
		return false;
	}
	
	public boolean removeIfHas(long itemId)
	{
		for(InventoryPage inventoryPage : inventoryPages)
			if(inventoryPage.removeIfHas(itemId))
				return true;
		return false;
	}
	
	public ItemUseable useItem(long itemId)
	{
		ItemUseable item = null;
		for(InventoryPage inventoryPage : inventoryPages)
			if((item = inventoryPage.useIfHas(itemId)) != null)
				return item;
		return item;
		
	}

}
