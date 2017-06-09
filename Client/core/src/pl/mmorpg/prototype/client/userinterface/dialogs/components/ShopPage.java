package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import pl.mmorpg.prototype.client.items.ItemReference;
import pl.mmorpg.prototype.client.userinterface.ShopItem;

public class ShopPage extends VerticalGroup
{
	private static final int BUTTON_ROW_LENGTH = 6;
	private static final int BUTTON_COLUMN_LENGTH = 12;
	
	private final Map<Point, InventoryField> inventoryFields = new HashMap<>();
	
	public ShopPage(ShopItem[] items)
	{
		createUIElements();
		insertItems(items);
	}

	private void createUIElements()
	{
		space(0).pad(0).padRight(5).fill();
		for (int i = 0; i < BUTTON_COLUMN_LENGTH; i++)
		{
			HorizontalGroup buttonRow = new HorizontalGroup().space(0).pad(0).fill();
			for (int j = 0; j < BUTTON_ROW_LENGTH; j++)
			{
				Point cellPosition = new Point(j, i);
				InventoryField field = new InventoryField();
				inventoryFields.put(cellPosition, field);
				buttonRow.addActor(field);
			}
			addActor(buttonRow);
		}
		padBottom(8);
	}

	private void addPopUpInfoListener(InventoryField field, ShopItem item)
	{
		field.addListener( new InputListener()
		{
			private PopUpInfo infoDialog = new PopUpInfo(item);

				
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				if(!infoDialog.hasParent())
					field.add(infoDialog);
				infoDialog.setVisible(true);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				infoDialog.setVisible(false);
				field.removeActor(infoDialog);
			}
		});
	}
	

	private void insertItems(ShopItem[] items)
	{
		Point currentPosition = new Point(0, 0);
		for(ShopItem item : items)
		{
			InventoryField field = inventoryFields.get(currentPosition);
			field.put(new ItemReference(item.getItem()));
			addPopUpInfoListener(field, item);
			currentPosition = nextPosition(currentPosition);
		}
	}

	private Point nextPosition(Point position)
	{
		position.x++;
		if(position.x >= BUTTON_ROW_LENGTH)
		{
			position.x = 0;
			position.y++;
		}
		return position;
	}
}
