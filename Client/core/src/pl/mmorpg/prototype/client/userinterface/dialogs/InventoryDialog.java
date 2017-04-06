package pl.mmorpg.prototype.client.userinterface.dialogs;

import java.awt.Point;
import java.util.HashMap;
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
import pl.mmorpg.prototype.client.userinterface.dialogs.components.StringValueLabel;

public class InventoryDialog extends Dialog
{
	private final Map<Point, InventoryField> inventoryFields = new HashMap<>();
	private final UserInterface linkedInterface;
	private final StringValueLabel<Integer> goldLabel = new StringValueLabel<>("Gold: ", Settings.DEFAULT_SKIN);

	public InventoryDialog(UserInterface linkedInterface, Integer linkedFieldGold)
	{
		super("Inventory", Settings.DEFAULT_SKIN);
		this.linkedInterface = linkedInterface;
		Button closeButton = new CloseButton(this);
		getTitleTable().add(closeButton).size(15, 15).padRight(-5).top().right();

		VerticalGroup buttons = new VerticalGroup().space(0).pad(0).fill();
		for (int i = 0; i < 5; i++)
		{
			HorizontalGroup buttonRow = new HorizontalGroup().space(0).pad(0).fill();
			for (int j = 0; j < 5; j++)
			{
				Point cellPosition = new Point(i, j);
				InventoryField button = createButton(cellPosition);
				inventoryFields.put(cellPosition, button);
				buttonRow.addActor(button);
			}
			buttons.addActor(buttonRow);
		}
		buttons.padBottom(8);
		this.getContentTable().add(buttons);
		this.getContentTable().row();
		this.getContentTable().row();
		goldLabel.setValue(linkedFieldGold);
		goldLabel.update();
		this.getContentTable().add(goldLabel).left();
		this.getContentTable().row();
		
		this.setX(1230);
		this.setY(280);
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

	public void buttonClicked(Point cellPosition)
	{
		linkedInterface.inventoryFieldClicked(inventoryFields.get(cellPosition));
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
	
	public void updateGoldValue()
	{
		goldLabel.update();
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		if(visible)
		{
			this.setX(1230);
			this.setY(280);
		}
		super.setVisible(visible);
	}

}
