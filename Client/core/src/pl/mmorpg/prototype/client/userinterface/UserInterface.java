package pl.mmorpg.prototype.client.userinterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.exceptions.CannotFindSpecifiedDialogTypeException;
import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.items.ItemPositionSupplier;
import pl.mmorpg.prototype.client.items.StackableItem;
import pl.mmorpg.prototype.client.objects.icons.DraggableIcon;
import pl.mmorpg.prototype.client.objects.icons.items.Item;
import pl.mmorpg.prototype.client.objects.icons.spells.Spell;
import pl.mmorpg.prototype.client.objects.monsters.npcs.Npc;
import pl.mmorpg.prototype.client.quests.Quest;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.userinterface.dialogs.ChatDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.ConsoleDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.ContainerDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.EquipmentDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.HitPointManaPointPane;
import pl.mmorpg.prototype.client.userinterface.dialogs.InventoryDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.ItemCounter;
import pl.mmorpg.prototype.client.userinterface.dialogs.ItemQuickAccessDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.MenuDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.NpcConversationDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.QuestBoardDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.QuestListDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.QuestRewardDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.ShopDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.ShortcutBarPane;
import pl.mmorpg.prototype.client.userinterface.dialogs.SpellListDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.SpellQuickAccessDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.StatisticsDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.ItemQuickAccessIcon;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.TimedLabel;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory.ButtonField;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessageReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.QuestFinishedRewardPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.QuestDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.InventoryItemRepositionRequestPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemRewardRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.NpcContinueDialogPacket;

public class UserInterface
{
	private final Stage stage = Assets.getStage();
	private final Stage popUpInfoStage = Assets.getStage();
	private final MenuDialog menuDialog;
	private final InventoryDialog inventoryDialog;
	private final StatisticsDialog statisticsDialog;
	private final ShortcutBarPane shortcutBarDialog;
	private final HitPointManaPointPane hpMpDialog;
	private final ItemQuickAccessDialog itemQuickAccessDialog;
	private final SpellQuickAccessDialog spellQuickAccessDialog;
	private final EquipmentDialog equipmentDialog;
	private final ChatDialog chatDialog;
	private final ConsoleDialog consoleDialog;
	private final QuestListDialog questListDialog;
	private final SpellListDialog spellListDialog;
	private final ActorManipulator dialogs = new ActorManipulator();
	private final DialogIdSupplier dialogIdSupplier = new DialogIdSupplier();

	private final MousePointerToItem mousePointerToIcon = new MousePointerToItem();

	private final PlayState linkedState;

	public UserInterface(PlayState linkedState, UserCharacterDataPacket character)
	{
		this.linkedState = linkedState;
		menuDialog = new MenuDialog(this);
		inventoryDialog = new InventoryDialog(this, character.getGold());
		statisticsDialog = new StatisticsDialog(character);
		shortcutBarDialog = new ShortcutBarPane(this);
		hpMpDialog = new HitPointManaPointPane(character);
		itemQuickAccessDialog = new ItemQuickAccessDialog(this);
		spellQuickAccessDialog = new SpellQuickAccessDialog(this);
		equipmentDialog = new EquipmentDialog();
		chatDialog = new ChatDialog(this);
		consoleDialog = new ConsoleDialog(this);
		questListDialog = new QuestListDialog();
		spellListDialog = new SpellListDialog(this);
		mapDialogsWithKeys();
		mapOtherDialogs();
		addDialogsToStage();
		dialogs.hideKeyMappedDialogs();

		stage.addListener(new ClickListener(Buttons.LEFT)
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				if (!dialogs.hasDialogOnPosition(x, y))
				{
					linkedState.userLeftClickedOnGameBoard(x, y);
					stage.unfocusAll();
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
					stage.unfocusAll();
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
		dialogs.map(Keys.Q, questListDialog);
		dialogs.map(Keys.J, spellListDialog);
	}

	private void mapOtherDialogs()
	{
		dialogs.add(shortcutBarDialog);
		dialogs.add(hpMpDialog);
		dialogs.add(itemQuickAccessDialog);
		dialogs.add(spellQuickAccessDialog);
	}

	private void addDialogsToStage()
	{
		stage.addActor(chatDialog);
		stage.addActor(itemQuickAccessDialog);
		stage.addActor(spellQuickAccessDialog);
		stage.addActor(hpMpDialog);
		stage.addActor(shortcutBarDialog);
		stage.addActor(menuDialog);
		stage.addActor(inventoryDialog);
		stage.addActor(statisticsDialog);
		stage.addActor(equipmentDialog);
		stage.addActor(consoleDialog);
		stage.addActor(questListDialog);
		stage.addActor(spellListDialog);
	}

	public void draw(SpriteBatch batch)
	{
		stage.draw();
		popUpInfoStage.draw();
		popUpInfoStage.getBatch().begin();
		if (mousePointerToIcon.icon != null)
			mousePointerToIcon.icon.renderWhenDragged(popUpInfoStage.getBatch());
		popUpInfoStage.getBatch().end();
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

	public void inventoryFieldClicked(ButtonField<Item> inventoryField, ItemInventoryPosition cellPosition)
	{
		if (mousePointerToIcon.icon == null && inventoryField.hasContent())
			mousePointerToIcon.icon = (DraggableIcon) inventoryField.getContent();
		else if (mousePointerToIcon.icon != null && mousePointerToIcon.icon instanceof Item)
		{
			InventoryItemRepositionRequestPacket inventoryItemRepositionRequestPacket = PacketsMaker
					.makeInventoryItemRepositionRequestPacket(((Item) mousePointerToIcon.icon).getId(),
							cellPosition);
			((PacketsSender) linkedState).send(inventoryItemRepositionRequestPacket);
			mousePointerToIcon.icon = null;
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

	public boolean isTextFieldFocused()
	{
		return stage.getKeyboardFocus() instanceof TextField;
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

	public void itemQuickAccessButtonClicked(ButtonField<ItemQuickAccessIcon> field, int cellPosition)
	{
		DraggableIcon heldIcon = mousePointerToIcon.icon;
		if (heldIcon != null)
		{
			if (!(heldIcon instanceof Item))
				return;
			ItemQuickAccessIcon icon = new ItemQuickAccessIcon(((Item) heldIcon).getIdentifier(),
					(ItemCounter) inventoryDialog);
			field.put(icon);
			((PacketsSender) linkedState).send(PacketsMaker
					.makeItemPutInQuickAccessBarPacket(((Item) heldIcon).getIdentifier(), cellPosition));
			mousePointerToIcon.icon = null;
		} else if (field.hasContent())
		{
			field.removeContent();
			((PacketsSender) linkedState).send(PacketsMaker.makeItemRemovedFromQuickAccessBarPacket(cellPosition));
		}
	}

	public void spellQuickAccessButtonClicked(ButtonField<Spell> buttonField, int cellPosition)
	{
		DraggableIcon heldIcon = mousePointerToIcon.icon;
		if(heldIcon == null && buttonField.hasContent())
			buttonField.removeContent();
		else if (!(heldIcon instanceof Spell))
			return;
		else 
		{
			buttonField.put((Spell)heldIcon);
			mousePointerToIcon.icon = null;
		}
	}
	
	public void spellListButtonWithIconClicked(ButtonField<Spell> spellButtonField)
	{
		mousePointerToIcon.icon = spellButtonField.getContent();
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
		Item item = (Item) inventoryDialog.useItem(itemId);
		itemQuickAccessDialog.decreaseNumberOfItems(item.getIdentifier());
	}

	public void containerOpened(CharacterItemDataPacket[] contentItems, int gold, long containerId)
	{
		if (!dialogs.hasIdentifiableDialog(containerId))
			Gdx.app.postRunnable(() -> createAndOpenContainerDialog(contentItems, gold, containerId));
	}

	private void createAndOpenContainerDialog(CharacterItemDataPacket[] contentItems, int gold, long containerId)
	{
		ItemPositionSupplier desiredItemPositionSupplier = inventoryDialog::getDesiredItemPositionFor;
		Dialog containerDialog = new ContainerDialog(contentItems, gold, "Container", dialogs,
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
			ContainerDialog dialog = dialogs.getIdentifiableDialog(containerId);
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
			ContainerDialog containerDialog = (ContainerDialog) dialogs.getIdentifiableDialog(containerId);
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
		itemQuickAccessDialog.increaseNumbers(newItem.getIdentifier(), getItemCount(newItem));
	}

	private int getItemCount(Item item)
	{
		if (item instanceof StackableItem)
			return ((StackableItem) item).getItemCount();
		return 1;
	}

	public void putItemInQuickAccessBar(String itemIdentifier, int cellPosition)
	{
		itemQuickAccessDialog.putItem(itemIdentifier, cellPosition, (ItemCounter) inventoryDialog);
	}

	public ItemInventoryPosition getSuitePositionInInventoryFor(ShopItem item)
	{
		return inventoryDialog.getDesiredItemPositionFor(item.getItem());
	}

	public void addInfoMessageToConsole(String message)
	{
		consoleDialog.addMessage(message);
	}

	public void questBoardClicked(QuestDataPacket[] quests, long questBoardId)
	{
		if (!dialogs.hasIdentifiableDialog(questBoardId))
			showQuestDialog(quests, questBoardId);
	}

	private void showQuestDialog(QuestDataPacket[] quests, long questBoardId)
	{
		QuestBoardDialog questDialog = new QuestBoardDialog(dialogs, questBoardId, quests, stage, linkedState);
		positionDialogNearMouse(questDialog);
		stage.addActor(questDialog);
		dialogs.add(questDialog);
	}

	public void openNewQuestRewardDialog(QuestFinishedRewardPacket rewardPacket)
	{
		ItemPositionSupplier desiredItemPositionSupplier = inventoryDialog::getDesiredItemPositionFor;
		QuestRewardDialog questRewardDialog = new QuestRewardDialog(dialogs, dialogIdSupplier.getId(), rewardPacket,
				desiredItemPositionSupplier, (PacketsSender) linkedState);
		positionDialogNearMouse(questRewardDialog);
		stage.addActor(questRewardDialog);
		dialogs.add(questRewardDialog);
	}

	public void removeItemFromQuestRewardContainer(ItemRewardRemovePacket packet)
	{
		QuestRewardDialog dialog = dialogs.searchForDialog(QuestRewardDialog.class);
		dialog.removeItem(packet.getItemIdentifier(), packet.getNumberOfItems());
	}

	public void removeGoldFromQuestRewardDialog(int goldAmount)
	{
		QuestRewardDialog dialog = dialogs.searchForDialog(QuestRewardDialog.class);
		dialog.updateGoldByDecreasingBy(goldAmount);
	}

	public void addQuestToQuestListDialog(Quest quest)
	{
		questListDialog.addQuest(quest);
	}

	public void removeQuestPositionFromQuestBoardDialog(String questName)
	{
		try
		{
			QuestBoardDialog questBoardDialog = dialogs.searchForDialog(QuestBoardDialog.class);
			questBoardDialog.removeQuestPosition(questName);
		} catch (CannotFindSpecifiedDialogTypeException e)
		{
		}
	}

	public void openNpcConversationDialog(Npc npc, String speech, String[] possibleAnswers)
	{
		if (dialogs.hasIdentifiableDialog(npc.getId()))
		{
			NpcConversationDialog npcConversationDialog = dialogs.getIdentifiableDialog(npc.getId());
			npcConversationDialog.cleanUpItself();
		}

		NpcConversationDialog newDialog = new NpcConversationDialog(npc, speech, possibleAnswers,
				(PacketsSender) linkedState, dialogs);
		stage.addActor(newDialog);
		dialogs.add(newDialog);
	}

	public void continueNpcConversation(NpcContinueDialogPacket packet)
	{
		NpcConversationDialog npcConversationDialog = dialogs.getIdentifiableDialog(packet.getNpcId());
		npcConversationDialog.update(packet.getSpeech(), packet.getPossibleAnswers());
	}

	public void stackItemsInInventoryDialog(ItemInventoryPosition firstPosition, ItemInventoryPosition secondPosition)
	{
		inventoryDialog.stackItems(firstPosition, secondPosition);
	}

	public void focus(Actor actor)
	{
		stage.setKeyboardFocus(actor);
		stage.setScrollFocus(actor);
	}

}