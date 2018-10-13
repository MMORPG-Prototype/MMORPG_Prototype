package pl.mmorpg.prototype.server.packetshandling.levelup;

import com.esotericsoftware.kryonet.Connection;
import pl.mmorpg.prototype.clientservercommon.packets.levelup.UseLevelUpPointOnDexterityPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.states.PlayState;

public class UseLevelUpPointOnDexterityPacketHandler extends UseLevelUpPointPacketHandler<UseLevelUpPointOnDexterityPacket>
{
	public UseLevelUpPointOnDexterityPacketHandler(GameDataRetriever gameDataRetriever, PlayState playState)
	{
		super(gameDataRetriever, playState);
	}

	@Override
	public void validationPassed(Connection connection, PlayerCharacter character)
	{
		character.getUserCharacterData().setLevelUpPoints(character.getUserCharacterData().getLevelUpPoints() - 1);
		character.addDexterity();
		character.recalculateStatistics();
		connection.sendTCP(PacketsMaker.makeLevelUpPointOnDexteritySpentSuccessfullyPacket());
	}

}
