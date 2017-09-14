package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Collection;
import java.util.Collections;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemRemovedFromQuickAccessBarPacket;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.quests.events.Event;
import pl.mmorpg.prototype.server.states.PlayState;

public class ItemRemovedFromQuickAccessBarPacketHandler extends PacketHandlerBase<ItemRemovedFromQuickAccessBarPacket>
{
	private GameDataRetriever gameData;
	private PlayState playState;

	public ItemRemovedFromQuickAccessBarPacketHandler(PlayState playState, GameDataRetriever gameData)
	{
		this.playState = playState;
		this.gameData = gameData;
	}

	@Override
	public Collection<Event> handle(Connection connection, ItemRemovedFromQuickAccessBarPacket packet)
	{
		int characterId = gameData.getCharacterIdByConnectionId(connection.getID());
		PlayerCharacter character = (PlayerCharacter)playState.getObject(characterId);
		character.removeConfigElementFromQuickAccessBar(packet.getCellPosition());
		return Collections.emptyList();
	}
	
}
