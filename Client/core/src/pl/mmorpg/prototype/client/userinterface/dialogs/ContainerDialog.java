package pl.mmorpg.prototype.client.userinterface.dialogs;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.items.ItemFactory;
import pl.mmorpg.prototype.client.items.ItemPositionSupplier;
import pl.mmorpg.prototype.client.objects.icons.items.Item;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.AutoCleanupOnCloseButtonDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.ButtonCreator;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.StringValueLabel;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory.ButtonField;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TakeItemFromContainerPacket;

public class ContainerDialog extends AutoCleanupOnCloseButtonDialog
{
	private static final int ROW_LENGTH = 5;
	private static final int BUTTON_SIZE = 32;
	private final Map<Point, ButtonField<Item>> containerFields = new HashMap<>();
	private final StringValueLabel<Integer> goldLabel;

	public ContainerDialog(CharacterItemDataPacket[] itemsToShow, int gold, String title,
			ActorManipulator linkedContainer, PacketsSender packetsSender, long containerId,
			ItemPositionSupplier userInventoryTakeItemPositionSupplier)
	{
		super(title, linkedContainer, containerId);

		int numberOfItems = itemsToShow.length;

		addFieldRow(itemsToShow, packetsSender, numberOfItems, 0, userInventoryTakeItemPositionSupplier);
		for (int i = ROW_LENGTH; i < numberOfItems; i += ROW_LENGTH)
		{
			this.row();
			addFieldRow(itemsToShow, packetsSender, numberOfItems, i, userInventoryTakeItemPositionSupplier);
		}
		this.row();

		goldLabel = new StringValueLabel<>("Gold: ", Settings.DEFAULT_SKIN, gold);
		this.getButtonTable().add(goldLabel).align(Align.right);
		Button takeGoldButton = ButtonCreator.createTextButton("Take",
				() -> packetsSender.send(PacketsMaker.makeTakingGoldFromContainerPacket(containerId)));

		this.getButtonTable().add(takeGoldButton).align(Align.bottomRight);
		setWidth(ROW_LENGTH * BUTTON_SIZE + 50);
		setHeight(120);
	}

	private void addFieldRow(CharacterItemDataPacket[] itemsToShow, PacketsSender packetsSender, int numberOfItems,
			int i, ItemPositionSupplier userInventoryTakeItemPositionSupplier)
	{
		HorizontalGroup buttonRow = new HorizontalGroup().space(0).pad(0).fill();
		for (int j = 0; j < ROW_LENGTH; j++)
		{
			ButtonField<Item> field = createField(packetsSender, userInventoryTakeItemPositionSupplier);
			int nextIndex = i * ROW_LENGTH + j;
			buttonRow.addActor(field);
			containerFields.put(new Point(j, i), field);
			if (nextIndex < numberOfItems)
			{
				Item item = ItemFactory.produceItem(itemsToShow[nextIndex]);
				field.put(item);
			}
		}
		this.getContentTable().add(buttonRow);
	}

	private ButtonField<Item> createField(PacketsSender packetsSender,
			ItemPositionSupplier userInventoryTakeItemPositionSupplier)
	{
		ButtonField<Item> field = new ButtonField<Item>();
		field.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				if (field.hasContent())
				{
					Item item = field.getContent();
					TakeItemFromContainerPacket packet = PacketsMaker.makeTakeItemFromContainerPacket(
							ContainerDialog.this.getId(), item.getId(),
							userInventoryTakeItemPositionSupplier.get(item));
					packetsSender.send(packet);
				}
			}
		});
		return field;
	}

	public boolean removeItem(long itemId)
	{
		for (ButtonField<Item> field : containerFields.values())
			if (field.hasContent() && field.getContent().getId() == itemId)
			{
				field.removeContent();
				return true;
			}
		return false;
	}

	public void updateGoldByDecreasingBy(int amount)
	{
		goldLabel.setValue(goldLabel.getValue() - amount);
		goldLabel.update();
	}

}
