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

import pl.mmorpg.prototype.clientservercommon.EquipmentPosition;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.server.ServerSettings;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.clientservercommon.LevelCalculator;
import pl.mmorpg.prototype.server.collision.interfaces.StackableCollisionMap;
import pl.mmorpg.prototype.server.collision.pixelmap.IntegerRectangle;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.collision.stackablemap.LayerCollisionMap;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.database.entities.Character;
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
import pl.mmorpg.prototype.server.objects.monsters.spells.objects.ThrowableObject;
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
	private final PixelCollisionMap<GameObject> collisionMap;
	private final StackableCollisionMap<MonsterBody> deadBodiesCollisionMap = new LayerCollisionMap<>(214, 160, 30, 30);
	private final Map<Long, GameObject> gameObjects = new ConcurrentHashMap<>();
	private final Map<Long, GameContainer> gameContainers = new ConcurrentHashMap<>();
	private final TiledMapRenderer mapRenderer;
	private final GameObjectsFactory objectsFactory;
	private final MonsterSpawner monsterSpawner;
	private final Map<Integer, GameCommandsHandler> gameCommandsHandlers = new HashMap<>();
	private final EventsHandler questEventsHandler;

	private final OrthographicCamera camera = new OrthographicCamera();
	private final ServerInputHandler inputHandler = new ServerInputHandler(new PlayStateInputActions(camera));

	public PlayState(Server server)
	{
		this.server = server;
		RewardForFinishedQuestObserver rewardForFinishedQuestObserver = new RewardForFinishedQuestObserver(this, this);
		questEventsHandler = new EventsHandler(rewardForFinishedQuestObserver, (PacketsSender) this);
		camera.setToOrtho(false);
		//low values not to use too much cpu during debugging
		camera.viewportWidth = 700;
		camera.viewportHeight = 400;

		TiledMap map = Assets.get("Map/tiled2.tmx");
		Integer mapHeight = getMapProperty(map, "height") * getMapProperty(map, "tileheight");
		Integer mapWidth = getMapProperty(map, "width") * getMapProperty(map, "tilewidth");
		collisionMap = new PixelCollisionMap<>(mapWidth, mapHeight, 1, GameObject.NULL_OBJECT);

		objectsFactory = new GameObjectsFactory(collisionMap, this);
		monsterSpawner = new MonsterSpawner(objectsFactory);

		loadMap(map);

		if (ServerSettings.isHeadless)
			mapRenderer = new NullOrthogonalTiledMapRenderer();
		else
			mapRenderer = new OrthogonalTiledMapRenderer(map, Assets.getBatch());

		Gdx.input.setInputProcessor(inputHandler);

		addNpcs();
		addGameObject(ObjectsIdentifiers.QUEST_BOARD, 100, 100);
	}

	private Integer getMapProperty(TiledMap map, String name)
	{
		return map.getProperties().get(name, Integer.class);
	}

	private void loadMap(TiledMap map)
	{
		loadCollision(map);
		loadSpawners(map);
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
		spawnerInfos.forEach(this::addSpawner);
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
		else if (gameObject instanceof ThrowableObject)
			sendToAll(PacketsMaker
					.makeCreationPacket((ThrowableObject) gameObject, ((ThrowableObject) gameObject).getTarget()));
		else
			sendToAll(PacketsMaker.makeCreationPacket(gameObject));
	}

	@Override
	public void render(Batch batch)
	{
		batch.setProjectionMatrix(camera.combined);
		batch.end();
		mapRenderer.render();
		mapRenderer.setView(camera);
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
		inputHandler.process();
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

	public void playerKilledMonster(PlayerCharacter playerCharacter, Monster target)
	{
		handleExperienceGain(playerCharacter, target);
		Event monsterKilledEvent = new MonsterKilledEvent(target.getIdentifier());
		monsterKilledEvent.addReceiver(playerCharacter);
		enqueueQuestEvent(monsterKilledEvent);
	}

	private void handleExperienceGain(PlayerCharacter playerCharacter, Monster target)
	{
		int playerLevel = playerCharacter.getProperties().level;
		int previousExperience = playerCharacter.getProperties().experience;
		int experienceGain = target.getProperties().experienceGain;
		if (new LevelCalculator().doesQualifyForLevelingUp(playerLevel, previousExperience, experienceGain))
		{
			int levelUpPoints = 5;
			playerCharacter.addLevel(levelUpPoints);
			sendToAll(PacketsMaker.makeLevelUpPacket(playerCharacter.getId(), levelUpPoints));
		}

		playerCharacter.addExperience(target.getProperties().experienceGain);
		sendToAll(PacketsMaker.makeExperienceGainPacket(playerCharacter.getId(), target.getProperties().experienceGain));
	}

	public void enqueueQuestEvent(Event event)
	{
		questEventsHandler.enqueueEvent(event);
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

	public void addItem(ItemIdentifiers identifier, int amount, Character userCharacter,
			InventoryPosition inventoryPosition)
	{
		PlayerCharacter player = (PlayerCharacter) gameObjects.get((long) userCharacter.getId());
		if (player.hasItemInPosition(inventoryPosition))
		{
			sendTo(player.getConnectionId(), PacketsMaker.makeUnacceptableOperationPacket("Inventory field is taken"));
			return;
		}

		Item item = GameItemsFactory.produce(identifier, amount, IdSupplier.getId(), inventoryPosition, EquipmentPosition.NONE);
		player.addItemDenyStacking(item);
		sendTo(player.getConnectionId(), PacketsMaker.makeItemPacket(item));
	}
}
