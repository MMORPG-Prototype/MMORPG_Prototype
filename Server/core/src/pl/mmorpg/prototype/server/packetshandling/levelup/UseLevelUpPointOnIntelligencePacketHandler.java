package pl.mmorpg.prototype.server.packetshandling.levelup;

import com.esotericsoftware.kryonet.Connection;
import pl.mmorpg.prototype.clientservercommon.packets.levelup.LevelUpPointOnIntelligenceSpentSuccessfullyPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.Character;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;

public class UseLevelUpPointOnIntelligencePacketHandler extends UseLevelUpPointPacketHandler<LevelUpPointOnIntelligenceSpentSuccessfullyPacket>
{
	public UseLevelUpPointOnIntelligencePacketHandler(GameDataRetriever gameDataRetriever)
	{
		super(gameDataRetriever);
	}

	@Override
	public void validationPassed(Connection connection, Character character)
	{
		character.setLevelUpPoints(character.getLevelUpPoints() - 1);
		character.setIntelligence(character.getIntelligence() + 1);
		connection.sendTCP(PacketsMaker.makeLevelUpPointOnIntelligenceSpentSuccessfullyPacket());
	}
}
