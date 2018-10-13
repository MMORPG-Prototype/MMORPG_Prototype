package pl.mmorpg.prototype.server.packetshandling.levelup;

import com.esotericsoftware.kryonet.Connection;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class UseLevelUpPointPacketHandler<T> extends PacketHandlerBase<T>
{
	private final GameDataRetriever gameDataRetriever;
	private final PlayState playState;

	public UseLevelUpPointPacketHandler(GameDataRetriever gameDataRetriever, PlayState playState)
	{
		this.gameDataRetriever = gameDataRetriever;
		this.playState = playState;
	}

	@Override
	public synchronized void handle(Connection connection, T packet)
	{
		long characterId = gameDataRetriever.getCharacterIdByConnectionId(connection.getID());
		PlayerCharacter player = (PlayerCharacter) playState.getObject(characterId);
		if (player.getUserCharacterData().getLevelUpPoints() <= 0)
		{
			String error = "You do not have level up point to spend left!";
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket(error));
			return;
		}
		validationPassed(connection, player);
	}

	public abstract void validationPassed(Connection connection, PlayerCharacter character);
}
