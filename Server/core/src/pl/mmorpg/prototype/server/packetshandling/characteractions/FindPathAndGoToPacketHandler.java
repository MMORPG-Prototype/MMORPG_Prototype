package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.FindPathAndGoToPacket;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public class FindPathAndGoToPacketHandler extends PacketHandlerBase<FindPathAndGoToPacket>
{
	private final GameDataRetriever gameDataRetriever;
	private final PlayState playState;

	public FindPathAndGoToPacketHandler(GameDataRetriever gameDataRetriever, PlayState playState)
	{
		this.gameDataRetriever = gameDataRetriever;
		this.playState = playState;
	}

	@Override
	public void handle(Connection connection, FindPathAndGoToPacket packet)
	{
		int characterId = gameDataRetriever.getCharacterIdByConnectionId(connection.getID());
		PlayerCharacter player = (PlayerCharacter) playState.getObject(characterId);
		player.findPathAndGoTo(packet.getX(), packet.getY());
	}
}
