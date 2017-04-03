package pl.mmorpg.prototype.client.states;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.exceptions.NotImplementedException;
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
	private UserInterface userInterface;

	private Item mouseHoldingItem = null;


	public PlayState(StateManager states, Client client)
	{
		this.client = client;
		inputHandler = new NullInputHandler();
		this.states = states;
		inputMultiplexer = new InputMultiplexer();
	}

	public void initialize(UserCharacterDataPacket character)
	{
		player = new Player(character.id);
		gameObjects.put((long) character.id, player);
		inputHandler = new PlayInputContinuousHandler(client, character);
		userInterface = new UserInterface(this, character);
		inputMultiplexer.addProcessor(new PlayInputSingleHandle(userInterface.getDialogs()));
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(inputHandler);
		userInterface.mapWithKeys();
		showDialogs();
	}

	private void showDialogs()
	{
		stage.addActor(userInterface.menuDialog);
		userInterface.inventoryDialog.show(stage);
		userInterface.statisticsDialog.show(stage);
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
		userInterface.update();
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
		userInterface.clear();
		stage.clear();
		inputMultiplexer.clear();
	}

	public Stage getStage()
	{
		return stage;
	}

	public void showOrHideDialog(Class<? extends Table> dialogType)
	{
		userInterface.showOrHide(dialogType);
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
		userInterface.inventoryDialog.addItem(newItem);
	}
}
