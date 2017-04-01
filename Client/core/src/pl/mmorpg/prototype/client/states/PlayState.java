package pl.mmorpg.prototype.client.states;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.input.InputProcessorAdapter;
import pl.mmorpg.prototype.client.input.NullInputHandler;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.Player;

public class PlayState implements State, GameObjectsContainer
{
	private Client client;
	private StateManager states;
	private Player player;
	private Map<Long, GameObject> gameObjects = new ConcurrentHashMap<>();
	private InputProcessorAdapter inputHandler;

	public PlayState(StateManager states)
	{
		inputHandler = new NullInputHandler();
		this.states = states;
	}

	public void initialize(Client client)
	{
		this.client = client;
		player = new Player(client.getID());
		gameObjects.put(player.getId(), player);
		inputHandler = new PlayInputHandler(client);
		Gdx.input.setInputProcessor(inputHandler);
	}

	@Override
	public void render(SpriteBatch batch)
	{
		for(GameObject object : gameObjects.values())
			object.render(batch);
	}

	@Override
	public void update(float deltaTime)
	{
		inputHandler.process();
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

}
