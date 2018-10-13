package pl.mmorpg.prototype.server.packetshandling.levelup;

import com.esotericsoftware.kryonet.Connection;
import pl.mmorpg.prototype.clientservercommon.packets.levelup.LevelUpPointOnIntelligenceSpentSuccessfullyPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.states.PlayState;

public class UseLevelUpPointOnIntelligencePacketHandler extends UseLevelUpPointPacketHandler<LevelUpPointOnIntelligenceSpentSuccessfullyPacket>
{
	public UseLevelUpPointOnIntelligencePacketHandler(GameDataRetriever gameDataRetriever, PlayState playState)
	{
		super(gameDataRetriever, playState);
	}

	@Override
	public void validationPassed(Connection connection, PlayerCharacter character)
	{
		character.getUserCharacterData().setLevelUpPoints(character.getUserCharacterData().getLevelUpPoints() - 1);
		character.addIntelligence();
		character.recalculateStatistics();
		connection.sendTCP(PacketsMaker.makeLevelUpPointOnIntelligenceSpentSuccessfullyPacket());
	}
}
