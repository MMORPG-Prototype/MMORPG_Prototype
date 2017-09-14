package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Collection;
import java.util.Collections;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemPutInQuickAccessBarPacket;
import pl.mmorpg.prototype.server.database.entities.QuickAccessBarConfigurationElement;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.quests.events.Event;
import pl.mmorpg.prototype.server.states.PlayState;

public class ItemPutInQuickAccessBarPacketHandler extends PacketHandlerBase<ItemPutInQuickAccessBarPacket>
{
	private GameDataRetriever gameData;
	private PlayState playState;

	public ItemPutInQuickAccessBarPacketHandler(PlayState playState, GameDataRetriever gameData)
	{
		this.playState = playState;
		this.gameData = gameData;
	}
	
	@Override
	public Collection<Event> handle(Connection connection, ItemPutInQuickAccessBarPacket packet)
	{
		int characterId = gameData.getCharacterIdByConnectionId(connection.getID());
		PlayerCharacter character = (PlayerCharacter)playState.getObject(characterId);
		
		QuickAccessBarConfigurationElement quickAccessConfigElement = new QuickAccessBarConfigurationElement();
		quickAccessConfigElement.setCharacter(character.getUserCharacterData());
		quickAccessConfigElement.setFieldPosition(packet.getCellPosition());
		quickAccessConfigElement.setItemIdentifier(ItemIdentifiers.valueOf(packet.getItemIdentifier()));
		
		character.putNewConfigElemetInQuickAccessBar(quickAccessConfigElement);
		return Collections.emptyList();
	}

}
