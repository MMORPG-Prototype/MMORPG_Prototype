package pl.mmorpg.prototype.client.states;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.exceptions.NotImplementedException;
import pl.mmorpg.prototype.client.input.DialogManipulator;
import pl.mmorpg.prototype.client.input.InputMultiplexer;
import pl.mmorpg.prototype.client.input.InputProcessorAdapter;
import pl.mmorpg.prototype.client.input.NullInputHandler;
import pl.mmorpg.prototype.client.input.PlayInputContinuousHandler;
import pl.mmorpg.prototype.client.input.PlayInputSingleHandle;
import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.items.ItemFactory;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.Player;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.dialogs.CustomDialogs;
import pl.mmorpg.prototype.client.states.dialogs.InventoryDialog;
import pl.mmorpg.prototype.client.states.dialogs.MenuDialog;
import pl.mmorpg.prototype.client.states.dialogs.StatisticsDialog;
import pl.mmorpg.prototype.client.states.dialogs.components.InventoryField;
import pl.mmorpg.prototype.client.states.helpers.InventoryManager;
import pl.mmorpg.prototype.clientservercommon.packets.DisconnectPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class PlayState implements State, GameObjectsContainer
{
	private Stage stage = Assets.getStage();

	private Client client;
	private StateManager states;
	private Player player;
	private Map<Long, GameObject> gameObjects = new ConcurrentHashMap<>();
	private InputProcessorAdapter inputHandler;
	private InputMultiplexer inputMultiplexer;
	private final MenuDialog menuDialog = new MenuDialog(this);
	private final InventoryDialog inventoryDialog = new InventoryDialog(this);
	private StatisticsDialog statisticsDialog;
	private final DialogManipulator dialogs = new DialogManipulator();
	
	private Item mouseHoldingItem = null;

	private Dialog lastActiveDialog;

	public PlayState(StateManager states, Client client)
	{
		this.client = client;
		inputHandler = new NullInputHandler();
		this.states = states;
		inputMultiplexer = new InputMultiplexer();
		lastActiveDialog = inventoryDialog;
	}

	public void initialize(UserCharacterDataPacket character)
	{
		player = new Player(character.id);
		gameObjects.put((long) character.id, player);
		inputHandler = new PlayInputContinuousHandler(client, character);
		statisticsDialog = new StatisticsDialog(character);
		inputMultiplexer.addProcessor(new PlayInputSingleHandle(dialogs));
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(inputHandler);
		mapDialogs();
		showDialogs();
	}

	private void mapDialogs()
	{
		dialogs.map(Keys.M, menuDialog);
		dialogs.map(Keys.I, inventoryDialog);
		dialogs.map(Keys.C, statisticsDialog);
	}

	private void showDialogs()
	{
		stage.addActor(menuDialog);
		inventoryDialog.show(stage);
		statisticsDialog.show(stage);
	}

	@Override
	public void render(SpriteBatch batch)
	{
		batch.begin();
		for (GameObject object : gameObjects.values())
			object.render(batch);

		batch.end();
		stage.draw();
		batch.begin();
		if (mouseHoldingItem != null)
			mouseHoldingItem.renderWhenDragged(batch);
		batch.end();
	}

	@Override
	public void update(float deltaTime)
	{
		stage.act();
		inputHandler.process();
		manageDialogZIndexes();
	}

	private void manageDialogZIndexes()
	{
		List<Dialog> mouseHoveringDialogs = getDialogsOnMousePosition();
		if (!mouseHoveringDialogs.isEmpty() && !containsLastMouseHoveringDialog(mouseHoveringDialogs))
		{
			lastActiveDialog = mouseHoveringDialogs.iterator().next();
			lastActiveDialog.toFront();
		}

	}

	private boolean containsLastMouseHoveringDialog(List<Dialog> mouseHoveringDialogs)
	{
		for (Dialog dialog : mouseHoveringDialogs)
			if (dialog == lastActiveDialog)
				return true;
		return false;
	}

	private List<Dialog> getDialogsOnMousePosition()
	{
		List<Dialog> mouseHoveringDialogs = new LinkedList<>();
		for (Dialog dialog : dialogs.getAll())
			if (mouseHovers(dialog))
				mouseHoveringDialogs.add(dialog);
		return mouseHoveringDialogs;
	}

	private boolean mouseHovers(Actor actor)
	{
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		return mouseX >= actor.getX() && mouseX <= actor.getRight() && mouseY >= actor.getY()
				&& mouseY <= actor.getTop();
	}

	@Override
	public void add(GameObject object)
	{
		gameObjects.put(object.getId(), object);
	}

	@Override
	public void removeObject(long id)
	{
		gameObjects.remove(id);
	}

	@Override
	public Map<Long, GameObject> getGameObjects()
	{
		return gameObjects;
	}

	@Override
	public GameObject getObject(long id)
	{
		return gameObjects.get(id);
	}

	@Override
	public void reactivate()
	{
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	public void userWantsToDisconnect()
	{
		client.sendTCP(new DisconnectPacket());
		reset();
		states.push(new SettingsChoosingState(client, states));

	}

	private void reset()
	{
		inputHandler = new NullInputHandler();
		gameObjects.clear();
		dialogs.clear();
		stage.clear();
		inputMultiplexer.clear();
	}

	public Stage getStage()
	{
		return stage;
	}

	public void showOrHideDialog(CustomDialogs dialogType)
	{
		dialogs.showOrHide(dialogType);
	}

	public void userWantsToChangeCharacter()
	{
		throw new NotImplementedException();
	}

	public void inventoryFieldClicked(InventoryField inventoryField)
	{
		mouseHoldingItem = InventoryManager.inventoryFieldClicked(mouseHoldingItem, inventoryField);
	}

	public void newItemPacketReceived(CharacterItemDataPacket itemData)
	{
		Item newItem = ItemFactory.produceItem(itemData);
		inventoryDialog.addItem(newItem);
	}
}
