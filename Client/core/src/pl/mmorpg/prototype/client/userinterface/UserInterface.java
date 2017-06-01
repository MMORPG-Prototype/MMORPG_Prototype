package pl.mmorpg.prototype.client.userinterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.items.StackableItem;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.helpers.UserInterfaceManager;
import pl.mmorpg.prototype.client.userinterface.dialogs.ChatDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.EquipmentDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.HitPointManaPointPane;
import pl.mmorpg.prototype.client.userinterface.dialogs.InventoryDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.MenuDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.OpenContainerDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.QuickAccessDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.ShortcutBarPane;
import pl.mmorpg.prototype.client.userinterface.dialogs.StatisticsDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.DialogIdSupplier;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryField;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.TimedLabel;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessageReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class UserInterface
{
	private final DialogIdSupplier dialogIdSupplier = new DialogIdSupplier();

	private final Stage stage = Assets.getStage();
	private final MenuDialog menuDialog;
	private final InventoryDialog inventoryDialog;
	private final StatisticsDialog statisticsDialog;
	private final ShortcutBarPane standardBarDialog;
	private final HitPointManaPointPane hpMpDialog;
	private final QuickAccessDialog quickAccessDialog;
	private final EquipmentDialog equipmentDialog;
	private final ChatDialog chatDialog;
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
	}

	public void draw(SpriteBatch batch)
	{
		stage.draw();
		batch.begin();
		if (mousePointerToItem.item != null)
			mousePointerToItem.item.renderWhenDragged(batch);
		batch.end();
	}

	public void update()
	{
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

	public InputProcessor getStage()
	{
		return stage;
	}

	public void inventoryFieldClicked(InventoryField inventoryField)
	{
		mousePointerToItem = UserInterfaceManager.inventoryFieldClicked(mousePointerToItem, inventoryField,
				inventoryDialog);
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

	public void addItemToInventory(Item newItem)
	{
		if(newItem instanceof StackableItem)
			inventoryDialog.addItem((StackableItem)newItem);
		else
			inventoryDialog.addItem(newItem);
	}

	public void quickAccesButtonClicked(InventoryField field)
	{
		InventoryField lastFieldClicked = inventoryDialog.getLastFieldWithItemClicked();
		mousePointerToItem = UserInterfaceManager.quickAccessFieldClicked(mousePointerToItem, field, lastFieldClicked);
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
		Item usedItem = (Item) inventoryDialog.useItem(itemId);

		if (usedItem.shouldBeRemoved() && quickAccessDialog.hasItem(usedItem))
			quickAccessDialog.removeItem(usedItem);

	}

	public void containerOpened(CharacterItemDataPacket[] contentItems, long containerId)
	{
		if (!dialogs.hasIdentifiableDialog(containerId))
			createAndOpenContainerDialog(contentItems, containerId);
	}

	private void createAndOpenContainerDialog(CharacterItemDataPacket[] contentItems, long containerId)
	{
		Dialog containerDialog = new OpenContainerDialog(contentItems, "Container", dialogs,
				(PacketsSender) linkedState, containerId);
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
		if(dialogs.hasIdentifiableDialog(containerId))
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

}