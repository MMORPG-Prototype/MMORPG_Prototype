package pl.mmorpg.prototype.server.states;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.server.collision.interfaces.StackableCollisionMap;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.collision.stackablemap.LayerCollisionMap;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.MapCollisionUnknownObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.containers.GameContainer;
import pl.mmorpg.prototype.server.objects.monsters.GreenDragon;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.RedDragon;
import pl.mmorpg.prototype.server.objects.monsters.bodies.MonsterBody;
import pl.mmorpg.prototype.server.objects.spells.Fireball;
import pl.mmorpg.prototype.server.resources.Assets;

public class PlayState extends State implements GameObjectsContainer, PacketsSender
{
    private Server server;
    private StateManager states;
    private PixelCollisionMap<GameObject> collisionMap = new PixelCollisionMap<>(1500, 800, GameObject.NULL_OBJECT);
    private StackableCollisionMap<MonsterBody> deadBodiesCollisionMap = new LayerCollisionMap<>(1500, 800, 20, 20);
    private Map<Long, GameObject> gameObjects = new ConcurrentHashMap<>();
    private TiledMapRenderer mapRenderer;
    private ServerInputHandler inputHandler = new ServerInputHandler(this);

    private OrthographicCamera camera = new OrthographicCamera(1400, 700);

    public PlayState(Server server, StateManager states)
    {
        this.server = server;
        this.states = states;
        camera.setToOrtho(false);

        collisionMap.setScale(1);
        TiledMap map = Assets.get("Map/tiled.tmx");
        MapObjects objects = map.getLayers().get("Warstwa Obiektu 1").getObjects();
        Array<RectangleMapObject> byType = objects.getByType(RectangleMapObject.class);
        byType.forEach((rectangle) -> collisionMap.insert(new MapCollisionUnknownObject(rectangle.getRectangle())));

        mapRenderer = new OrthogonalTiledMapRenderer(map);
        mapRenderer.setView(camera);

        Gdx.input.setInputProcessor(inputHandler);
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
	
	private void addMonster(Monster monster)
	{
		monster.setPosition(400, 400);
        collisionMap.insert(monster);
        add(monster);
        server.sendToAllTCP(PacketsMaker.makeCreationPacket(monster));	
	}

    @Override
    public void render(SpriteBatch batch)
    {
        // collisionMap.render(batch);
        // mapRenderer.render(new int[] { 0, 1, 2, 3, 4 });
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
    }

    @Override
    public void add(GameObject object)
    {
        gameObjects.put(object.getId(), object);
    }
    
    
    public void addDeadBody(MonsterBody monsterBody)
    {
    	add(monsterBody);
    	deadBodiesCollisionMap.insert(monsterBody);
    }

    @Override
    public GameObject remove(long objectId)
    {
        GameObject object = gameObjects.remove(objectId);
        object.onRemoval();
        collisionMap.remove(object);
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


}
