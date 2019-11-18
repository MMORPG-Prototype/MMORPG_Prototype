package pl.mmorpg.prototype.client.states;

import java.awt.Point;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.client.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.client.collision.pixelmap.UndefinedStaticObjectCreator;
import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.exceptions.GameException;
import pl.mmorpg.prototype.client.input.InputMultiplexer;
import pl.mmorpg.prototype.client.input.InputProcessorAdapter;
import pl.mmorpg.prototype.client.input.NullInputHandler;
import pl.mmorpg.prototype.client.input.PlayInputContinuousHandler;
import pl.mmorpg.prototype.client.input.PlayInputSingleHandle;
import pl.mmorpg.prototype.client.items.ItemFactory;
import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.items.equipment.EquipmentItem;
import pl.mmorpg.prototype.client.objects.*;
import pl.mmorpg.prototype.client.objects.graphic.BloodAnimation;
import pl.mmorpg.prototype.client.objects.graphic.DefinedAreaCloudCluster;
import pl.mmorpg.prototype.client.objects.graphic.ExperienceGainLabel;
import pl.mmorpg.prototype.client.objects.graphic.FadingRedRectangle;
import pl.mmorpg.prototype.client.objects.graphic.FireDamageLabel;
import pl.mmorpg.prototype.client.objects.graphic.GameWorldLabel;
import pl.mmorpg.prototype.client.objects.graphic.GraphicGameObject;
import pl.mmorpg.prototype.client.objects.graphic.HealLabel;
import pl.mmorpg.prototype.client.objects.graphic.ManaReplenishLabel;
import pl.mmorpg.prototype.client.objects.graphic.NormalDamageLabel;
import pl.mmorpg.prototype.client.objects.graphic.helpers.ObjectGraphicEffectPlacer;
import pl.mmorpg.prototype.client.objects.graphic.helpers.ObjectHighlighter;
import pl.mmorpg.prototype.client.objects.graphic.helpers.ObjectInfoDisplayer;
import pl.mmorpg.prototype.client.objects.icons.items.Item;
import pl.mmorpg.prototype.client.objects.interactive.QuestBoard;
import pl.mmorpg.prototype.client.objects.monsters.Monster;
import pl.mmorpg.prototype.client.objects.monsters.MonstersFactory;
import pl.mmorpg.prototype.client.objects.monsters.npcs.Npc;
import pl.mmorpg.prototype.client.objects.monsters.npcs.QuestDialogNpc;
import pl.mmorpg.prototype.client.objects.monsters.npcs.Shop;
import pl.mmorpg.prototype.client.objects.spells.ThrowableObject;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerCategory;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.path.search.BestFirstPathFinder;
import pl.mmorpg.prototype.client.path.search.PathFinder;
import pl.mmorpg.prototype.client.path.search.collisionDetectors.CollisionDetector;
import pl.mmorpg.prototype.client.path.search.collisionDetectors.GameObjectCollisionDetector;
import pl.mmorpg.prototype.client.path.search.distanceComparators.ManhattanDistanceComparator;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.helpers.GameObjectsContainer;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.clientservercommon.EquipmentPosition;
import pl.mmorpg.prototype.clientservercommon.Settings;
import pl.mmorpg.prototype.clientservercommon.packets.*;
import pl.mmorpg.prototype.clientservercommon.packets.damage.FireDamagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.damage.NormalDamagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.InventoryPositionPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.levelup.LevelUpPacket;
import pl.mmorpg.prototype.clientservercommon.packets.levelup.LevelUpPointOnDexteritySpentSuccessfullyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.levelup.LevelUpPointOnIntelligenceSpentSuccessfullyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.levelup.LevelUpPointOnStrengthSpentSuccessfullyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ExperienceGainPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.MonsterTargetingReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.NpcStartDialogPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.OpenContainterPacket;
import pl.mmorpg.prototype.clientservercommon.packets.quest.event.NpcDialogEventPacket;

public class PlayState implements State, GameObjectsContainer, PacketsSender, GraphicObjectsContainer
{
	private static final int CAMERA_WIDTH = 900;
	private static final int CAMERA_HEIGHT = 500;
	private final Client client;
	private final StateManager states;
	private final Map<Long, GameObject> gameObjects = new ConcurrentHashMap<>();
	private final BlockingQueue<GraphicGameObject> clientGraphics = new LinkedBlockingQueue<>();
	private final ObjectGraphicEffectPlacer objectHighlighter = new ObjectHighlighter(clientGraphics);
	private final ObjectGraphicEffectPlacer objectInfoDisplayer = new ObjectInfoDisplayer(clientGraphics);
	private final InputMultiplexer inputMultiplexer = new InputMultiplexer();
	private final PacketHandlerRegisterer packetHandlerRegisterer;
	private final OrthographicCamera camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
	private TiledMap map;
	private TiledMapRenderer mapRenderer;
	private PixelCollisionMap<GameObject> collisionMap;
	private InputProcessorAdapter inputHandler;
	private Player player;
	private UserInterface userInterface;
	private boolean isInitialized = false;
	private float currentGameMouseX = -1f;
	private float currentGameMouseY = -1f;
	private int mapWidth = Integer.MAX_VALUE;
	private int mapHeight = Integer.MAX_VALUE;

	public PlayState(StateManager states, Client client, PacketHandlerRegisterer packetHandlerRegisterer)
	{
		this.client = client;
		this.packetHandlerRegisterer = packetHandlerRegisterer;
		inputHandler = new NullInputHandler();
		player = new NullPlayer();
		this.states = states;
		camera.setToOrtho(false);
		camera.viewportWidth = CAMERA_WIDTH;
		camera.viewportHeight = CAMERA_HEIGHT;
		packetHandlerRegisterer.registerPrivateClassPacketHandlers(this);
	}

	public void initialize(UserCharacterDataPacket playerData)
	{
		map = Assets.get(String.format("Map/%s.tmx", playerData.getStartingMap()));
		mapWidth = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
		mapHeight = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);
		mapRenderer = new OrthogonalTiledMapRenderer(map, Assets.getBatch());

		int newCameraX = playerData.getStartingX() - 8;
		int newCameraY = playerData.getStartingY() - 8;
		collisionMap = createCollisionMap(newCameraX, newCameraY);
		player = new Player(playerData.getId(), collisionMap, playerData, packetHandlerRegisterer);
		cameraUpdate();
		add(player);
		gameObjects.put((long) playerData.getId(), player);
		userInterface = new UserInterface(this, player, packetHandlerRegisterer);
		initializeInputHandlers();
		insertMapObjectsIntoCollisionMap(collisionMap, map);
		addClouds();
		isInitialized = true;
	}

	private void initializeInputHandlers()
	{
		inputHandler = new PlayInputContinuousHandler(userInterface, client, player);
		inputMultiplexer.addProcessor(new PlayInputSingleHandle(userInterface, player, this));
		inputMultiplexer.addProcessor(userInterface.getStage());
		inputMultiplexer.addProcessor(inputHandler);
	}

	private void addClouds()
	{
		clientGraphics.offer(new DefinedAreaCloudCluster(getMapWidth(map), getMapHeight(map)));
	}

	private float getMapWidth(TiledMap map)
	{
		return map.getProperties().get("width", Integer.class) *
				map.getProperties().get("tilewidth", Integer.class);
	}

	private float getMapHeight(TiledMap map)
	{
		return map.getProperties().get("height", Integer.class) *
				map.getProperties().get("tileheight", Integer.class);
	}

	public boolean isInitialized()
	{
		return isInitialized;
	}

	@Override
	public void render(SpriteBatch batch)
	{
		synchronizedLock(() -> doRender(batch));
	}

	private void doRender(SpriteBatch batch)
	{
		batch.setProjectionMatrix(camera.combined);
		batch.disableBlending();
		updateMapRendererView();
		mapRenderer.render(new int[] { 0 });
		batch.enableBlending();
		mapRenderer.render(new int[] { 1 });
		batch.begin();
		for (GameObject object : gameObjects.values())
			object.render(batch);

		//collisionMap.debugMethodRender(batch, Assets.get("debugTexture.png"), camera);
		batch.end();
		mapRenderer.render(new int[] { 2, 3, 4 });
		batch.begin();
		for (GraphicGameObject object : clientGraphics)
			object.render(batch);
		batch.end();
		userInterface.draw(batch);
		batch.begin();
		batch.end();
	}

	private void synchronizedLock(Runnable runnable) {
		synchronized (this) {
			runnable.run();
		}
	}

	private void updateMapRendererView()
	{
		mapRenderer.setView(camera.combined, camera.position.x - camera.viewportWidth / 2,
				(camera.position.y - camera.viewportHeight / 2) - 100, camera.viewportWidth,
				camera.viewportHeight + 100);
	}

	@Override
	public void update(float deltaTime)
	{
		synchronizedLock(() -> doUpdate(deltaTime));
	}

	private void doUpdate(float deltaTime)
	{
		for (GameObject object : gameObjects.values())
			object.update(deltaTime);

		graphicObjectsUpdate(deltaTime);
		objectHighlighter.update();
		objectInfoDisplayer.update();
		cameraUpdate();
		collisionMap.update((int) (camera.position.x - 50 - camera.viewportWidth / 2),
				(int) (camera.position.y - 50 - camera.viewportHeight / 2));
		inputHandler.process();
		userInterface.update();
	}

	public void onMouseMove(float x, float y)
	{
		currentGameMouseX = getRealX(x);
		currentGameMouseY = getRealY(y);
		highlightHoveredObject();
		displayInfoOfHoveredObject();
	}

	private void highlightHoveredObject()
	{
		GameObject object = collisionMap.getObject((int) currentGameMouseX, (int) currentGameMouseY);
		if (object != null)
		{
			Supplier<Boolean> graphicRemovalCondition = () -> !object.getCollisionRect()
					.contains(currentGameMouseX, currentGameMouseY);
			objectHighlighter.putEffect(object, graphicRemovalCondition);
		}
	}

	private void displayInfoOfHoveredObject()
	{
		GameObject object = collisionMap.getObject((int) currentGameMouseX, (int) currentGameMouseY);
		if (object != null)
		{
			Supplier<Boolean> graphicRemovalCondition = () -> !object.getCollisionRect()
					.contains(currentGameMouseX, currentGameMouseY);
			objectInfoDisplayer.putEffect(object, graphicRemovalCondition);
		}
	}

	private void cameraUpdate()
	{
		camera.position.set(player.getX() - player.getWidth() / 2, player.getY() - player.getHeight() / 2, 0);
		holdCameraInGameWorld();
		camera.update();
	}

	private void holdCameraInGameWorld()
	{
		if (camera.position.x < camera.viewportWidth / 2)
			camera.position.x = camera.viewportWidth / 2;
		if (camera.position.y < camera.viewportHeight / 2)
			camera.position.y = camera.viewportHeight / 2;
		if (camera.position.x > mapWidth - camera.viewportWidth / 2)
			camera.position.x = mapWidth - camera.viewportWidth / 2;
		if (camera.position.y > mapHeight - camera.viewportHeight / 2)
			camera.position.y = mapHeight - camera.viewportHeight / 2;
	}

	private void graphicObjectsUpdate(float deltaTime)
	{
		Iterator<GraphicGameObject> it = clientGraphics.iterator();
		while (it.hasNext())
		{
			GraphicGameObject object = it.next();
			object.update(deltaTime);
			if (object.shouldDelete())
				it.remove();
		}
	}

	@Override
	public void send(Object packet)
	{
		client.sendTCP(packet);
	}

	@Override
	public void add(GameObject object)
	{
		collisionMap.insert(object);
		gameObjects.put(object.getId(), object);
	}

	@Override
	public void add(GraphicGameObject graphic)
	{
		clientGraphics.add(graphic);
	}

	@Override
	public void removeObject(long id)
	{
		GameObject object = gameObjects.remove(id);
		GraphicObjectsContainer graphics = this;
		collisionMap.remove(object);
		object.onRemoval(graphics);
		object.unregisterHandlers(packetHandlerRegisterer);
		if (object == player)
			playerHasDied();
		else if (player.hasLockedOnTarget(object))
			player.releaseTarget();
	}

	private void playerHasDied()
	{
		this.userWantsToChangeCharacter();
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
		states.push(new SettingsChoosingState(client, states, packetHandlerRegisterer));
	}

	private void reset()
	{
		synchronizedLock(this::doReset);
	}

	private void doReset()
	{
		isInitialized = false;
		packetHandlerRegisterer.unregister(PacketHandlerCategory.USER_INTERFACE);
		inputHandler = new NullInputHandler();
		gameObjects.values().forEach(object -> object.unregisterHandlers(packetHandlerRegisterer));
		gameObjects.clear();
		clientGraphics.clear();
		userInterface.clear();
		inputMultiplexer.clear();
		collisionMap.clear();
	}

	private void insertMapObjectsIntoCollisionMap(PixelCollisionMap<GameObject> collisionMap, TiledMap worldMap)
	{
		MapObjects objects = worldMap.getLayers().get("CollisionLayer").getObjects();
		Array<RectangleMapObject> collision = objects.getByType(RectangleMapObject.class);
		collision.forEach((rectangle) -> collisionMap.insertStaticObject(rectangle.getRectangle()));
	}

	private static PixelCollisionMap<GameObject> createCollisionMap(int cameraX, int cameraY)
	{
		UndefinedStaticObjectCreator<GameObject> collisionObjectCreator = GameObject.CollisionMapGameObject::new;
		return new PixelCollisionMap<>(CAMERA_WIDTH + 100, CAMERA_HEIGHT + 100, cameraX,
				cameraY, collisionObjectCreator);
	}

	public void userWantsToChangeCharacter()
	{
		client.sendTCP(new CharacterChangePacket());
		states.push(new ChoosingCharacterState(client, states, packetHandlerRegisterer));
		reset();
	}

	public void userWantsToLogOut()
	{
		client.sendTCP(new LogoutPacket());
		states.push(new AuthenticationState(client, states, packetHandlerRegisterer));
		reset();
	}

	public void userWantsToSendMessage(String message)
	{
		ChatMessagePacket packet = PacketsMaker.makeChatMessagePacket(message);
		client.sendTCP(packet);
	}

	public void userLeftClickedOnGameBoard(float x, float y)
	{
		float realX = getRealX(x);
		float realY = getRealY(y);
		sendProperLeftClickedPacket(realX, realY);
		System.out.println("X: " + realX + ", Y: " + realY);
	}

	private void sendProperLeftClickedPacket(float realX, float realY)
	{
		Object packet = createProperLeftClickedPacket(realX, realY);
		client.sendTCP(packet);
	}

	private Object createProperLeftClickedPacket(float realX, float realY)
	{
		GameObject object = collisionMap.getObject((int) realX, (int) realY);
		if (object instanceof Shop)
			return PacketsMaker.makeOpenShopPacket(object.getId());
		else if (object instanceof QuestDialogNpc)
			return PacketsMaker.makeNpcDialogStartRequestPacket(object.getId());
		else if (object instanceof Monster)
			return PacketsMaker.makeTargetMonsterPacket(object.getId());
		else if (object instanceof QuestBoard)
			return PacketsMaker.makeGetQuestBoardInfoPacket(object.getId());
		else
		{
			drawPathTo(realX, realY);
			return PacketsMaker.makeFindPathAndGoToPacket((int) realX, (int) realY);
		}
	}

	private void drawPathTo(float x, float y)
	{
		x -= x % 10;
		y -= y % 10;
		PathFinder pathFinder = new BestFirstPathFinder();
		Point start = new Point((int) player.getX(), (int) player.getY());
		start.x -= start.x % 10;
		start.y -= start.y % 10;
		Point destination = new Point((int) x, (int) y);
		new Thread(() ->
		{
			CollisionDetector collisionDetector = new GameObjectCollisionDetector(collisionMap, player);
			Collection<? extends Point> path = pathFinder.find(start, destination,
					new ManhattanDistanceComparator(destination), collisionDetector);
			drawPath(path);
		}).start();
	}

	private void drawPath(Collection<? extends Point> path)
	{
		path.forEach(this::addPathElement);
	}

	private boolean addPathElement(Point point)
	{
		return clientGraphics.offer(new FadingRedRectangle(new Rectangle(point.x, point.y, 10, 10)));
	}

	private float getRealY(float mouseY)
	{
		return camera.position.y - camera.viewportHeight / 2 + mouseY * camera.viewportHeight / Settings.WINDOW_HEIGHT;
	}

	private float getRealX(float mouseX)
	{
		return camera.position.x - camera.viewportWidth / 2 + mouseX * camera.viewportWidth / Settings.WINDOW_WIDTH;
	}

	public boolean has(long targetId)
	{
		return gameObjects.get(targetId) != null;
	}

	private void damagePacketReceived(long targetId, int damage,
			BiFunction<Integer, GameObject, GraphicGameObject> damageLabelCreator)
	{
		Monster attackTarget = (Monster) gameObjects.get(targetId);
		attackTarget.gotHitBy(damage);
		GraphicGameObject damageNumber = damageLabelCreator.apply(damage, attackTarget);
		GraphicGameObject bloodAnimation = new BloodAnimation(attackTarget);
		clientGraphics.add(damageNumber);
		clientGraphics.add(bloodAnimation);
		if (attackTarget == player)
			userInterface.updateHitPointManaPointDialog();
	}

	public void userRightClickedOnGameBoard(float x, float y)
	{
		OpenContainterPacket packet = PacketsMaker.makeContainterOpeningPacket(getRealX(x), getRealY(y));
		client.sendTCP(packet);
	}

	public void userDistributedLevelUpPoint(Object packetToSend)
	{
		client.sendTCP(packetToSend);
	}

	private void strengthPointAllocated()
	{
		player.addStrength();
		player.decreaseLevelUpPoints();
		userInterface.levelUpPointAllocated();
	}

	private void intelligencePointAllocated()
	{
		player.addIntelligence();
		player.decreaseLevelUpPoints();
		userInterface.levelUpPointAllocated();
	}

	private void dexterityPointAllocated()
	{
		player.addDexterity();
		player.decreaseLevelUpPoints();
		userInterface.levelUpPointAllocated();
	}

	@SuppressWarnings("unused")
	private class MonsterCreationPacketHandler extends PacketHandlerBase<MonsterCreationPacket>
	{
		private final MonstersFactory monstersFactory = new MonstersFactory(packetHandlerRegisterer);

		@Override
		protected void doHandle(MonsterCreationPacket packet)
		{
			System.out.println("Monster creation");
			Monster newMonster = monstersFactory.produce(packet, collisionMap);
			PlayState.this.add(newMonster);
		}
	}

	@SuppressWarnings("unused")
	private class ThrowableObjectCreationPacketHandler extends PacketHandlerBase<ThrowableObjectCreationPacket>
	{
		private final ThrowableObjectsFactory throwableObjectsFactory = new ThrowableObjectsFactory(packetHandlerRegisterer);

		@Override
		protected void doHandle(ThrowableObjectCreationPacket packet)
		{
			System.out.println("Throwable object creation");
			ThrowableObject object = throwableObjectsFactory.produce(packet, collisionMap);
			PlayState.this.add(object);
		}
	}

	@SuppressWarnings("unused")
	private class ObjectCreationPacketHandler extends PacketHandlerBase<ObjectCreationPacket>
	{
		private final ObjectsFactory objectsFactory = new ObjectsFactory(packetHandlerRegisterer);

		@Override
		protected void doHandle(ObjectCreationPacket packet)
		{
			System.out.println("Object creation");
			GameObject newObject = objectsFactory.produce(packet, collisionMap);
			PlayState.this.add(newObject);
		}
	}

	@SuppressWarnings("unused")
	private class ObjectRemovePacketHandler extends PacketHandlerBase<ObjectRemovePacket>
	{
		@Override
		protected void doHandle(ObjectRemovePacket packet)
		{
			removeObject(packet.id);
		}
	}

	@SuppressWarnings("unused")
	private class PlayerCreationPacketHandler extends PacketHandlerBase<PlayerCreationPacket>
	{
		@Override
		protected void doHandle(PlayerCreationPacket packet)
		{
			Player player = new Player(packet.id, collisionMap, packet.data, packetHandlerRegisterer);
			add(player);
		}
	}

	@SuppressWarnings("unused")
	private class CharacterItemDataPacketHandler extends PacketHandlerBase<CharacterItemDataPacket>
	{
		@Override
		protected void doHandle(CharacterItemDataPacket itemData)
		{
			Item newItem = ItemFactory.produceItem(itemData);
			InventoryPositionPacket inventoryPosition = itemData.getInventoryPosition();
			if (inventoryPosition != null)
			{
				ItemInventoryPosition position = new ItemInventoryPosition(inventoryPosition.getInventoryPageNumber(),
						new Point(inventoryPosition.getInventoryX(), inventoryPosition.getInventoryY()));
				userInterface.addItemToEquipment(newItem, position);
				userInterface.increaseQuickAccessDialogNumbers(newItem);
			}
			else if (newItem instanceof EquipmentItem)
			{
				EquipmentPosition position = EquipmentPosition.valueOf(itemData.getEquipmentPosition());
				userInterface.addItemToEquipment((EquipmentItem) newItem, position);
			}
			else
				throw new GameException("This should not happen");
		}
	}

	@SuppressWarnings("unused")
	private class CharacterMonsterTargetingReplyPacketHandler extends PacketHandlerBase<MonsterTargetingReplyPacket>
	{
		@Override
		protected void doHandle(MonsterTargetingReplyPacket packet)
		{
			GameObject target = gameObjects.get(packet.monsterId);
			player.lockOnTarget(target);
			System.out.println("Monster targeted " + target);
		}
	}

	@SuppressWarnings("unused")
	private class ChatMessageReplyPacketHandler extends PacketHandlerBase<ChatMessageReplyPacket>
	{
		@Override
		protected void doHandle(ChatMessageReplyPacket packet)
		{
			GameObject source = gameObjects.get(packet.sourceCharacterId);
			GameWorldLabel message = new GameWorldLabel(packet.getMessage(), source);
			if (!clientGraphics.offer(message))
				Log.warn("Couldn't add graphic object ");
		}
	}

	@SuppressWarnings("unused")
	private class ExperienceGainPacketHandler extends PacketHandlerBase<ExperienceGainPacket>
	{
		@Override
		protected void doHandle(ExperienceGainPacket packet)
		{
			Player target = (Player) gameObjects.get(packet.getTargetId());
			GraphicGameObject experienceGainLabel = new ExperienceGainLabel(String.valueOf(packet.getExperience()), target);
			clientGraphics.add(experienceGainLabel);
			if (target == player)
			{
				target.addExperience(packet.getExperience());
				userInterface.updateStatsDialog();
			}
		}
	}

	@SuppressWarnings("unused")
	private class LevelUpPacketHandler extends PacketHandlerBase<LevelUpPacket>
	{
		@Override
		protected void doHandle(LevelUpPacket packet)
		{
			Player target = (Player) gameObjects.get(packet.getTargetId());
			// temp TODO make new effect/label for leveling up
			GraphicGameObject experienceGainLabel = new ExperienceGainLabel("1", target);
			clientGraphics.add(experienceGainLabel);
			if (target == player)
			{
				target.addLevel(packet.getLevelUpPoints());
				userInterface.openLevelUpPointsDialog();
				userInterface.updateStatsDialog();
			}
		}
	}

	@SuppressWarnings("unused")
	private class UseLevelUpPointOnStrengthPacketReplyHandler extends PacketHandlerBase<LevelUpPointOnStrengthSpentSuccessfullyPacket>
	{
		@Override
		protected void doHandle(LevelUpPointOnStrengthSpentSuccessfullyPacket packet)
		{
			strengthPointAllocated();
		}

	}

	@SuppressWarnings("unused")
	private class UseLevelUpPointOnIntelligencePacketReplyHandler extends PacketHandlerBase<LevelUpPointOnIntelligenceSpentSuccessfullyPacket>
	{
		@Override
		protected void doHandle(LevelUpPointOnIntelligenceSpentSuccessfullyPacket packet)
		{
			intelligencePointAllocated();
		}

	}

	@SuppressWarnings("unused")
	private class UseLevelUpPointOnDexterityReplyPacketHandler extends PacketHandlerBase<LevelUpPointOnDexteritySpentSuccessfullyPacket>
	{
		@Override
		protected void doHandle(LevelUpPointOnDexteritySpentSuccessfullyPacket packet)
		{
			dexterityPointAllocated();
		}
	}

	@SuppressWarnings("unused")
	private class FireDamagePacketHandler extends PacketHandlerBase<FireDamagePacket>
	{
		@Override
		protected void doHandle(FireDamagePacket packet)
		{
			damagePacketReceived(packet.getTargetId(), packet.getDamage(), FireDamageLabel::new);
		}

		@Override
	    public boolean canBeHandled(FireDamagePacket packet)
	    {
	        return isInitialized() && has(packet.getTargetId());
	    }
	}

	@SuppressWarnings("unused")
	private class NormalDamagePacketHandler extends PacketHandlerBase<NormalDamagePacket>
	{
		@Override
		protected void doHandle(NormalDamagePacket packet)
		{
			damagePacketReceived(packet.getTargetId(), packet.getDamage(), NormalDamageLabel::new);
		}

	    @Override
	    public boolean canBeHandled(NormalDamagePacket packet)
	    {
	        return isInitialized() && has(packet.getTargetId());
	    }
	}

	@SuppressWarnings("unused")
	private class HpChangeByItemUsagePacketHandler extends PacketHandlerBase<HpChangeByItemUsagePacket>
	{
		@Override
		protected void doHandle(HpChangeByItemUsagePacket packet)
		{
			Monster target = (Monster) gameObjects.get(packet.getMonsterTargetId());
			if(target == null) {
				return;
			}
			target.getProperties().hp += packet.getHpChange();
			HealLabel healLabel = new HealLabel(String.valueOf(packet.getHpChange()), target);
			clientGraphics.add(healLabel);
			if (target == player)
			{
				UserCharacterDataPacket data = player.getData();
				data.setHitPoints(data.getHitPoints() + packet.getHpChange());
			}
		}

	}

	@SuppressWarnings("unused")
	private class MpChangeByItemUsagePacketHandler extends PacketHandlerBase<MpChangeByItemUsagePacket>
	{
		@Override
		protected void doHandle(MpChangeByItemUsagePacket packet)
		{
			Monster target = (Monster) gameObjects.get(packet.getMonsterTargetId());
			target.getProperties().mp -= packet.getMpChange();
			ManaReplenishLabel manaReplenishLabel = new ManaReplenishLabel(String.valueOf(packet.getMpChange()), target);
			clientGraphics.add(manaReplenishLabel);
			if (target == player)
			{
				UserCharacterDataPacket data = player.getData();
				data.setManaPoints(data.getManaPoints() + packet.getMpChange());
			}

		}
	}

	@SuppressWarnings("unused")
	private class HpUpdatePacketHandler extends PacketHandlerBase<HpUpdatePacket>
	{
		@Override
		protected void doHandle(HpUpdatePacket packet)
		{
			updateHp(packet.getId(), packet.getNewHp());

		}

		private void updateHp(long targetId, int newHp)
		{
			Monster target = (Monster) getObject(targetId);
			target.getProperties().hp = newHp;
			if (target == player)
			{
				UserCharacterDataPacket data = player.getData();
				data.setHitPoints(newHp);
				userInterface.updateHitPointManaPointDialog();
			}
		}
	}

	@SuppressWarnings("unused")
	private class MpUpdatePacketHandler extends PacketHandlerBase<MpUpdatePacket>
	{
		@Override
		protected void doHandle(MpUpdatePacket packet)
		{
			updateMp(packet.getId(), packet.getNewMp());
		}

		private void updateMp(long targetId, int newMp)
		{
			Monster target = (Monster) getObject(targetId);
			target.getProperties().mp = newMp;
			if (target == player)
			{
				UserCharacterDataPacket data = player.getData();
				data.setManaPoints(newMp);
				userInterface.updateHitPointManaPointDialog();
			}
		}
	}


	@SuppressWarnings("unused")
	private class ManaDrainPacketHandler extends PacketHandlerBase<ManaDrainPacket>
	{
		@Override
		protected void doHandle(ManaDrainPacket packet)
		{
			playerUsedMana(packet.getManaDrained());
		}

		private void playerUsedMana(int manaDrain)
		{
			player.manaDrained(manaDrain);
			userInterface.updateHitPointManaPointDialog();
		}
	}


	@SuppressWarnings("unused")
	private class NpcDialogEventPacketHandler extends PacketHandlerBase<NpcDialogEventPacket>
	{

		@Override
		protected void doHandle(NpcDialogEventPacket packet)
		{
			if (isDialogStarting(packet))
				openNpcConversationDialog(packet.getNpcId(), packet.getSpeech(), packet.getPossibleAnswers());
			else
				userInterface.continueNpcConversation(packet.getNpcId(), packet.getSpeech(), packet.getPossibleAnswers());
		}

		private void openNpcConversationDialog(long npcId, String speech, String[] possibleAnswers)
		{
			Npc npc = (Npc) gameObjects.get(npcId);
			userInterface.openNpcConversationDialog(npc, speech, possibleAnswers);
		}

		private boolean isDialogStarting(NpcDialogEventPacket packet)
		{
			return !userInterface.isNpcDialogOpened(packet.getNpcId());
		}
	}

}
