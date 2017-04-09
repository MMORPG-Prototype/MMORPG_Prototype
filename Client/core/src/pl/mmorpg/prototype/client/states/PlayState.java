package pl.mmorpg.prototype.client.states;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.input.InputMultiplexer;
import pl.mmorpg.prototype.client.input.InputProcessorAdapter;
import pl.mmorpg.prototype.client.input.NullInputHandler;
import pl.mmorpg.prototype.client.input.PlayInputContinuousHandler;
import pl.mmorpg.prototype.client.input.PlayInputSingleHandle;
import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.items.ItemFactory;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.GameWorldLabel;
import pl.mmorpg.prototype.client.objects.NullPlayer;
import pl.mmorpg.prototype.client.objects.Player;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.helpers.GameObjectsContainer;
import pl.mmorpg.prototype.client.userinterface.GraphicGameObject;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterChangePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessageReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.DisconnectPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class PlayState implements State, GameObjectsContainer
{
    private Client client;
    private StateManager states;
    private Player player;
    private Map<Long, GameObject> gameObjects = new ConcurrentHashMap<>();
    private List<GraphicGameObject> clientGraphics = new LinkedList<>();
    private InputProcessorAdapter inputHandler;
    private InputMultiplexer inputMultiplexer;
    private UserInterface userInterface;
    private TiledMapRenderer mapRenderer;

    private OrthographicCamera camera = new OrthographicCamera(900, 500);

    public PlayState(StateManager states, Client client)
    {
        this.client = client;
        inputHandler = new NullInputHandler();
        player = new NullPlayer();
        this.states = states;
        inputMultiplexer = new InputMultiplexer();
        camera.setToOrtho(false);

        TiledMap map = Assets.get("Map/tiled.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, Assets.getBatch());
        mapRenderer.setView(camera);
    }

    public void initialize(UserCharacterDataPacket character)
    {
        player = new Player(character.getId());
        player.initialize(character);
        gameObjects.put((long) character.getId(), player);
        inputHandler = new PlayInputContinuousHandler(client, player);
        userInterface = new UserInterface(this, character);
        inputMultiplexer.addProcessor(new PlayInputSingleHandle(userInterface.getDialogs(), player));
        inputMultiplexer.addProcessor(userInterface.getStage());
        inputMultiplexer.addProcessor(inputHandler);
    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.setProjectionMatrix(camera.combined);
        mapRenderer.render(new int[] { 0 });
        batch.begin();
        for (GameObject object : gameObjects.values())
            object.render(batch);
        for(GraphicGameObject object : clientGraphics)
        	object.render(batch);

        batch.end();
        mapRenderer.render(new int[] { 1, 2, 3, 4 });
        userInterface.draw(batch);
    }

    @Override
    public void update(float deltaTime)
    {   
        for (GameObject object : gameObjects.values())
            object.update(deltaTime);
        for (GraphicGameObject object : clientGraphics)
            object.update(deltaTime);
        clientGraphics = clientGraphics.stream()
        		.filter(object -> object.isAlive())
        		.collect(Collectors.toList());

        camera.viewportWidth = 900;
        camera.viewportHeight = 500;
        camera.position.set(player.getX() - player.getWidth() / 2, player.getY() - player.getHeight() / 2, 0);
        camera.update();
        inputHandler.process();
        userInterface.update();
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
        gameObjects.clear();
        userInterface.clear();
        inputMultiplexer.clear();
    }

    public void userWantsToChangeCharacter()
    {
        client.sendTCP(new CharacterChangePacket());
        states.push(new ChoosingCharacterState(client, states));
        reset();
    }

    public void newItemPacketReceived(CharacterItemDataPacket itemData)
    {
        Item newItem = ItemFactory.produceItem(itemData);
        userInterface.addItemToInventory(newItem);
    }

	public void userWantsToSendMessage(String message)
	{
		ChatMessagePacket packet = PacketsMaker.makeChatMessagePacket(message);
		client.sendTCP(packet);
	}

	public void chatMessagePacketReceived(ChatMessageReplyPacket packet)
	{
		userInterface.addMessageToDialogChat(packet);	
		GameObject source = gameObjects.get(packet.sourceCharacterId);
		GameWorldLabel message = new GameWorldLabel(packet.getMessage(), source);
		clientGraphics.add(message);
	}
}
