package pl.mmorpg.prototype.client.userinterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.items.DraggableItem;
import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.items.ItemPositionSupplier;
import pl.mmorpg.prototype.client.items.QuickAccessIcon;
import pl.mmorpg.prototype.client.items.StackableItem;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.userinterface.dialogs.ChatDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.ConsoleDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.EquipmentDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.HitPointManaPointPane;
import pl.mmorpg.prototype.client.userinterface.dialogs.InventoryDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.ItemCounter;
import pl.mmorpg.prototype.client.userinterface.dialogs.MenuDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.OpenContainerDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.QuickAccessDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.ShopDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.ShortcutBarPane;
import pl.mmorpg.prototype.client.userinterface.dialogs.StatisticsDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryField;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.TimedLabel;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessageReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.InventoryItemRepositionRequestPacket;

public class UserInterface
{
	private final Stage stage = Assets.getStage();
	private final Stage popUpInfoStage = Assets.getStage();
	private final MenuDialog menuDialog;
	private final InventoryDialog inventoryDialog;
	private final StatisticsDialog statisticsDialog;
	private final ShortcutBarPane standardBarDialog;
	private final HitPointManaPointPane hpMpDialog;
	private final QuickAccessDialog quickAccessDialog;
	private final EquipmentDialog equipmentDialog;
	private final ChatDialog chatDialog;
	private final ConsoleDialog consoleDialog;
	private final ActorManipulator dialogs = new ActorManipulator();

	private MousePointerToItem mousePointerToItem = new MousePointerToItem();

	private PlayState linkedState;

	public UserInterface(PlayState linkedState, UserCharacterDataPacket character)
	{
		this.linkedState = linkedState;
		menuDialog = new MenuDialog(this);
		inventoryDialog = new InventoryDialog(this, character.getGold());
		statisticsDialog = new StatisticsDialog(character);
		standardBarDialog = new ShortcutBarPane(this);
		hpMpDialog = new HitPointManaPointPane(character);
		quickAccessDialog = new QuickAccessDialog(this);
		equipmentDialog = new EquipmentDialog();
		chatDialog = new ChatDialog(this);
		consoleDialog = new ConsoleDialog(this);
		mapDialogsWithKeys();
		addOtherDialogs();
		showDialogs();
		dialogs.hideKeyMappedDialogs();

		stage.addListener(new ClickListener(Buttons.LEFT)
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				if (!dialogs.hasDialogOnPosition(x, y))
				{
					linkedState.userLeftClickedOnGameBoard(x, y);
				}
			}
		});
		stage.addListener(new ClickListener(Buttons.RIGHT)
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				if (!dialogs.hasDialogOnPosition(x, y))
				{
					linkedState.userRightClickedOnGameBoard(x, y);
				}
			}
		});
	}

	public void mapDialogsWithKeys()
	{
		dialogs.map(Keys.T, chatDialog);
		dialogs.map(Keys.E, equipmentDialog);
		dialogs.map(Keys.M, menuDialog);
		dialogs.map(Keys.I, inventoryDialog);
		dialogs.map(Keys.C, statisticsDialog);
		dialogs.map(Keys.L, consoleDialog);
	}

	private void addOtherDialogs()
	{
		dialogs.add(standardBarDialog);
		dialogs.add(hpMpDialog);
		dialogs.add(quickAccessDialog);
	}

	public void showDialogs()
	{
		stage.addActor(chatDialog);
		stage.addActor(quickAccessDialog);
		stage.addActor(hpMpDialog);
		stage.addActor(standardBarDialog);
		stage.addActor(menuDialog);
		stage.addActor(inventoryDialog);
		stage.addActor(statisticsDialog);
		stage.addActor(equipmentDialog);
		stage.addActor(consoleDialog);
	}

	public void draw(SpriteBatch batch)
	{
		stage.draw();
		popUpInfoStage.draw();
		batch.begin();
		if (mousePointerToItem.item != null)
			mousePointerToItem.item.renderWhenDragged(batch);
		batch.end();
	}

	public void update()
	{
		popUpInfoStage.act();
		stage.act();
		dialogs.manageZIndexes();
	}

	public ActorManipulator getDialogs()
	{
		return dialogs;
	}

	public void clear()
	{
		stage.clear();
		dialogs.clear();
	}

	public Stage getStage()
	{
		return stage;
	}

	public void inventoryFieldClicked(InventoryField<Item> inventoryField, ItemInventoryPosition cellPosition)
	{
		if (mousePointerToItem.item == null && inventoryField.hasContent())
			mousePointerToItem.item = (DraggableItem)inventoryField.getContent();
		else if (mousePointerToItem.item != null)
		{
			InventoryItemRepositionRequestPacket inventoryItemRepositionRequestPacket = PacketsMaker
					.makeInventoryItemRepositionRequestPacket(mousePointerToItem.item.getId(), cellPosition);
			((PacketsSender) linkedState).send(inventoryItemRepositionRequestPacket);
			mousePointerToItem.item = null;
		}
	}

	public void userWantsToDisconnect()
	{
		linkedState.userWantsToDisconnect();
	}

	public void showOrHideDialog(Class<? extends Table> clazz)
	{
		dialogs.showOrHide(clazz);
	}

	public void userWantsToChangeCharacter()
	{
		linkedState.userWantsToChangeCharacter();
	}

	public void addItemToInventory(Item newItem, ItemInventoryPosition position)
	{
		if (newItem instanceof StackableItem)
			inventoryDialog.addItem((StackableItem) newItem, position);
		else
			inventoryDialog.addItem(newItem, position);
	}

	public void quickAccessButtonClicked(InventoryField<QuickAccessIcon> field, int cellPosition)
	{
		DraggableItem heldItem = mousePointerToItem.item;
		if (heldItem != null)
		{
			QuickAccessIcon icon = new QuickAccessIcon(heldItem.getIdentifier(), (ItemCounter)inventoryDialog);
			field.put(icon);
			((PacketsSender)linkedState).send(PacketsMaker.makeItemPutInQuickAccessBarPacket(heldItem.getIdentifier(), cellPosition));
			mousePointerToItem.item = null;
		}
		else if(field.hasContent())
		{
			field.removeContent();
			((PacketsSender)linkedState).send(PacketsMaker.makeItemRemovedFromQuickAccessBarPacket(cellPosition));
		}
	}

	public void userDistributedStatPoints()
	{
		statisticsDialog.updateStatistics();
	}

	public void userWantsToSendMessage(String message)
	{
		linkedState.userWantsToSendMessage(message);
	}

	public void addMessageToDialogChat(ChatMessageReplyPacket packet)
	{
		chatDialog.addMessage(packet);
	}

	public void updateStatsDialog()
	{
		statisticsDialog.updateStatistics();
	}

	public void updateHitPointManaPointDialog()
	{
		hpMpDialog.updateValues();
	}

	public void userWantsToLogOut()
	{
		linkedState.userWantsToLogOut();
	}

	public void updateHpMpDialog()
	{
		hpMpDialog.updateValues();
	}

	public void itemUsed(long itemId)
	{
		Item item = (Item)inventoryDialog.useItem(itemId);
		quickAccessDialog.decreaseNumberOfItems(item.getIdentifier());
	}

	public void containerOpened(CharacterItemDataPacket[] contentItems, int gold, long containerId)
	{
		if (!dialogs.hasIdentifiableDialog(containerId))
			Gdx.app.postRunnable(() -> createAndOpenContainerDialog(contentItems, gold, containerId));
	}

	private void createAndOpenContainerDialog(CharacterItemDataPacket[] contentItems, int gold, long containerId)
	{
		ItemPositionSupplier desiredItemPositionSupplier = inventoryDialog::getDesiredItemPositionFor;
		Dialog containerDialog = new OpenContainerDialog(contentItems, gold, "Container", dialogs,
				(PacketsSender) linkedState, containerId, desiredItemPositionSupplier);
		positionDialogNearMouse(containerDialog);
		stage.addActor(containerDialog);
		dialogs.add(containerDialog);
	}

	private void positionDialogNearMouse(Dialog containerDialog)
	{
		containerDialog.setPosition(Gdx.input.getX(),
				Gdx.graphics.getHeight() - Gdx.input.getY() - containerDialog.getHeight());
	}

	public void removeContainerItem(long containerId, long itemId)
	{
		if (dialogs.hasIdentifiableDialog(containerId))
		{
			OpenContainerDialog dialog = dialogs.getIdentifiableDialog(containerId);
			dialog.removeItem(itemId);
		}

	}

	public void showTimedErrorMessage(String errorMessage, float timeout)
	{
		Label label = new TimedLabel(errorMessage, timeout);
		label.setColor(Color.RED);
		label.setX(960);
		label.setY(55);

		stage.addActor(label);
	}

	public void decreaseGoldFromContainerDialog(long containerId, int goldAmount)
	{
		if (dialogs.hasIdentifiableDialog(containerId))
		{
			OpenContainerDialog containerDialog = (OpenContainerDialog) dialogs.getIdentifiableDialog(containerId);
			containerDialog.updateGoldByDecreasingBy(goldAmount);
		}

	}

	public void updateGoldAmountInInventory(int goldAmount)
	{
		InventoryDialog inventory = (InventoryDialog) dialogs.searchForDialog(InventoryDialog.class);
		inventory.updateGoldValue(goldAmount);
	}

	public void openShopDialog(ShopItem[] shopItems, long shopId)
	{
		if (!dialogs.hasIdentifiableDialog(shopId))
		{
			ShopDialog shop = new ShopDialog("Shop", shopId, shopItems, popUpInfoStage, this,
					(PacketsSender) linkedState);
			shop.setPosition(0, 100);
			shop.pack();
			dialogs.add(shop);
			stage.addActor(shop);
		}
	}

	public void addDialog(Dialog dialog)
	{
		dialogs.add(dialog);
		stage.addActor(dialog);
	}

	public void sendCommandToExecute(String command)
	{
		linkedState.send(PacketsMaker.makeScriptCodePacket(command));
	}

	public void repositionItemInInventory(ItemInventoryPosition sourcePosition,
			ItemInventoryPosition destinationPosition)
	{
		inventoryDialog.repositionItem(sourcePosition, destinationPosition);
	}

	public void swapItemsInInventory(ItemInventoryPosition firstPosition, ItemInventoryPosition secondPosition)
	{
		inventoryDialog.swapItems(firstPosition, secondPosition);
	}

	public Item searchForItem(String itemIdentifier)
	{
		return inventoryDialog.searchForItem(itemIdentifier);
	}

	public void increaseQuickAccessDialogNumbers(Item newItem)
	{
		quickAccessDialog.increaseNumbers(newItem.getIdentifier(), getItemCount(newItem));
	}
	
	private int getItemCount(Item item)
	{
		if(item instanceof StackableItem)
			return ((StackableItem)item).getItemCount();
		return 1;
	}

	public void putItemInQuickAccessBar(String itemIdentifier, int cellPosition)
	{
		quickAccessDialog.putItem(itemIdentifier, cellPosition, (ItemCounter)inventoryDialog);
	}

}