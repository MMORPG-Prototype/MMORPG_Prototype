package pl.mmorpg.prototype.server.packetshandling.levelup;

import com.esotericsoftware.kryonet.Connection;
import pl.mmorpg.prototype.clientservercommon.packets.levelup.UseLevelUpPointOnStrengthPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.Character;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;

public class UseLevelUpPointOnStrengthPacketHandler extends UseLevelUpPointPacketHandler<UseLevelUpPointOnStrengthPacket>
{
	public UseLevelUpPointOnStrengthPacketHandler(GameDataRetriever gameDataRetriever)
	{
		super(gameDataRetriever);
	}

	@Override
	public void validationPassed(Connection connection, Character character)
	{
		character.setLevelUpPoints(character.getLevelUpPoints() - 1);
		character.setStrength(character.getStrength() + 1);
		connection.sendTCP(PacketsMaker.makeLevelUpPointOnStrengthSpentSuccessfullyPacket());
	}
}
