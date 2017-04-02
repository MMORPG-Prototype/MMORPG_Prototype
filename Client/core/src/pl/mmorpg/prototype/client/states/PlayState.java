package pl.mmorpg.prototype.client.states;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.input.InputProcessorAdapter;
import pl.mmorpg.prototype.client.input.MyInputMultiplexer;
import pl.mmorpg.prototype.client.input.NullInputHandler;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.Player;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.dialogs.DisconnectDialog;
import pl.mmorpg.prototype.client.states.dialogs.InventoryDialog;
import pl.mmorpg.prototype.clientservercommon.packets.DisconnectPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class PlayState implements State, GameObjectsContainer
{
	private Stage stage = Assets.getStage();

	private Client client;
	private StateManager states;
	private Player player;
	private Map<Long, GameObject> gameObjects = new ConcurrentHashMap<>();
	private InputProcessorAdapter inputHandler;
	private MyInputMultiplexer inputMultiplexer;
	private DisconnectDialog disconnectDialog = new DisconnectDialog(this);
	private InventoryDialog inventoryDialog = new InventoryDialog(this);

	public PlayState(StateManager states, Client client)
	{
		this.client = client;
		inputHandler = new NullInputHandler();
		this.states = states;
		inputMultiplexer = new MyInputMultiplexer();
		stage.addActor(disconnectDialog);
		stage.addActor(inventoryDialog);
	}

	public void initialize(UserCharacterDataPacket character)
	{
		player = new Player(character.id);
		gameObjects.put((long) character.id, player);
		inputHandler = new PlayInputHandler(client, character);
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(inputHandler);
	}

	@Override
	public void render(SpriteBatch batch)
	{
		batch.begin();
		for(GameObject object : gameObjects.values())
			object.render(batch);

		batch.end();
		stage.draw();
	}

	@Override
	public void update(float deltaTime)
	{
		stage.act();
		inputHandler.process();
		Gdx.input.setInputProcessor(inputMultiplexer);
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
		inventoryDialog.pack();
		disconnectDialog.pack();
		stage.addActor(inventoryDialog);
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
		stage.clear();
		gameObjects.clear();
		inputMultiplexer.clear();
	}

}
