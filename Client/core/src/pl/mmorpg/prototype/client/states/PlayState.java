package pl.mmorpg.prototype.client.states;

import java.awt.Point;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.input.InputMultiplexer;
import pl.mmorpg.prototype.client.input.InputProcessorAdapter;
import pl.mmorpg.prototype.client.input.NullInputHandler;
import pl.mmorpg.prototype.client.input.PlayInputContinuousHandler;
import pl.mmorpg.prototype.client.input.PlayInputSingleHandle;
import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.items.ItemFactory;
import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.GraphicObjectsContainer;
import pl.mmorpg.prototype.client.objects.NullPlayer;
import pl.mmorpg.prototype.client.objects.Player;
import pl.mmorpg.prototype.client.objects.graphic.BloodAnimation;
import pl.mmorpg.prototype.client.objects.graphic.ExperienceGainLabel;
import pl.mmorpg.prototype.client.objects.graphic.FireDamageLabel;
import pl.mmorpg.prototype.client.objects.graphic.GameWorldLabel;
import pl.mmorpg.prototype.client.objects.graphic.GraphicGameObject;
import pl.mmorpg.prototype.client.objects.graphic.HealLabel;
import pl.mmorpg.prototype.client.objects.graphic.ManaReplenishLabel;
import pl.mmorpg.prototype.client.objects.graphic.NormalDamageLabel;
import pl.mmorpg.prototype.client.objects.monsters.Monster;
import pl.mmorpg.prototype.client.quests.Quest;
import pl.mmorpg.prototype.client.quests.QuestCreator;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.helpers.GameObjectsContainer;
import pl.mmorpg.prototype.client.userinterface.ShopItem;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.ConsoleDialog;
import pl.mmorpg.prototype.clientservercommon.Settings;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterChangePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessageReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.DisconnectPacket;
import pl.mmorpg.prototype.clientservercommon.packets.HpChangeByItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.LogoutPacket;
import pl.mmorpg.prototype.clientservercommon.packets.MpChangeByItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.QuestAcceptedPacket;
import pl.mmorpg.prototype.clientservercommon.packets.QuestBoardInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.QuestFinishedRewardPacket;
import pl.mmorpg.prototype.clientservercommon.packets.QuestStateInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptResultInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ShopItemPacket;
import pl.mmorpg.prototype.clientservercommon.packets.damage.FireDamagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.damage.NormalDamagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.BoardClickPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ContainerItemRemovalPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ExperienceGainPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemPutInQuickAccessBarPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemRewardRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.OpenContainterPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.QuestRewardGoldRemovalPacket;

public class PlayState implements State, GameObjectsContainer, PacketsSender, GraphicObjectsContainer
{
    private Client client;
    private StateManager states;
    private Player player;
    private Map<Long, GameObject> gameObjects = new ConcurrentHashMap<>();
    private BlockingQueue<GraphicGameObject> clientGraphics = new LinkedBlockingQueue<>();
    private InputProcessorAdapter inputHandler;
    private InputMultiplexer inputMultiplexer;
    private UserInterface userInterface;
    private TiledMapRenderer mapRenderer;
    private boolean isInitalized = false;

    private OrthographicCamera camera = new OrthographicCamera(900, 500);

    public PlayState(StateManager states, Client client)
    {
        this.client = client;
        inputHandler = new NullInputHandler();
        player = new NullPlayer();
        this.states = states;
        inputMultiplexer = new InputMultiplexer();
        camera.setToOrtho(false);
        camera.viewportWidth = 900;
        camera.viewportHeight = 500;
        TiledMap map = Assets.get("Map/tiled2.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, Assets.getBatch());

    }

    public void initialize(UserCharacterDataPacket character)
    {
        player = new Player(character.getId());
        player.initialize(character);
        gameObjects.put((long) character.getId(), player);
        inputHandler = new PlayInputContinuousHandler(client, player);
        userInterface = new UserInterface(this, character);
        inputMultiplexer.addProcessor(new PlayInputSingleHandle(userInterface.getDialogs(), player, this));
        inputMultiplexer.addProcessor(userInterface.getStage());
        inputMultiplexer.addProcessor(inputHandler);
        isInitalized = true;
    }

    public boolean isInitialized()
    {
        return isInitalized;
    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.setProjectionMatrix(camera.combined);
        batch.disableBlending();
        mapRenderer.setView(camera);
        mapRenderer.render(new int[] { 0 });
        batch.enableBlending();
        mapRenderer.render(new int[] { 1 });
        mapRenderer.render(new int[] { 2 });
        batch.begin();
        for (GameObject object : gameObjects.values())
            object.render(batch);
        for (GraphicGameObject object : clientGraphics)
            object.render(batch);
        batch.end();
        mapRenderer.render(new int[] { 3, 4, 5, 6, 7, 8 });
        userInterface.draw(batch);
    }

    @Override
    public void update(float deltaTime)
    {
        for (GameObject object : gameObjects.values())
            object.update(deltaTime);

        graphicObjectsUpdate(deltaTime);
        cameraUpdate();
        inputHandler.process();
        userInterface.update();
    }

    private void cameraUpdate()
    {
        camera.position.set(player.getX() - player.getWidth() / 2, player.getY() - player.getHeight() / 2, 0);
        camera.update();
    }

    private void graphicObjectsUpdate(float deltaTime)
    {
        Iterator<GraphicGameObject> it = clientGraphics.iterator();
        while (it.hasNext())
        {
            GraphicGameObject object = it.next();
            object.update(deltaTime);
            if (!object.isAlive())
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
        object.onRemoval(graphics);
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
        states.push(new SettingsChoosingState(client, states));

    }

    private void reset()
    {
        isInitalized = false;
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

    public void userWantsToLogOut()
    {
        client.sendTCP(new LogoutPacket());
        states.push(new AuthenticationState(client, states));
        reset();
    }

    public void newItemPacketReceived(CharacterItemDataPacket itemData)
    {
        Item newItem = ItemFactory.produceItem(itemData);
        ItemInventoryPosition position = new ItemInventoryPosition(itemData.getInventoryPageNumber(),
                new Point(itemData.getInventoryX(), itemData.getInventoryY()));

        userInterface.addItemToInventory(newItem, position);
        userInterface.increaseQuickAccessDialogNumbers(newItem);
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
        if (clientGraphics.offer(message))
        {
            Log.warn("Couldn't add graphic object ");
        }
    }

    public void userLeftClickedOnGameBoard(float x, float y)
    {
        BoardClickPacket packet = PacketsMaker.makeBoardClickPacket(getRealX(x), getRealY(y));
        client.sendTCP(packet);
    }

    private float getRealY(float y)
    {
        return player.getY() - camera.viewportHeight / 2 + y * camera.viewportHeight / Settings.GAME_HEIGHT
                - player.getHeight() / 2;
    }

    private float getRealX(float x)
    {
        return (player.getX() - camera.viewportWidth / 2 + x * camera.viewportWidth / Settings.GAME_WIDTH)
                - player.getWidth() / 2;
    }

    public void monsterTargeted(Long monsterId)
    {
        GameObject target = gameObjects.get(monsterId);
        player.lockOnTarget(target);
        System.out.println("Monster targeted " + gameObjects.get(monsterId));
    }

    public boolean has(long targetId)
    {
        return gameObjects.get(targetId) != null;
    }

    public void experienceGainPacketReceived(ExperienceGainPacket packet)
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

    public void hpChangeByItemUsagePacketReceived(HpChangeByItemUsagePacket packet)
    {
        Monster target = (Monster) gameObjects.get(packet.getMonsterTargetId());
        target.getProperties().hp += packet.getHpChange();
        HealLabel healLabel = new HealLabel(String.valueOf(packet.getHpChange()), target);
        clientGraphics.add(healLabel);
        if (target == player)
        {
            UserCharacterDataPacket data = player.getData();
            data.setHitPoints(data.getHitPoints() + packet.getHpChange());
            userInterface.updateHpMpDialog();
        }
    }

    public void mpChangeByItemUsagePacketReceived(MpChangeByItemUsagePacket packet)
    {
        Monster target = (Monster) gameObjects.get(packet.getMonsterTargetId());
        target.getProperties().mp -= packet.getMpChange();
        ManaReplenishLabel manaReplenishLabel = new ManaReplenishLabel(String.valueOf(packet.getMpChange()), target);
        clientGraphics.add(manaReplenishLabel);
        if (target == player)
        {
            UserCharacterDataPacket data = player.getData();
            data.setManaPoints(data.getManaPoints() + packet.getMpChange());
            userInterface.updateHpMpDialog();
        }
    }

    public void itemUsagePacketReceived(ItemUsagePacket packet)
    {
        userInterface.itemUsed(packet.getItemId());
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

    public void fireDamagePacketReceived(FireDamagePacket packet)
    {
        damagePacketReceived(packet.getTargetId(), packet.getDamage(),
                (damage, target) -> new FireDamageLabel(damage, target));
    }

    public void normalDamagePacketReceived(NormalDamagePacket packet)
    {
        damagePacketReceived(packet.getTargetId(), packet.getDamage(),
                (damage, target) -> new NormalDamageLabel(damage, target));
    }

    public void playerUsedMana(int manaDrain)
    {
        player.manaDrained(manaDrain);
        userInterface.updateHitPointManaPointDialog();
    }

    public void userRightClickedOnGameBoard(float x, float y)
    {
        OpenContainterPacket packet = PacketsMaker.makeContainterOpeningPacket(getRealX(x), getRealY(y));
        client.sendTCP(packet);
    }

    public void containerOpened(CharacterItemDataPacket[] contentItems, int gold, long containerId)
    {
        userInterface.containerOpened(contentItems, gold, containerId);
    }

    public void containerItemRemovalPacketReceived(ContainerItemRemovalPacket packet)
    {
        userInterface.removeContainerItem(packet.getContainerId(), packet.getItemId());
    }

    public void showTimedErrorMessage(String errorMessage, float timeout)
    {
        userInterface.showTimedErrorMessage(errorMessage, timeout);
    }

    public void decreaseGoldFromDialogInterface(long containerId, int goldAmount)
    {
        userInterface.decreaseGoldFromContainerDialog(containerId, goldAmount);
    }

    public void characterReceivedGold(int goldAmount)
    {
        MonsterProperties properties = player.getProperties();
        properties.gold += goldAmount;
        userInterface.updateGoldAmountInInventory(properties.gold);
    }

    public void updateHp(long targetId, int newHp)
    {
        Monster target = (Monster) getObject(targetId);
        target.getProperties().hp = newHp;
        if (target == player)
        {
            UserCharacterDataPacket data = player.getData();
            data.setHitPoints(newHp);
            userInterface.updateHpMpDialog();
        }

    }

    public void updateMp(long targetId, int newMp)
    {
        Monster target = (Monster) getObject(targetId);
        target.getProperties().mp = newMp;
        if (target == player)
        {
            UserCharacterDataPacket data = player.getData();
            data.setManaPoints(newMp);
            userInterface.updateHpMpDialog();
        }
    }

    public void openShopDialog(ShopItemPacket[] shopItemsPacket, long shopId)
    {
        ShopItem[] shopItems = Stream.of(shopItemsPacket).map(this::makeShopItem).toArray(ShopItem[]::new);
        userInterface.openShopDialog(shopItems, shopId);
    }

    private ShopItem makeShopItem(ShopItemPacket packet)
    {
        Item item = ItemFactory.produceItem(packet.getItem());
        ShopItem shopItem = new ShopItem(item, packet.getPrice());
        return shopItem;
    }

    public void updateCharacterGold(int newGoldAmount)
    {
        player.getProperties().gold = newGoldAmount;
        userInterface.updateGoldAmountInInventory(newGoldAmount);
    }

    public void scriptExecutionErrorReceived(String error)
    {
        ConsoleDialog console = userInterface.getDialogs().searchForDialog(ConsoleDialog.class);
        console.addErrorMessage(error);
    }

    public void repositionItemInInventory(ItemInventoryPosition sourcePosition,
            ItemInventoryPosition destinationPosition)
    {
        userInterface.repositionItemInInventory(sourcePosition, destinationPosition);
    }

    public void swapItemsInInventory(ItemInventoryPosition firstPosition, ItemInventoryPosition secondPosition)
    {
        userInterface.swapItemsInInventory(firstPosition, secondPosition);
    }

    public void putItemInQuickAccessBarPacketReceived(ItemPutInQuickAccessBarPacket packet)
    {
        userInterface.putItemInQuickAccessBar(packet.getItemIdentifier(), packet.getCellPosition());
    }

    public void scriptResultInfoPacketReceived(ScriptResultInfoPacket packet)
    {
        userInterface.addInfoMessageToConsole(packet.getMessage());
    }

    public void questBoardInfoPacketReceived(QuestBoardInfoPacket packet)
    {
        userInterface.questBoardClicked(packet.getQuests(), packet.getQuestBoardId());
    }

    public void questFinishedRewardPacketReceived(QuestFinishedRewardPacket packet)
    {
        userInterface.openNewQuestRewardDialog(packet);
    }

    public void itemRewardRemovePacketReceived(ItemRewardRemovePacket packet)
    {
        userInterface.removeItemFromQuestRewardContainer(packet);
    }

    public void questRewardGoldRemovalPacketReceived(QuestRewardGoldRemovalPacket packet)
    {
        userInterface.removeGoldFromQuestRewardDialog(packet.getGoldAmount());
    }

    public void questInfoReceived(Quest quest)
    {
        userInterface.addQuestToQuestListDialog(quest);       
    }

    public void questAcceptedPacketReceived(QuestAcceptedPacket packet)
    {
        QuestStateInfoPacket questStatePacket = packet.getQuestStatePacket();
        userInterface.removeQuestPositionFromQuestBoardDialog(questStatePacket.getQuestName());
        Quest quest = QuestCreator.create(questStatePacket);
        questInfoReceived(quest);
    }

}
