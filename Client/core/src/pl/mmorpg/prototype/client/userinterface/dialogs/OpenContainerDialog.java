package pl.mmorpg.prototype.client.userinterface.dialogs;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;

import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.items.ItemFactory;
import pl.mmorpg.prototype.client.items.ItemReference;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.AutoCleanupOnCloseButtonDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryField;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;

public class OpenContainerDialog extends AutoCleanupOnCloseButtonDialog
{
	private static final int ROW_LENGTH = 5;
	private static final int BUTTON_SIZE = 32;
	private Map<Point, InventoryField> containerFields = new HashMap<>();

	public OpenContainerDialog(CharacterItemDataPacket[] itemsToShow, String title, ActorManipulator linkedContainer, int id)
	{
		super(title, linkedContainer, id);
		
		int numberOfItems = itemsToShow.length;
		
		for(int i=0; i<numberOfItems; i += ROW_LENGTH)
		{
			HorizontalGroup buttonRow = new HorizontalGroup().space(0).pad(0).fill();
			for(int j=0; j<ROW_LENGTH; j++)
			{
				InventoryField field = new InventoryField();
				int nextIndex = i*ROW_LENGTH + j;
				if(nextIndex < numberOfItems)
				{
					Item item = ItemFactory.produceItem(itemsToShow[nextIndex]);
					field.put(new ItemReference(item));
				}
				buttonRow.addActor(field);
				containerFields.put(new Point(j, i), field);
			}
			this.getContentTable().add(buttonRow);
		}
		
		setWidth(ROW_LENGTH*BUTTON_SIZE + 50);
		setHeight(80);
	}

}
