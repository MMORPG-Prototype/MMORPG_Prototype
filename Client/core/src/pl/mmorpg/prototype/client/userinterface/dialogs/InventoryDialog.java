package pl.mmorpg.prototype.client.userinterface.dialogs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.exceptions.NoFreeFieldException;
import pl.mmorpg.prototype.client.exceptions.NoSuchInventoryFieldInPosition;
import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.CloseButton;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryField;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryTextField;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.StringValueLabel;

public class InventoryDialog extends Dialog
{
	private static final int numberOfPages = 5;
	private int currentPageIndex = 0;
	private final List<Map<Point, InventoryField>> inventoryFields = new ArrayList<>(numberOfPages);
	private final List<VerticalGroup> rawButtons = new ArrayList<>(numberOfPages);
	private final VerticalGroup currentPageButtons = new VerticalGroup();
	private final UserInterface linkedInterface;
	private final StringValueLabel<Integer> goldLabel = new StringValueLabel<>("Gold: ", Settings.DEFAULT_SKIN);

	public InventoryDialog(UserInterface linkedInterface, Integer linkedFieldGold)
	{
		super("Inventory", Settings.DEFAULT_SKIN);
		this.linkedInterface = linkedInterface;

		for (int i = 0; i < numberOfPages; i++)
			inventoryFields.add(new HashMap<>());

		Button closeButton = new CloseButton(this);
		getTitleTable().add(closeButton).size(15, 15).padRight(-5).top().right();

		for (int k = 0; k < numberOfPages; k++)
		{
			VerticalGroup buttons = new VerticalGroup().space(0).pad(0).padRight(5).fill();
			for (int i = 0; i < 5; i++)
			{
				HorizontalGroup buttonRow = new HorizontalGroup().space(0).pad(0).fill();
				for (int j = 0; j < 5; j++)
				{
					Point cellPosition = new Point(i, j);
					InventoryField button = createButton(cellPosition);
					inventoryFields.get(k).put(cellPosition, button);
					buttonRow.addActor(button);
				}
				buttons.addActor(buttonRow);
			}
			buttons.padBottom(8);
			rawButtons.add(buttons);
		}
		
		currentPageButtons.addActor(rawButtons.get(0));
		this.getContentTable().add(currentPageButtons);
		VerticalGroup switchButtons = new VerticalGroup().space(0).pad(0).top().padTop(-8).fill();

		for (int i = 0; i < numberOfPages; i++)
		{
			InventoryTextField switchButton = createSwitchButton(i);
			switchButtons.addActor(switchButton);
		}

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

	private InventoryTextField createSwitchButton(int pageIndex)
	{
		InventoryTextField switchButton = new InventoryTextField(String.valueOf(pageIndex));
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

	private void buttonClicked(Point cellPosition)
	{
		linkedInterface.inventoryFieldClicked(inventoryFields.get(currentPageIndex).get(cellPosition));
	}
	
	private void switchButtonClicked(int pageIndex)
	{
		currentPageButtons.clear();
		currentPageButtons.addActor(rawButtons.get(pageIndex));
		currentPageIndex = pageIndex;
	}

	public void put(Item item, Point position)
	{
		InventoryField field = inventoryFields.get(currentPageIndex).get(position);
		if (field == null)
			throw new NoSuchInventoryFieldInPosition(position);
		field.put(item);
	}

	public Item getItem(Point position)
	{
		InventoryField field = inventoryFields.get(currentPageIndex).get(position);
		return field.getItem();
	}

	public void addItem(Item item)
	{
		for (InventoryField field : inventoryFields.get(currentPageIndex).values())
			if (!field.hasItem())
			{
				field.put(item);
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

}
