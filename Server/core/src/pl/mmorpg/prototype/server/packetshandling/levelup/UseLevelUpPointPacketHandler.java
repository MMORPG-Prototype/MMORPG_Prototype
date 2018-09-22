package pl.mmorpg.prototype.server.packetshandling.levelup;

import com.esotericsoftware.kryonet.Connection;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.Character;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;

public abstract class UseLevelUpPointPacketHandler<T> extends PacketHandlerBase<T>
{
	private final GameDataRetriever gameDataRetriever;

	public UseLevelUpPointPacketHandler(GameDataRetriever gameDataRetriever)
	{
		this.gameDataRetriever = gameDataRetriever;
	}

	@Override
	public synchronized void handle(Connection connection, T packet)
	{
		Character character = gameDataRetriever.getUserCharacterByConnectionId(connection.getID());
		if (character.getLevelUpPoints() <= 0)
		{
			String error = "You do not have level up point to spend left!";
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket(error));
			return;
		}
		validationPassed(connection, character);
	}

	public abstract void validationPassed(Connection connection, Character character);
}
