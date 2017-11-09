package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.ItemUsagePacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.exceptions.OutOfStockException;
import pl.mmorpg.prototype.server.exceptions.PlayerUsingItemNotFoundException;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public class ItemUsagePacketHandler extends PacketHandlerBase<ItemUsagePacket>
{
	private PlayState playState;
	private GameDataRetriever gameData;

	public ItemUsagePacketHandler(GameDataRetriever gameData, PlayState playState, Server server)
	{
		this.gameData = gameData;
		this.playState = playState;
	}
	
	@Override
	public void handle(Connection connection, ItemUsagePacket packet)
	{
		int characterId = gameData.getCharacterIdByConnectionId(connection.getID());
		PlayerCharacter itemUser = (PlayerCharacter)playState.getObject(characterId);
		if(itemUser == null)
			throw new PlayerUsingItemNotFoundException();
		Monster target = (Monster)playState.getObject(packet.getTargetId());
		try
		{
			itemUser.useItem(packet.getItemId(), target, (PacketsSender)playState);
			connection.sendTCP(packet);
		}
		catch(OutOfStockException e)
		{
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("Your item stack was depleted"));
		}
	}

}
