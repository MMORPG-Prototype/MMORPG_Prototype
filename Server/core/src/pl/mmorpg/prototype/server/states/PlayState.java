package pl.mmorpg.prototype.server.states;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.script.ScriptException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.server.ServerSettings;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.collision.interfaces.StackableCollisionMap;
import pl.mmorpg.prototype.server.collision.pixelmap.IntegerRectangle;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.collision.stackablemap.LayerCollisionMap;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.exceptions.CannotTargetItselfException;
import pl.mmorpg.prototype.server.headless.NullOrthogonalTiledMapRenderer;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.MapCollisionUnknownObject;
import pl.mmorpg.prototype.server.objects.ObjectsIdentifier;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.containers.GameContainer;
import pl.mmorpg.prototype.server.objects.items.GameItemsFactory;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.GameObjectsFactory;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.bodies.MonsterBody;
import pl.mmorpg.prototype.server.objects.monsters.spells.Fireball;
import pl.mmorpg.prototype.server.objects.spawners.MonsterSpawner;
import pl.mmorpg.prototype.server.objects.spawners.MonsterSpawnerUnit;
import pl.mmorpg.prototype.server.quests.events.Event;
import pl.mmorpg.prototype.server.quests.events.EventsHandler;
import pl.mmorpg.prototype.server.quests.events.MonsterKilledEvent;
import pl.mmorpg.prototype.server.quests.observers.RewardForFinishedQuestObserver;
import pl.mmorpg.prototype.server.resources.Assets;

public class PlayState extends State implements GameObjectsContainer, PacketsSender
{
	private final Server server;
	private final StateManager states;
	private final PixelCollisionMap<GameObject> collisionMap = new PixelCollisionMap<>(6400, 4800,
			GameObject.NULL_OBJECT);
	private final StackableCollisionMap<MonsterBody> deadBodiesCollisionMap = new LayerCollisionMap<>(214, 160, 30, 30);
	private final Map<Long, GameObject> gameObjects = new ConcurrentHashMap<>();
	private final Map<Long, GameContainer> gameContainers = new ConcurrentHashMap<>();
	private final TiledMapRenderer mapRenderer;
	private final ServerInputHandler inputHandler = new ServerInputHandler(this);
	private final GameObjectsFactory objectsFactory = new GameObjectsFactory(collisionMap, this);
	private final MonsterSpawner monsterSpawner = new MonsterSpawner(objectsFactory);
	private final Map<Integer, GameCommandsHandler> gameCommandsHandlers = new HashMap<>();
	private final RewardForFinishedQuestObserver rewardForFisnishedQuestObserver;
	private final EventsHandler questEventsHandler;

	private OrthographicCamera camera = new OrthographicCamera(6400, 4800);

	public PlayState(Server server, StateManager states)
	{
		this.server = server;
		this.states = states;
		rewardForFisnishedQuestObserver = new RewardForFinishedQuestObserver(this, this);
		questEventsHandler = new EventsHandler(rewardForFisnishedQuestObserver);
		camera.setToOrtho(false);

		collisionMap.setScale(1);
		TiledMap map = loadMap();

		if (ServerSettings.isHeadless)
			mapRenderer = new NullOrthogonalTiledMapRenderer();
		else
			mapRenderer = new OrthogonalTiledMapRenderer(map, Assets.getBatch());
		mapRenderer.setView(camera);

		Gdx.input.setInputProcessor(inputHandler);

		addNpcs();
		addGameObject(ObjectsIdentifiers.QUEST_BOARD, 100, 100);
	}

	private TiledMap loadMap()
	{
		TiledMap map = Assets.get("Map/tiled2.tmx");
		loadCollision(map);
		loadSpawners(map);
		return map;
	}

	private void loadCollision(TiledMap map)
	{
		MapObjects objects = map.getLayers().get("CollisionLayer").getObjects();
		Array<RectangleMapObject> collision = objects.getByType(RectangleMapObject.class);
		collision.forEach((rectangle) -> collisionMap.insert(new MapCollisionUnknownObject(rectangle.getRectangle())));
	}

	private void loadSpawners(TiledMap map)
	{
		MapObjects objects = map.getLayers().get("SpawnAreasLayer").getObjects();
		Array<RectangleMapObject> spawnerInfos = objects.getByType(RectangleMapObject.class);
		spawnerInfos.forEach(spawnerElement -> addSpawner(spawnerElement));
	}

	private void addSpawner(RectangleMapObject spawnerElement)
	{
		MapProperties properties = spawnerElement.getProperties();
		String monsterIdentifier = (String) properties.get("MonsterType");
		@SuppressWarnings("unchecked")
		Class<? extends Monster> monsterType = (Class<? extends Monster>) ObjectsIdentifier
				.getObjectType(monsterIdentifier);
		float spawnInterval = (float) properties.get("spawnInterval");
		int maximumMonsterAmount = (int) properties.get("MaximumMonsterAmount");
		IntegerRectangle spawnArea = new IntegerRectangle(spawnerElement.getRectangle());
		MonsterSpawnerUnit spawnerUnit = new MonsterSpawnerUnit(monsterType, spawnArea, maximumMonsterAmount,
				spawnInterval);
		monsterSpawner.addSpawner(spawnerUnit);
	}

	private void addNpcs()
	{
		addGameObject(ObjectsIdentifiers.GROCERY_NPC, 400, 400);
		addGameObject(ObjectsIdentifiers.QUEST_DIALOG_NPC, 300, 300);
	}

	void addGameObject(String identifier, int x, int y)
	{
		GameObject gameObject = objectsFactory.produce(ObjectsIdentifier.getObjectType(identifier), IdSupplier.getId());
		gameObject.setPosition(x, y);
		addGameObject(gameObject);
	}

	void addGameObject(GameObject gameObject)
	{
		collisionMap.insert(gameObject);
		add(gameObject);
		if (gameObject instanceof Monster)
			sendToAll(PacketsMaker.makeCreationPacket((Monster) gameObject));
		else
			sendToAll(PacketsMaker.makeCreationPacket(gameObject));
	}

	@Override
	public void render(Batch batch)
	{
		batch.end();
		mapRenderer.render();
		batch.begin();
		Collection<GameObject> toRender = gameObjects.values();
		for (GameObject object : toRender)
			object.render(batch);

	}

	@Override
	public void update(float deltaTime)
	{
		camera.update();
		for (GameObject object : gameObjects.values())
			object.update(deltaTime);
		handleSpawner(deltaTime);
		questEventsHandler.processEvents();
	}

	private void handleSpawner(float deltaTime)
	{
		monsterSpawner.updateSpawners(deltaTime);
		Monster spawnedMonster = monsterSpawner.getNewMonster(IdSupplier.getId());
		if (spawnedMonster != null)
			addGameObject(spawnedMonster);
	}

	@Override
	public void add(GameObject object)
	{
		gameObjects.put(object.getId(), object);
	}

	public void addDeadBody(MonsterBody monsterBody)
	{
		add(monsterBody);
		GameContainer container = monsterBody.getContainer();
		gameContainers.put(container.getId(), container);
		deadBodiesCollisionMap.insert(monsterBody);
	}

	@Override
	public GameObject remove(long objectId)
	{
		GameObject object = gameObjects.remove(objectId);
		object.onRemoval();
		collisionMap.remove(object);
		if (object instanceof Monster)
			monsterSpawner.monsterHasDied(object.getId());
		return object;
	}

	@Override
	public Map<Long, GameObject> getGameObjects()
	{
		return gameObjects;
	}

	public PixelCollisionMap<GameObject> getCollisionMap()
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

	@Override
	public void sendToAll(Object packet)
	{
		server.sendToAllTCP(packet);
	}

	@Override
	public void sendTo(int connectionId, Object packet)
	{
		server.sendToTCP(connectionId, packet);
	}

	public void objectTargeted(Monster source, Monster target)
	{
		if (source == target)
			throw new CannotTargetItselfException();
		source.targetMonster(target);
	}

	public void playerKilled(PlayerCharacter playerCharacter, Monster target)
	{
		sendToAll(
				PacketsMaker.makeExperienceGainPacket(playerCharacter.getId(), target.getProperties().experienceGain));
		Event monsterKilledEvent = new MonsterKilledEvent(target.getIdentifier());
		monsterKilledEvent.addReceiver(playerCharacter);
		enqueueEvent(monsterKilledEvent);
	}

	public void enqueueEvent(Event event)
	{
		questEventsHandler.enqueueEvent(event);
	}

	public void createFireball(PlayerCharacter source, Monster targetedMonster)
	{
		Fireball fireball = new Fireball(IdSupplier.getId(), source, this, this);
		fireball.setTarget(targetedMonster);
		fireball.setPosition(source.getX(), source.getY());
		gameObjects.put(fireball.getId(), fireball);
		sendToAll(PacketsMaker.makeCreationPacket(fireball));
	}

	public boolean hasContainer(int gameX, int gameY)
	{
		return deadBodiesCollisionMap.getTopObject(gameX, gameY) != null;
	}

	public GameContainer getContainer(int gameX, int gameY)
	{
		MonsterBody monsterBody = deadBodiesCollisionMap.getTopObject(gameX, gameY);
		GameContainer containerWithLoot = monsterBody.getContainer();
		return containerWithLoot;
	}

	public GameContainer getContainer(long containerId)
	{
		return gameContainers.get(containerId);
	}

	public void addGameCommandsHandler(UserInfo userInfo)
	{
		gameCommandsHandlers.put(userInfo.user.getId(), new GameCommandsHandler(this, userInfo));
	}

	public void removeGameCommandsHandler(Integer userId)
	{
		gameCommandsHandlers.remove(userId);
	}

	public Object executeCode(String code, Integer userId) throws ScriptException
	{
		return gameCommandsHandlers.get(userId).execute(code);
	}

	public void addItem(ItemIdentifiers identifier, int amount, UserCharacter userCharacter,
			InventoryPosition inventoryPosition)
	{
		PlayerCharacter player = (PlayerCharacter) gameObjects.get((long) userCharacter.getId());
		if (player.hasItemInPosition(inventoryPosition))
		{
			sendTo(player.getConnectionId(), PacketsMaker.makeUnacceptableOperationPacket("Inventory field is taken"));
			return;
		}

		Item item = GameItemsFactory.produce(identifier, amount, IdSupplier.getId(), inventoryPosition);
		player.addItem(item);
		sendTo(player.getConnectionId(), PacketsMaker.makeItemPacket(item));
	}
}
