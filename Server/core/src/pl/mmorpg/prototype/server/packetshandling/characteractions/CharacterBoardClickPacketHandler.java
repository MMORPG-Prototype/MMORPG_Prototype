package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Collection;

import org.apache.commons.lang3.NotImplementedException;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.ShopItemsPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.BoardClickPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.exceptions.CannotTargetItselfException;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.ineractivestaticobjects.QuestBoard;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.npcs.ShopItemWrapper;
import pl.mmorpg.prototype.server.objects.monsters.npcs.ShopNpc;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public class CharacterBoardClickPacketHandler extends PacketHandlerBase<BoardClickPacket>
{
    private PlayState playState;
	private GameDataRetriever gameData;

    public CharacterBoardClickPacketHandler(PlayState playState, GameDataRetriever gameData)
    {
        this.playState = playState;
		this.gameData = gameData;
    }

    @Override
    public void handle(Connection connection, BoardClickPacket packet)
    {
        GameObject target = playState.getCollisionMap().getTopObject(packet.gameX, packet.gameY);
        if (target != null)
        {
            UserCharacter userCharacter = gameData.getUserCharacterByConnectionId(connection.getID());
            PlayerCharacter source = (PlayerCharacter)playState.getObject(userCharacter.getId());
             
            clientBoardClickProperHandle(connection, target, source);

        }
    }

	private void clientBoardClickProperHandle(Connection connection, GameObject target, PlayerCharacter source)
	{
		if(target instanceof ShopNpc)
			sendShopItemsInfo(connection, (ShopNpc)target);
		else if(target instanceof Monster)
			tryToTargetMonster(connection, (Monster)target, source);
		else if(target instanceof QuestBoard)
			sendQuestBoardInfo(connection, (QuestBoard)target);
		else
			throw new NotImplementedException("Not implemented");
		
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
	
	private void sendQuestBoardInfo(Connection connection, QuestBoard questBoard)
	{
		connection.sendTCP(PacketsMaker.makeQuestBoardInfoPacket(questBoard));
	}

}
