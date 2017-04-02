package pl.mmorpg.prototype.server.states;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;

public class PlayState extends State implements GameObjectsContainer
{
	private Server server;
	private StateManager states;
	private CollisionMap collisionMap = new CollisionMap(800, 400);
	private Map<Long, GameObject> gameObjects = new ConcurrentHashMap<>();

	public PlayState(Server server, StateManager states)
	{
		this.server = server;
		this.states = states;
	}

	@Override
	public void render(SpriteBatch batch)
	{
		for (GameObject object : gameObjects.values())
			object.render(batch);
	}

	@Override
	public void update(float deltaTime)
	{
		for (GameObject object : gameObjects.values())
			object.update(deltaTime);
	}

	@Override
	public void add(GameObject object)
	{
		gameObjects.put(object.getId(), object);
	}

	@Override
	public GameObject remove(long objectId)
	{
		GameObject object = gameObjects.remove(objectId);
		collisionMap.remove(object);
		return object;
	}

	@Override
	public Map<Long, GameObject> getGameObjects()
	{
		return gameObjects;
	}

	public CollisionMap getCollisionMap()
	{
		return collisionMap;
	}

	@Override
	public GameObject getObject(long id)
	{
		return gameObjects.get(id);
	}

	@Override
	public boolean has(long objectId)
	{
		return gameObjects.containsKey(objectId);
	}

}
