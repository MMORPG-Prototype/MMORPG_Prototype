package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.SpellRemovedFromQuickAccessBarPacket;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public class SpellRemovedFromQuickAccessBarPacketHandler extends PacketHandlerBase<SpellRemovedFromQuickAccessBarPacket>
{
	private GameDataRetriever gameData;
	private PlayState playState;

	public SpellRemovedFromQuickAccessBarPacketHandler(PlayState playState, GameDataRetriever gameData)
	{
		this.playState = playState;
		this.gameData = gameData;
	}

	@Override
	public void handle(Connection connection, SpellRemovedFromQuickAccessBarPacket packet)
	{
		int characterId = gameData.getCharacterIdByConnectionId(connection.getID());
		PlayerCharacter character = (PlayerCharacter)playState.getObject(characterId);
		character.removeConfigElementFromSpellQuickAccessBar(packet.getCellPosition());
	}
	
}
