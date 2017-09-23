package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TakingGoldFromContainerPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.containers.GameContainer;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public class TakingGoldFromContainerPacketHandler extends PacketHandlerBase<TakingGoldFromContainerPacket>
{
	private PlayState playState;
	private Server server;
	private GameDataRetriever gameData;

	public TakingGoldFromContainerPacketHandler(GameDataRetriever gameData, Server server, PlayState playState)
	{
		this.gameData = gameData;
		this.server = server;
		this.playState = playState;
	}
	
	
	@Override 
	public void handle(Connection connection, TakingGoldFromContainerPacket packet)
	{
		long characterId = gameData.getCharacterIdByConnectionId(connection.getID());
		PlayerCharacter player = (PlayerCharacter)playState.getObject(characterId);
		GameContainer container = playState.getContainer(packet.getContainerId());
		int gold = container.getGoldAmount();
		if(gold > 0)
		{
			connection.sendTCP(PacketsMaker.makeGoldReceivePacket(gold));
			server.sendToAllTCP(PacketsMaker.makeContainerGoldRemovalPacket(packet.getContainerId(), gold));
			container.setGoldAmount(0);
			player.addGold(gold);
		}
		else
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("There is no gold left in this container"));
		
	}

}
