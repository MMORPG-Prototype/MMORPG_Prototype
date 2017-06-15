package pl.mmorpg.prototype.server.states;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.mockito.Mockito;

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

import pl.mmorpg.prototype.server.ServerSettings;
import pl.mmorpg.prototype.server.collision.interfaces.StackableCollisionMap;
import pl.mmorpg.prototype.server.collision.pixelmap.IntegerRectangle;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.collision.stackablemap.LayerCollisionMap;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.exceptions.CannotTargetItselfException;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.MapCollisionUnknownObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.containers.GameContainer;
import pl.mmorpg.prototype.server.objects.monsters.GreenDragon;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.MonsterIdentifier;
import pl.mmorpg.prototype.server.objects.monsters.MonstersFactory;
import pl.mmorpg.prototype.server.objects.monsters.RedDragon;
import pl.mmorpg.prototype.server.objects.monsters.Skeleton;
import pl.mmorpg.prototype.server.objects.monsters.bodies.MonsterBody;
import pl.mmorpg.prototype.server.objects.monsters.npcs.GroceryShopNpc;
import pl.mmorpg.prototype.server.objects.monsters.spells.Fireball;
import pl.mmorpg.prototype.server.objects.spawners.MonsterSpawner;
import pl.mmorpg.prototype.server.objects.spawners.MonsterSpawnerUnit;
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
	private final MonsterSpawner monsterSpawner = new MonsterSpawner(new MonstersFactory(collisionMap, this));

	private OrthographicCamera camera = new OrthographicCamera(6400, 4800);

	public PlayState(Server server, StateManager states)
	{
		this.server = server;
		this.states = states;
		camera.setToOrtho(false);

		collisionMap.setScale(1);
		TiledMap map = loadMap();

		if(ServerSettings.isHeadless)
		{
			mapRenderer = Mockito.mock(OrthogonalTiledMapRenderer.class);
		}
		else
			mapRenderer = new OrthogonalTiledMapRenderer(map, Assets.getBatch());
		mapRenderer.setView(camera);

		Gdx.input.setInputProcessor(inputHandler);

		addNpcs();
		// addMonsterSpawnerUnits();
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
		Class<? extends Monster> monsterType = MonsterIdentifier.getMonsterType(monsterIdentifier);
		float spawnInterval = (float) properties.get("spawnInterval");
		int maximumMonsterAmount = (int) properties.get("MaximumMonsterAmount");
		IntegerRectangle spawnArea = new IntegerRectangle(spawnerElement.getRectangle());
		MonsterSpawnerUnit spawnerUnit = new MonsterSpawnerUnit(monsterType, spawnArea, maximumMonsterAmount,
				spawnInterval);
		monsterSpawner.addSpawner(spawnerUnit);
	}

	private void addMonsterSpawnerUnits()
	{
		MonsterSpawnerUnit greenDragonSpawnerUnit = new MonsterSpawnerUnit(GreenDragon.class,
				new IntegerRectangle(100, 100, 400, 400), 1);
		MonsterSpawnerUnit redDragonSpawnerUnit = new MonsterSpawnerUnit(RedDragon.class,
				new IntegerRectangle(100, 100, 400, 400), 1);
		monsterSpawner.addSpawner(greenDragonSpawnerUnit);
		monsterSpawner.addSpawner(redDragonSpawnerUnit);
	}

	private void addNpcs()
	{
		GroceryShopNpc groceryShop = new GroceryShopNpc(IdSupplier.getId(), collisionMap, this);
		groceryShop.setPosition(400, 400);
		addMonster(groceryShop);
	}

	public void addGreenDragon()
	{
		Monster dragon = new GreenDragon(IdSupplier.getId(), collisionMap, this);
		addMonster(dragon);
	}

	public void addRedDragon()
	{
		Monster dragon = new RedDragon(IdSupplier.getId(), collisionMap, this);
		addMonster(dragon);
	}

	public void addSkeleton()
	{
		Monster skeleton = new Skeleton(IdSupplier.getId(), collisionMap, this);
		addMonster(skeleton);
	}

	private void addMonster(Monster monster)
	{
		collisionMap.insert(monster);
		add(monster);
		server.sendToAllTCP(PacketsMaker.makeCreationPacket(monster));
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
	}

	private void handleSpawner(float deltaTime)
	{
		monsterSpawner.updateSpawners(deltaTime);
		Monster spawnedMonster = monsterSpawner.getNewMonster(IdSupplier.getId());
		if (spawnedMonster != null)
			addMonster(spawnedMonster);
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

	public void objectTargeted(Monster source, Monster target)
	{
		if (source == target)
			throw new CannotTargetItselfException();
		source.targetMonster(target);
	}

	public void playerKilled(PlayerCharacter playerCharacter, Monster target)
	{
		server.sendToAllTCP(
				PacketsMaker.makeExperienceGainPacket(playerCharacter.getId(), target.getProperties().experienceGain));
	}

	public void createFireball(PlayerCharacter source, Monster targetedMonster)
	{
		Fireball fireball = new Fireball(IdSupplier.getId(), source, this, this);
		fireball.setTarget(targetedMonster);
		fireball.setPosition(source.getX(), source.getY());
		gameObjects.put(fireball.getId(), fireball);
		server.sendToAllTCP(PacketsMaker.makeCreationPacket(fireball));
	}

	@Override
	public void sendTo(int connectionId, Object packet)
	{
		server.sendToTCP(connectionId, packet);
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

}
