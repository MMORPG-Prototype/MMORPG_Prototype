package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Collection;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.ShopItemsPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.BoardClickPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.exceptions.CannotTargetItselfException;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.npcs.ShopItemWrapper;
import pl.mmorpg.prototype.server.objects.monsters.npcs.ShopNpc;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlingHelper;
import pl.mmorpg.prototype.server.states.PlayState;

public class CharacterBoardClickPacketHandler extends PacketHandlerBase<BoardClickPacket>
{
    private PlayState playState;
    private Map<Integer, UserInfo> loggedUsersKeyUserId;
    private Map<Integer, User> authenticatedClientsKeyClientId;

    public CharacterBoardClickPacketHandler(PlayState playState, Map<Integer, UserInfo> loggedUsersKeyUserId,
            Map<Integer, User> authenticatedClientsKeyClientId)
    {
        this.playState = playState;
        this.loggedUsersKeyUserId = loggedUsersKeyUserId;
        this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
    }

    @Override
    public void handle(Connection connection, BoardClickPacket packet)
    {
        GameObject target = playState.getCollisionMap().getTopObject(packet.gameX, packet.gameY);
        if (target != null && target instanceof Monster)
        {
            UserCharacter userCharacter = PacketHandlingHelper.getUserCharacterByConnectionId(connection.getID(),
                    loggedUsersKeyUserId, authenticatedClientsKeyClientId);
            PlayerCharacter source = (PlayerCharacter)playState.getObject(userCharacter.getId());
             
            clientBoardClickProperHandle(connection, (Monster)target, source);

        }
    }

	private void clientBoardClickProperHandle(Connection connection, Monster target, PlayerCharacter source)
	{
		if(target instanceof ShopNpc)
			sendShopItemsInfo(connection, (ShopNpc)target);
		else
			tryToTargetMonster(connection, target, source);
		
	}

	private void sendShopItemsInfo(Connection connection, ShopNpc source)
	{
		Collection<ShopItemWrapper> availableItems = source.getAvailableItems();
		ShopItemsPacket shopItemsPacket = PacketsMaker.makeShopItemsPacket(availableItems, source.getId());
		connection.sendTCP(shopItemsPacket);
	}
	
	private void tryToTargetMonster(Connection connection, Monster target, PlayerCharacter source)
	{
		try
		{
		    playState.objectTargeted(source, target);
		    connection.sendTCP(PacketsMaker.makeTargetingReplyPacket(target));
		}
		catch(CannotTargetItselfException e)
		{
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("Cannot target itself"));
		}
	}

}
