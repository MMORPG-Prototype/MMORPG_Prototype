package pl.mmorpg.prototype.client.userinterface;

import java.awt.*;
import java.util.stream.Stream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.exceptions.CannotFindSpecifiedDialogTypeException;
import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.items.*;
import pl.mmorpg.prototype.client.items.equipment.EquipmentItem;
import pl.mmorpg.prototype.client.objects.Player;
import pl.mmorpg.prototype.client.objects.icons.DraggableIcon;
import pl.mmorpg.prototype.client.objects.icons.items.Item;
import pl.mmorpg.prototype.client.objects.icons.spells.Spell;
import pl.mmorpg.prototype.client.objects.monsters.npcs.Npc;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.packethandlers.UserInterfacePacketHandlerBase;
import pl.mmorpg.prototype.client.quests.Quest;
import pl.mmorpg.prototype.client.quests.QuestCreator;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.userinterface.dialogs.*;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.ItemQuickAccessIcon;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.TimedLabel;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory.ButtonField;
import pl.mmorpg.prototype.clientservercommon.EquipmentPosition;
import pl.mmorpg.prototype.clientservercommon.packets.ContainerContentPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.InventoryPositionPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.*;
import pl.mmorpg.prototype.clientservercommon.packets.quest.QuestAcceptedPacket;
import pl.mmorpg.prototype.clientservercommon.packets.quest.QuestBoardInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.quest.QuestFinishedRewardPacket;
import pl.mmorpg.prototype.clientservercommon.packets.quest.QuestStateInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptExecutionErrorPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptResultInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ShopItemPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ShopItemsPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.QuestDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

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
	private final LevelUpDialog levelUpDialog;
	private final ActorManipulator dialogs = new ActorManipulator();
	private final DialogIdSupplier dialogIdSupplier = new DialogIdSupplier();

	private final MousePointerToItem mousePointerToIcon = new MousePointerToItem();

	private final PlayState linkedState;

	public UserInterface(PlayState linkedState, Player player, PacketHandlerRegisterer registerer)
	{
		this.linkedState = linkedState;
		UserCharacterDataPacket playerData = player.getData();
		menuDialog = new MenuDialog(this);
		inventoryDialog = new InventoryDialog(this, playerData.getGold(), registerer);
		statisticsDialog = new StatisticsDialog(player, stage);
		shortcutBarDialog = new ShortcutBarPane(this);
		hpMpDialog = new HitPointManaPointPane(player, registerer);
		itemQuickAccessDialog = new ItemQuickAccessDialog(this, (ItemCounter) inventoryDialog, registerer);
		spellQuickAccessDialog = new SpellQuickAccessDialog(this, registerer);
		equipmentDialog = new EquipmentDialog(this);
		chatDialog = new ChatDialog(this, registerer);
		consoleDialog = new ConsoleDialog(this);
		questListDialog = new QuestListDialog(registerer);
		spellListDialog = new SpellListDialog(this, registerer);
		levelUpDialog = new LevelUpDialog(this, playerData);
		mapDialogsWithKeys();
		mapOtherDialogs();
		addDialogsToStage();
		dialogs.hideKeyMappedDialogs();
		levelUpDialog.setVisible(false);

		addListeners(linkedState);
		registerer.registerPrivateClassPacketHandlers(this);
	}

	private void addListeners(PlayState linkedState)
	{
		addLeftClickListener(linkedState);
		addRightClickListener(linkedState);
		addMouseMoveListener(linkedState);
	}

	private void addLeftClickListener(PlayState linkedState)
	{
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
	}

	private void addRightClickListener(PlayState linkedState)
	{
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

	private void addMouseMoveListener(PlayState linkedState)
	{
		stage.addListener(new InputListener()
		{
			@Override
			public boolean mouseMoved(InputEvent event, float x, float y)
			{
				linkedState.onMouseMove(x, y);
				return super.mouseMoved(event, x, y);
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
		dialogs.add(levelUpDialog);
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
		stage.addActor(levelUpDialog);
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
			mousePointerToIcon.icon = inventoryField.getContent();
		else if (mousePointerToIcon.icon instanceof Item)
			emptyFieldClickedWhileHoldingAnItem(cellPosition, (Item) mousePointerToIcon.icon);
	}

	private void emptyFieldClickedWhileHoldingAnItem(ItemInventoryPosition cellPosition, Item item)
	{
		if (item instanceof StackableItem
				&& ((StackableItem)mousePointerToIcon.icon).getItemCount() > 1
				&& (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyJustPressed(Keys.SHIFT_RIGHT)))
			openSplitItemsDialog(cellPosition);
		else if (item instanceof EquipmentItem && equipmentDialog.isItemEquipped((EquipmentItem)item))
			takeOffItem((EquipmentItem)item, cellPosition);
		else
			repositionItem(cellPosition, item);

		mousePointerToIcon.icon = null;
	}

	private void openSplitItemsDialog(ItemInventoryPosition cellPosition)
	{
		Dialog splitItemsDialog = createSplitItemsDialog(cellPosition);
		DialogUtils.centerPosition(splitItemsDialog);
		addDialog(splitItemsDialog);
	}

	private Dialog createSplitItemsDialog(ItemInventoryPosition cellPosition)
	{
		Item destinationItem = inventoryDialog.getItem(cellPosition);
		if (destinationItem instanceof StackableItem)
			return new StackableItemsSplitWithSecondStackDialog((StackableItem) mousePointerToIcon.icon,
					(StackableItem) destinationItem, cellPosition, dialogs, (PacketsSender) linkedState,
					dialogIdSupplier.getId());

		return new StackableItemsSplitSingleStackDialog((StackableItem) mousePointerToIcon.icon, cellPosition, dialogs,
				(PacketsSender) linkedState, dialogIdSupplier.getId());
	}

	private void repositionItem(ItemInventoryPosition cellPosition, Item item)
	{
		InventoryItemRepositionRequestPacket inventoryItemRepositionRequestPacket = PacketsMaker
				.makeInventoryItemRepositionRequestPacket(item.getId(),
						cellPosition);
		((PacketsSender) linkedState).send(inventoryItemRepositionRequestPacket);
	}

	private void takeOffItem(EquipmentItem item, ItemInventoryPosition emptyDestinationPosition)
	{
		linkedState.send(PacketsMaker.makeTakeOffItemPacket(item.getId(), emptyDestinationPosition));
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

	public void addItemToEquipment(Item newItem, ItemInventoryPosition position)
	{
		if (newItem instanceof StackableItem)
			inventoryDialog.addItem((StackableItem) newItem, position);
		else
			inventoryDialog.addItem(newItem, position);
	}

	public void addItemToEquipment(EquipmentItem newItem, EquipmentPosition position)
	{
		equipmentDialog.equipItem(position, newItem);
	}

	public void itemQuickAccessButtonClicked(ButtonField<ItemQuickAccessIcon> field, int cellPosition)
	{
		DraggableIcon heldIcon = mousePointerToIcon.icon;
		if (heldIcon instanceof ItemUseable)
		{
			ItemQuickAccessIcon icon = new ItemQuickAccessIcon(((Item) heldIcon).getIdentifier(),
					(ItemCounter) inventoryDialog);
			field.put(icon);
			((PacketsSender) linkedState).send(
					PacketsMaker.makeItemPutInQuickAccessBarPacket(((Item) heldIcon).getIdentifier(), cellPosition));
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
		if (heldIcon == null && buttonField.hasContent())
		{
			buttonField.removeContent();
			((PacketsSender) linkedState).send(PacketsMaker.makeSpellRemovedFromQuickAccessBarPacket(cellPosition));
		} else if (heldIcon instanceof Spell)
		{
			buttonField.put((Spell) heldIcon);
			((PacketsSender) linkedState).send(
					PacketsMaker.makeSpellPutInQuickAccessBarPacket(((Spell) heldIcon).getIdentifier(), cellPosition));
			mousePointerToIcon.icon = null;
		}
	}

	public void spellListButtonWithIconClicked(ButtonField<Spell> spellButtonField)
	{
		mousePointerToIcon.icon = spellButtonField.getContent();
	}

	public void userDistributedLevelUpPoint(Object packetToSend)
	{
		linkedState.userDistributedLevelUpPoint(packetToSend);
	}

	public void userWantsToSendMessage(String message)
	{
		linkedState.userWantsToSendMessage(message);
	}

	public void updateStatsDialog()
	{
		statisticsDialog.updateStatistics();
	}

	public void userWantsToLogOut()
	{
		linkedState.userWantsToLogOut();
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

	public void addDialog(Dialog dialog)
	{
		dialogs.add(dialog);
		stage.addActor(dialog);
	}

	public void sendCommandToExecute(String command)
	{
		linkedState.send(PacketsMaker.makeScriptCodePacket(command));
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

	public ItemInventoryPosition getSuitePositionInInventoryFor(ShopItem item)
	{
		return inventoryDialog.getDesiredItemPositionFor(item.getItem());
	}

	private void showQuestDialog(QuestDataPacket[] quests, long questBoardId)
	{
		QuestBoardDialog questDialog = new QuestBoardDialog(dialogs, questBoardId, quests, stage, linkedState);
		positionDialogNearMouse(questDialog);
		stage.addActor(questDialog);
		dialogs.add(questDialog);
	}

	public void removeQuestPositionFromQuestBoardDialog(String questName)
	{
		try
		{
			QuestBoardDialog questBoardDialog = dialogs.searchForDialog(QuestBoardDialog.class);
			questBoardDialog.removeQuestPosition(questName);
		} catch (CannotFindSpecifiedDialogTypeException e)
		{
			Log.warn(e.getMessage());
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

	public void focus(Actor actor)
	{
		stage.setKeyboardFocus(actor);
		stage.setScrollFocus(actor);
	}

	public void updateHitPointManaPointDialog()
	{
		hpMpDialog.updateValues();
	}

	public void openLevelUpPointsDialog()
	{
		levelUpDialog.updateShownValues();
		levelUpDialog.setVisible(true);
	}

	public void levelUpPointAllocated()
	{
		levelUpDialog.updateShownValues();
		statisticsDialog.updateStatistics();
	}

	public void equipmentFieldClicked(EquipmentPosition equipmentPosition)
	{
		DraggableIcon heldIcon = mousePointerToIcon.icon;
		ButtonField<? extends DraggableIcon> itemField = equipmentDialog.getItemField(equipmentPosition);
		if (heldIcon == null && itemField.hasContent())
			mousePointerToIcon.icon = itemField.getContent();
		else if (heldIcon instanceof EquipmentItem)
			equipmentItemFieldClickedWhileHoldingEquipmentItem(equipmentPosition,
					(EquipmentItem) heldIcon, itemField);
	}

	private void equipmentItemFieldClickedWhileHoldingEquipmentItem(EquipmentPosition equipmentPosition, EquipmentItem equipmentItem,
			ButtonField<? extends DraggableIcon> destinationField)
	{
		if(!destinationField.hasContent())
			linkedState.send(PacketsMaker.makeEquipItemPacket(equipmentItem.getId(), equipmentPosition.toString()));
		else
			linkedState.send(PacketsMaker.makeSwitchEquipmentItemPacket(equipmentItem.getId(), equipmentPosition.toString()));

		mousePointerToIcon.icon = null;
	}

	@SuppressWarnings("unused")
	private class ContainerContentPacketHandler extends UserInterfacePacketHandlerBase<ContainerContentPacket>
	{
		@Override
		protected void doHandle(ContainerContentPacket packet)
		{
			if (!dialogs.hasIdentifiableDialog(packet.getContainerId()))
				Gdx.app.postRunnable(() -> createAndOpenContainerDialog(packet.getContentItems(),
						packet.getGoldAmount(), packet.getContainerId()));
		}
	}

	@SuppressWarnings("unused")
	private class ContainerGoldRemovalPacketHandler extends UserInterfacePacketHandlerBase<ContainerGoldRemovalPacket>
	{
		@Override
		protected void doHandle(ContainerGoldRemovalPacket packet)
		{
			decreaseGoldFromContainerDialog(packet.getContainerId(), packet.getGoldAmount());
		}

		private void decreaseGoldFromContainerDialog(long containerId, int goldAmount)
		{
			if (dialogs.hasIdentifiableDialog(containerId))
			{
				ContainerDialog containerDialog = (ContainerDialog) dialogs.getIdentifiableDialog(containerId);
				containerDialog.updateGoldByDecreasingBy(goldAmount);
			}
		}
	}

	@SuppressWarnings("unused")
	private class ContainerItemRemovalPacketHandler extends UserInterfacePacketHandlerBase<ContainerItemRemovalPacket>
	{
		@Override
		protected void doHandle(ContainerItemRemovalPacket packet)
		{
			removeContainerItem(packet.getContainerId(), packet.getItemId());
		}

		private void removeContainerItem(long containerId, long itemId)
		{
			if (dialogs.hasIdentifiableDialog(containerId))
			{
				ContainerDialog dialog = dialogs.getIdentifiableDialog(containerId);
				dialog.removeItem(itemId);
			}
		}
	}

	@SuppressWarnings("unused")
	private class ItemRewardRemovePacketHandler extends UserInterfacePacketHandlerBase<ItemRewardRemovePacket>
	{
		@Override
		protected void doHandle(ItemRewardRemovePacket packet)
		{
			removeItemFromQuestRewardContainer(packet.getItemIdentifier(), packet.getNumberOfItems());
		}

		private void removeItemFromQuestRewardContainer(String itemIdentifier, int numberOfItems)
		{
			QuestRewardDialog dialog = dialogs.searchForDialog(QuestRewardDialog.class);
			dialog.removeItem(itemIdentifier, numberOfItems);
		}
	}

	@SuppressWarnings("unused")
	private class ItemUsagePacketHandler extends UserInterfacePacketHandlerBase<ItemUsagePacket>
	{
		@Override
		protected void doHandle(ItemUsagePacket packet)
		{
			itemUsed(packet.getItemId());
		}

		private void itemUsed(long itemId)
		{
			Item item = (Item) inventoryDialog.useItem(itemId);
			if (item != null)
				itemQuickAccessDialog.decreaseNumberOfItems(item.getIdentifier());
			else
				Log.warn("Something is wrong with item usage!");
		}
	}

	@SuppressWarnings("unused")
	private class NpcContinueDialogPacketHandler extends UserInterfacePacketHandlerBase<NpcContinueDialogPacket>
	{
		@Override
		protected void doHandle(NpcContinueDialogPacket packet)
		{
			continueNpcConversation(packet.getNpcId(), packet.getSpeech(), packet.getPossibleAnswers());
		}

		private void continueNpcConversation(long npcId, String speech, String[] possibleAnswers)
		{
			NpcConversationDialog npcConversationDialog = dialogs.getIdentifiableDialog(npcId);
			npcConversationDialog.update(speech, possibleAnswers);
		}
	}

	@SuppressWarnings("unused")
	private class QuestAcceptedPacketHandler extends UserInterfacePacketHandlerBase<QuestAcceptedPacket>
	{
		@Override
		protected void doHandle(QuestAcceptedPacket packet)
		{
			QuestStateInfoPacket questStatePacket = packet.getQuestStatePacket();
			removeQuestPositionFromQuestBoardDialog(questStatePacket.getQuestName());
			Quest quest = QuestCreator.create(questStatePacket);
			questListDialog.addQuest(quest);
		}
	}

	@SuppressWarnings("unused")
	private class QuestStateInfoPacketHandler extends UserInterfacePacketHandlerBase<QuestStateInfoPacket>
	{
		@Override
		protected void doHandle(QuestStateInfoPacket packet)
		{
			Quest quest = QuestCreator.create(packet);
			questListDialog.updateQuest(quest);
		}
	}

	@SuppressWarnings("unused")
	private class QuestStateInfoPacketArrayHandler extends UserInterfacePacketHandlerBase<QuestStateInfoPacket[]>
	{
		@Override
		protected void doHandle(QuestStateInfoPacket[] packets)
		{
			for (QuestStateInfoPacket packet : packets)
			{
				Quest quest = QuestCreator.create(packet);
				questListDialog.addQuest(quest);
			}
		}
	}

	@SuppressWarnings("unused")
	private class QuestBoardInfoPacketHandler extends UserInterfacePacketHandlerBase<QuestBoardInfoPacket>
	{
		@Override
		protected void doHandle(QuestBoardInfoPacket packet)
		{
			questBoardClicked(packet.getQuests(), packet.getQuestBoardId());
		}

		private void questBoardClicked(QuestDataPacket[] quests, long questBoardId)
		{
			if (!dialogs.hasIdentifiableDialog(questBoardId))
				showQuestDialog(quests, questBoardId);
		}
	}

	@SuppressWarnings("unused")
	private class QuestFinishedRewardPacketHandler extends UserInterfacePacketHandlerBase<QuestFinishedRewardPacket>
	{
		@Override
		protected void doHandle(QuestFinishedRewardPacket packet)
		{
			openNewQuestRewardDialog(packet);
		}

		private void openNewQuestRewardDialog(QuestFinishedRewardPacket rewardPacket)
		{
			ItemPositionSupplier desiredItemPositionSupplier = inventoryDialog::getDesiredItemPositionFor;
			QuestRewardDialog questRewardDialog = new QuestRewardDialog(dialogs, dialogIdSupplier.getId(), rewardPacket,
					desiredItemPositionSupplier, (PacketsSender) linkedState);
			positionDialogNearMouse(questRewardDialog);
			stage.addActor(questRewardDialog);
			dialogs.add(questRewardDialog);
		}
	}

	@SuppressWarnings("unused")
	private class QuestRewardGoldRemovalPacketHandler extends UserInterfacePacketHandlerBase<QuestRewardGoldRemovalPacket>
	{
		@Override
		protected void doHandle(QuestRewardGoldRemovalPacket packet)
		{
			removeGoldFromQuestRewardDialog(packet.getGoldAmount());
		}

		private void removeGoldFromQuestRewardDialog(int goldAmount)
		{
			QuestRewardDialog dialog = dialogs.searchForDialog(QuestRewardDialog.class);
			dialog.updateGoldByDecreasingBy(goldAmount);
		}
	}

	@SuppressWarnings("unused")
	private class ScriptExecutionErrorPacketHandler extends UserInterfacePacketHandlerBase<ScriptExecutionErrorPacket>
	{
		@Override
		protected void doHandle(ScriptExecutionErrorPacket packet)
		{
			ConsoleDialog console = dialogs.searchForDialog(ConsoleDialog.class);
			console.addErrorMessage(packet.getError());
		}
	}

	@SuppressWarnings("unused")
	private class ScriptResultInfoPacketHandler extends UserInterfacePacketHandlerBase<ScriptResultInfoPacket>
	{
		@Override
		protected void doHandle(ScriptResultInfoPacket packet)
		{
			addInfoMessageToConsole(packet.getMessage());
		}

		private void addInfoMessageToConsole(String message)
		{
			consoleDialog.addMessage(message);
		}
	}

	@SuppressWarnings("unused")
	private class ShopItemsPacketHandler extends UserInterfacePacketHandlerBase<ShopItemsPacket>
	{
		@Override
		protected void doHandle(ShopItemsPacket packet)
		{
			openShopDialog(packet.getShopItems(), packet.getShopId());
		}

		private void openShopDialog(ShopItemPacket[] shopItemsPacket, long shopId)
		{
			ShopItem[] shopItems = Stream.of(shopItemsPacket).map(this::makeShopItem).toArray(ShopItem[]::new);
			openShopDialog(shopItems, shopId);
		}

		private ShopItem makeShopItem(ShopItemPacket packet)
		{
			Item item = ItemFactory.produceItem(packet.getItem());
			return new ShopItem(item, packet.getPrice());
		}

		private void openShopDialog(ShopItem[] shopItems, long shopId)
		{
			if (!dialogs.hasIdentifiableDialog(shopId))
			{
				ShopDialog shop = new ShopDialog("Shop", shopId, shopItems, popUpInfoStage, UserInterface.this,
						(PacketsSender) linkedState);
				shop.setPosition(0, 100);
				shop.pack();
				dialogs.add(shop);
				stage.addActor(shop);
			}
		}
	}

	@SuppressWarnings("unused")
	private class UnacceptableOperationPacketHandler extends UserInterfacePacketHandlerBase<UnacceptableOperationPacket>
	{
		private static final float DEFAULT_MESSAGE_TIMEOUT = 5.0f;

		@Override
		protected void doHandle(UnacceptableOperationPacket packet)
		{
			showTimedErrorMessage(packet.getErrorMessage(), DEFAULT_MESSAGE_TIMEOUT);
		}

		private void showTimedErrorMessage(String errorMessage, float timeout)
		{
			Label label = new TimedLabel(errorMessage, timeout);
			label.setColor(Color.RED);
			label.setX(960);
			label.setY(55);

			stage.addActor(label);
		}
	}

	@SuppressWarnings("unused")
	private class EquipItemPacketResponseHandler extends UserInterfacePacketHandlerBase<ItemEquippedSuccessfullyPacket>
	{
		@Override
		protected void doHandle(ItemEquippedSuccessfullyPacket packet)
		{
			EquipmentItem item = (EquipmentItem) inventoryDialog.removeItem(packet.getItemId());
			equipmentDialog.equipItem(EquipmentPosition.valueOf(packet.getEquipmentPosition()), item);
		}
	}

	@SuppressWarnings("unused")
	private class ItemTookOffSuccessfullyPacketHandler extends UserInterfacePacketHandlerBase<ItemTookOffSuccessfullyPacket>
	{
		@Override
		protected void doHandle(ItemTookOffSuccessfullyPacket packet)
		{
			EquipmentItem item = equipmentDialog.takeOffItem(EquipmentPosition.valueOf(packet.getEquipmentPosition()));
			ItemInventoryPosition destinationPosition = toItemInventoryPosition(packet.getDestinationPosition());
			inventoryDialog.addItem(item, destinationPosition);
		}

		private ItemInventoryPosition toItemInventoryPosition(InventoryPositionPacket position)
		{
			return new ItemInventoryPosition(position.getInventoryPageNumber(), new Point(position.getInventoryX(),
					position.getInventoryY()));
		}
	}
}