package pl.mmorpg.prototype.server.packetshandling;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.SpringApplicationContext;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.repositories.CharacterItemRepository;
import pl.mmorpg.prototype.server.database.repositories.UserCharacterRepository;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.items.GameItemsFactory;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.states.PlayState;

public class UserCharacterDataPacketHandler extends PacketHandlerBase<UserCharacterDataPacket>
{
    private Map<Integer, UserInfo> loggedUsersKeyUserId;
    private PlayState playState;
    private Server server;

    public UserCharacterDataPacketHandler(Map<Integer, UserInfo> loggedUsersKeyUserId, Server server,
            PlayState playState)
    {
        this.loggedUsersKeyUserId = loggedUsersKeyUserId;
        this.server = server; 
        this.playState = playState;
    }

    @Override
    public void handle(Connection connection, UserCharacterDataPacket packet)
    {
        userChoosenCharcter(packet.getId(), connection.getID());
    }

    private void userChoosenCharcter(int userCharacterId, int clientId)
    {
    	UserCharacterRepository characterRepo = SpringApplicationContext.getBean(UserCharacterRepository.class);
        UserCharacter character = characterRepo.findOne(userCharacterId);

        UserInfo info = loggedUsersKeyUserId.get(character.getUser().getId());
        info.userCharacter = character;

        sendCurrentGameObjectsInfo(clientId);
        PlayerCharacter newPlayer = new PlayerCharacter(character, playState);
        Collection<Item> playerItems = getPlayerItems(newPlayer);
        playerItems.forEach((item) -> newPlayer.addItem(item));
        playState.add(newPlayer);
        server.sendToAllExceptTCP(clientId, PacketsMaker.makeCreationPacket(newPlayer));
        sendItemsDataToClient(playerItems, clientId);
    }

    private Collection<Item> getPlayerItems(PlayerCharacter newPlayer)
	{
    	CharacterItemRepository itemRepo = SpringApplicationContext.getBean(CharacterItemRepository.class);
    	List<Item> characterItems = 
    			itemRepo.findByCharacter_Id((int)newPlayer.getId())
    			.stream()
    			.map(dbItem -> convertToGameItem(dbItem))
    			.collect(Collectors.toList());
    				
    	return characterItems;
	}

    private Item convertToGameItem(CharacterItem item)
    {
    	Item result = GameItemsFactory.produce(item, IdSupplier.getId());
    	return result;
    }
    
	private void sendCurrentGameObjectsInfo(int id)
    {
        Map<Long, GameObject> gameObjects = playState.getGameObjects();
        for (GameObject object : gameObjects.values())
        {

            if (object instanceof PlayerCharacter)
                server.sendToTCP(id, PacketsMaker.makeCreationPacket((PlayerCharacter) object));
            else if (object instanceof Monster)
                server.sendToTCP(id, PacketsMaker.makeCreationPacket((Monster) object));
            else
                server.sendToTCP(id, PacketsMaker.makeCreationPacket(object));
        }
    }

    private void sendItemsDataToClient(Collection<Item> playerItems, int clientId)
    {
    	playerItems.forEach((item) -> server.sendToTCP(clientId, PacketsMaker.makeItemPacket(item)));
    }
}
