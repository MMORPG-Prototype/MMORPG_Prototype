package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.items.ItemReference;
import pl.mmorpg.prototype.client.userinterface.ShopItem;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.ShopBuyingDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.listeners.ActorPopUpHideListener;

public class ShopPage extends VerticalGroup
{
	private static final int BUTTON_ROW_LENGTH = 6;
	private static final int BUTTON_COLUMN_LENGTH = 12;
	
	private final Map<Point, InventoryField> inventoryFields = new HashMap<>();
	private UserInterface linkedInterface;
	
	public ShopPage(ShopItem[] items, Stage stageForPopUpInfo, UserInterface linkedInterface)
	{
		createUIElements();
		insertItemsAndAddListeners(items, stageForPopUpInfo);
		this.linkedInterface = linkedInterface;
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

	private void insertItemsAndAddListeners(ShopItem[] items, Stage stageForPopUpInfo)
	{
		Point currentPosition = new Point(0, 0);
		for(ShopItem item : items)
		{
			InventoryField field = inventoryFields.get(currentPosition);
			field.put(new ItemReference(item.getItem()));
			addPopUpInfoListener(field, item, stageForPopUpInfo);
			addListenerForUsingField(field, item);
			currentPosition = nextPosition(currentPosition);
		}
	}

	private void addPopUpInfoListener(InventoryField field, ShopItem item, Stage stageForPopUpInfo)
	{
		PopUpInfo infoDialog = new PopUpInfo(item);
		InputListener popUpHideListener = new ActorPopUpHideListener(stageForPopUpInfo, infoDialog);
		field.addListener(popUpHideListener);
	}
	
	private void addListenerForUsingField(InventoryField field, ShopItem item)
	{
		field.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				ShopBuyingDialog dialog = new ShopBuyingDialog(item, linkedInterface);
				linkedInterface.addDialog(dialog);
				
			}
		});
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
