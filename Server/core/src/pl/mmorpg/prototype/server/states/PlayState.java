package pl.mmorpg.prototype.server.states;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.TestObject;

public class PlayState extends State
{
	private Server server;
	private StateManager states;
	private CollisionMap collisionMap = new CollisionMap(800, 400);
	private Map<Long, GameObject> gameObjects = new ConcurrentHashMap<>();
	private TestInputHandler inputHandler;

	public PlayState(Server server, StateManager states)
	{
		this.server = server;
		this.states = states;
		TestObject testObject = new TestObject(collisionMap);
		gameObjects.put((long) 0, testObject);
		collisionMap.insert(testObject);
		inputHandler = new TestInputHandler(testObject, collisionMap);
		Gdx.input.setInputProcessor(inputHandler);
	}

	@Override
	public void render(SpriteBatch batch)
	{
		// collisionMap.render(batch);
		for (GameObject object : gameObjects.values())
			object.render(batch);
	}

	@Override
	public void update(float deltaTime)
	{
		inputHandler.process();
		for (GameObject object : gameObjects.values())
			object.update(deltaTime);
	}

}
