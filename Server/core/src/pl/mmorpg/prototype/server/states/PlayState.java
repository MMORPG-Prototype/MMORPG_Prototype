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

import pl.mmorpg.prototype.clientservercommon.IdSupplier;
import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.Dragon;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.resources.Assets;

public class PlayState extends State implements GameObjectsContainer, PacketsSender
{
    private Server server;
    private StateManager states;
    private CollisionMap collisionMap = new CollisionMap(1500, 800);
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
        byType.forEach((rectangle) -> collisionMap.undefinedObjectInsert(rectangle.getRectangle()));

        mapRenderer = new OrthogonalTiledMapRenderer(map);
        mapRenderer.setView(camera);
        
        Gdx.input.setInputProcessor(inputHandler);
    }
    
    public void addDragon()
    {
    	Dragon dragon = new Dragon(IdSupplier.getId(), collisionMap, this);
        dragon.setPosition(400, 400);
        collisionMap.insert(dragon);
        add(dragon);
        server.sendToAllTCP(PacketsMaker.makeCreationPacket(dragon));
    }

    @Override
    public void render(SpriteBatch batch)
    {
        //mapRenderer.render(new int[] { 0, 1, 2, 3, 4 });
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

	@Override
	public void send(Object packet)
	{
		server.sendToAllTCP(packet);		
	}

	public void objectTargeted(Monster source, Monster target)
	{
		source.targetMonster(target);	
	}

	public void playerKilled(PlayerCharacter playerCharacter, Monster target)
	{
		// TODO not implemented yet
		
	}


}
