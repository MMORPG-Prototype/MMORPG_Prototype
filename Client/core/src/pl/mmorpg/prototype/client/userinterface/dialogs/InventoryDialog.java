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
import pl.mmorpg.prototype.client.items.ItemFactory;
import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.items.ItemUseable;
import pl.mmorpg.prototype.client.items.StackableItem;
import pl.mmorpg.prototype.client.objects.icons.items.Item;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.CloseButton;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.StringValueLabel;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory.ButtonField;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory.InventoryPage;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory.InventoryTextField;
import pl.mmorpg.prototype.clientservercommon.packets.GoldAmountChangePacket;
import pl.mmorpg.prototype.clientservercommon.packets.GoldReceivePacket;
import pl.mmorpg.prototype.clientservercommon.packets.InventoryItemRepositionPacket;
import pl.mmorpg.prototype.clientservercommon.packets.InventoryItemStackPacket;
import pl.mmorpg.prototype.clientservercommon.packets.InventoryItemSwapPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.StackableItemAmountChangePacket;

public class InventoryDialog extends Dialog implements ItemCounter
{
	private static final int numberOfPages = 5;
	private int currentPageIndex = 0;
	private final List<InventoryPage> inventoryPages = new ArrayList<>(numberOfPages);
	private final VerticalGroup currentPageButtons = new VerticalGroup();
	private final List<InventoryTextField<Item>> switchPageButtons = new ArrayList<>(numberOfPages);
	private final UserInterface linkedInterface;
	private final StringValueLabel<Integer> goldLabel = new StringValueLabel<>("Gold: ", Settings.DEFAULT_SKIN);

	public InventoryDialog(UserInterface linkedInterface, Integer initialGoldAmmount,
			PacketHandlerRegisterer registerer)
	{
		super("Inventory", Settings.DEFAULT_SKIN);
		this.linkedInterface = linkedInterface;
		registerer.registerPrivateClassPacketHandlers(this);

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
			InventoryTextField<Item> switchButton = createSwitchButton(i);
			switchButtons.addActor(switchButton);
			switchPageButtons.add(switchButton);
		}
		switchPageButtons.get(0).setColor(0.5f, 0.5f, 0.5f, 1);

		contentTable.add(switchButtons);
		contentTable.row();
		goldLabel.setValue(initialGoldAmmount);
		contentTable.add(goldLabel).left();
		contentTable.row();

		this.setX(1230);
		this.setY(43);
		this.pack();
	}

	private InventoryTextField<Item> createSwitchButton(int pageIndex)
	{
		InventoryTextField<Item> switchButton = new InventoryTextField<>(String.valueOf(pageIndex + 1));
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
		ButtonField<Item> field = getField(cellPosition);
		linkedInterface.inventoryFieldClicked(field, cellPosition);
	}

	private void switchButtonClicked(int pageIndex)
	{
		for (InventoryTextField<Item> button : switchPageButtons)
			button.setColor(1, 1, 1, 1);

		switchPageButtons.get(pageIndex).setColor(0.5f, 0.5f, 0.5f, 1);
		currentPageButtons.clear();
		currentPageButtons.addActor(inventoryPages.get(pageIndex));
		currentPageIndex = pageIndex;
	}

	public Item getItem(Point position)
	{
		ButtonField<Item> field = inventoryPages.get(currentPageIndex).getField(position);
		return field.getContent();
	}
	
	public Item getItem(ItemInventoryPosition cellPosition)
	{
		ButtonField<Item> field = inventoryPages.get(cellPosition.getPageNumber()).getField(cellPosition.getPosition());
		return field.getContent();
	}

	public void addItem(StackableItem item)
	{
		for (InventoryPage inventoryPage : inventoryPages)
		{
			for (ButtonField<Item> field : inventoryPage.getAllFields())
			{
				Item fieldItem = field.getContent();
				if (fieldItem != null && fieldItem.getIdentifier().equals(item.getIdentifier()))
				{
					((StackableItem) fieldItem).stackWith(item);
					return;
				}
			}
		}

		addItem((Item) item);
	}

	private ButtonField<Item> getField(ItemInventoryPosition fieldPosition)
	{
		return inventoryPages.get(fieldPosition.getPageNumber())
				.getField(new Point(fieldPosition.getPosition().x, fieldPosition.getPosition().y));
	}

	public void addItem(Item item)
	{
		ItemInventoryPosition freeFieldPosition = getFreeInventoryPosition();
		ButtonField<Item> freeField = getField(freeFieldPosition);
		freeField.put(item);
	}

	public void addItem(StackableItem newItem, ItemInventoryPosition inventoryPosition)
	{
		ButtonField<Item> field = getField(inventoryPosition);
		if (field.hasContent())
		{
			StackableItem item = (StackableItem) field.getContent();
			item.stackWith(newItem);
		} else
			field.put(newItem);
	}

	public void addItem(Item newItem, ItemInventoryPosition inventoryPosition)
	{
		getField(inventoryPosition).put(newItem);
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

	public boolean removeIfHas(Item item)
	{
		for (InventoryPage inventoryPage : inventoryPages)
			if (inventoryPage.removeIfHas(item))
				return true;
		return false;
	}

	public ItemUseable useItem(long itemId)
	{
		ItemUseable item;
		for (InventoryPage inventoryPage : inventoryPages)
			if ((item = inventoryPage.useIfHas(itemId)) != null)
				return item;
		return null;
	}

	public ItemInventoryPosition getFreeInventoryPosition()
	{
		for (int pageNumber = 0; pageNumber < inventoryPages.size(); pageNumber++)
		{
			InventoryPage currentPage = inventoryPages.get(pageNumber);
			for (int i = 0; i < currentPage.getInventoryFieldsHeightNumber(); i++)
				for (int j = 0; j < currentPage.getInventoryFieldsWidthNumber(); j++)
				{
					Point fieldPosition = new Point(j, i);
					ButtonField<Item> field = currentPage.getField(fieldPosition);
					if (!field.hasContent())
						return new ItemInventoryPosition(pageNumber, fieldPosition);
				}
		}
		throw new NoFreeFieldException();
	}

	public ItemInventoryPosition getDesiredItemPositionFor(Item item)
	{
		if (item instanceof StackableItem)
			return getFieldWithSuiteTypeStackableItemFor(item.getIdentifier());
		else
			return getFreeInventoryPosition();
	}

	public ItemInventoryPosition getDesiredItemPositionFor(String itemIdentifier, int numberOfItems)
	{
		if (isStackableType(itemIdentifier))
			return getFieldWithSuiteTypeStackableItemFor(itemIdentifier);
		else
			return getFreeInventoryPosition();
	}

	private boolean isStackableType(String itemIdentifier)
	{
		// TODO: Should refactor
		CharacterItemDataPacket packet = new CharacterItemDataPacket();
		packet.setIdentifier(itemIdentifier);
		Item item = ItemFactory.produceItem(packet);
		return item instanceof StackableItem;
	}

	public ItemInventoryPosition getFieldWithSuiteTypeStackableItemFor(String itemIdentifier)
	{
		try
		{
			return getFieldWithSameTypeItem(itemIdentifier);
		} catch (NoSuchFieldException e)
		{
			return getFreeInventoryPosition();
		}
	}

	private ItemInventoryPosition getFieldWithSameTypeItem(String itemIdentifier)
	{
		for (int pageIndex = 0; pageIndex < numberOfPages; pageIndex++)
			try
			{
				return getFieldWithSameTypeItem(itemIdentifier, pageIndex);
			} catch (NoSuchFieldException e)
			{
			}

		throw new NoSuchFieldException();
	}

	private ItemInventoryPosition getFieldWithSameTypeItem(String itemIdentifier, int pageIndex)
	{
		InventoryPage currentPage = inventoryPages.get(pageIndex);
		for (int i = 0; i < currentPage.getInventoryFieldsHeightNumber(); i++)
			for (int j = 0; j < currentPage.getInventoryFieldsWidthNumber(); j++)
			{
				Point fieldPosition = new Point(i, j);
				ButtonField<Item> field = currentPage.getField(fieldPosition);
				Item fieldItem = field.getContent();
				if (fieldItem != null && fieldItem.getIdentifier().equals(itemIdentifier))
					return new ItemInventoryPosition(pageIndex, fieldPosition);
			}
		throw new NoSuchFieldException();
	}

	public void repositionItem(ItemInventoryPosition sourcePosition, ItemInventoryPosition destinationPosition)
	{
		ButtonField<Item> sourceField = getField(sourcePosition);
		ButtonField<Item> destinationField = getField(destinationPosition);
		Item targetItem = sourceField.getContent();
		sourceField.removeContent();
		destinationField.put(targetItem);
	}

	@Override
	public int countItems(String itemIdentifier)
	{
		int itemCounter = 0;
		for (InventoryPage inventoryPage : inventoryPages)
			itemCounter += inventoryPage.countItems(itemIdentifier);
		return itemCounter;
	}

	public Item searchForItem(String itemIdentifier)
	{
		for (InventoryPage inventoryPage : inventoryPages)
		{
			Item item = inventoryPage.searchForItem(itemIdentifier);
			if (item != null)
				return item;
		}
		return null;
	}

	@SuppressWarnings("unused")
	private class GoldReceivePacketHandler extends PacketHandlerBase<GoldReceivePacket>
	{
		@Override
		protected void doHandle(GoldReceivePacket packet)
		{
			System.out.println("Inventory gold update");
			goldLabel.setValue(goldLabel.getValue() + packet.getGoldAmount());
		}
	}

	@SuppressWarnings("unused")
	private class GoldAmountChangePacketHandler extends PacketHandlerBase<GoldAmountChangePacket>
	{
		@Override
		protected void doHandle(GoldAmountChangePacket packet)
		{
			System.out.println("Inventory gold change ");
			goldLabel.setValue(packet.getNewGoldAmount());
		}
	}

	@SuppressWarnings("unused")
	private class InventoryItemRepositionPacketHandler extends PacketHandlerBase<InventoryItemRepositionPacket>
	{
		@Override
		protected void doHandle(InventoryItemRepositionPacket packet)
		{
			ItemInventoryPosition sourcePosition = new ItemInventoryPosition(packet.getSourcePageNumber(),
					new Point(packet.getSourcePageX(), packet.getSourcePageY()));
			ItemInventoryPosition destinationPosition = new ItemInventoryPosition(packet.getDestinationPageNumber(),
					new Point(packet.getDestinationPageX(), packet.getDestinationPageY()));
			repositionItem(sourcePosition, destinationPosition);
		}
	}

	@SuppressWarnings("unused")
	private class InventoryItemStackPacketHandler extends PacketHandlerBase<InventoryItemStackPacket>
	{
		@Override
		protected void doHandle(InventoryItemStackPacket packet)
		{
			ItemInventoryPosition firstPosition = new ItemInventoryPosition(packet.getFirstPositionPageNumber(),
					new Point(packet.getFirstPositionPageX(), packet.getFirstPositionPageY()));
			ItemInventoryPosition secondPosition = new ItemInventoryPosition(packet.getSecondPositionPageNumber(),
					new Point(packet.getSecondPositionPageX(), packet.getSecondPositionPageY()));
			stackItems(firstPosition, secondPosition);
		}

		private void stackItems(ItemInventoryPosition firstPosition, ItemInventoryPosition secondPosition)
		{
			ButtonField<Item> firstField = getField(firstPosition);
			ButtonField<Item> secondField = getField(secondPosition);
			Item firstItem = firstField.getContent();
			Item secondItem = secondField.getContent();
			firstField.removeContent();
			((StackableItem) secondItem).stackWith((StackableItem) firstItem);
		}
	}

	@SuppressWarnings("unused")
	private class InventoryItemSwapPacketHandler extends PacketHandlerBase<InventoryItemSwapPacket>
	{
		@Override
		protected void doHandle(InventoryItemSwapPacket packet)
		{
			ItemInventoryPosition firstPosition = new ItemInventoryPosition(packet.getFirstPositionPageNumber(),
					new Point(packet.getFirstPositionPageX(), packet.getFirstPositionPageY()));
			ItemInventoryPosition secondPosition = new ItemInventoryPosition(packet.getSecondPositionPageNumber(),
					new Point(packet.getSecondPositionPageX(), packet.getSecondPositionPageY()));

			swapItems(firstPosition, secondPosition);
		}

		private void swapItems(ItemInventoryPosition firstPosition, ItemInventoryPosition secondPosition)
		{
			ButtonField<Item> firstField = getField(firstPosition);
			ButtonField<Item> secondField = getField(secondPosition);
			Item firstItem = firstField.getContent();
			Item secondItem = secondField.getContent();
			firstField.put(secondItem);
			secondField.put(firstItem);
		}
	}

	@SuppressWarnings("unused")
	private class StackableItemAmountChangePacketHandler extends PacketHandlerBase<StackableItemAmountChangePacket>
	{
		@Override
		protected void doHandle(StackableItemAmountChangePacket packet)
		{
			ItemInventoryPosition fieldPosition = new ItemInventoryPosition(packet.getItemPageNumber(),
					new Point(packet.getItemPageX(), packet.getItemPageY()));
			StackableItem item = (StackableItem) getField(fieldPosition).getContent();
			item.modifyAmount(packet.getItemAmount());
		}

	}
}
