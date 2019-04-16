package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.SpellPutInQuickAccessBarPacket;
import pl.mmorpg.prototype.data.entities.CharacterSpell;
import pl.mmorpg.prototype.data.entities.SpellQuickAccessBarConfigurationElement;
import pl.mmorpg.prototype.data.entities.repositories.CharacterSpellRepository;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public class SpellPutInQuickAccessBarPacketHandler extends PacketHandlerBase<SpellPutInQuickAccessBarPacket>
{
	private final CharacterSpellRepository spellRepository = SpringContext.getBean(CharacterSpellRepository.class); 
	private GameDataRetriever gameData;
	private PlayState playState;

	public SpellPutInQuickAccessBarPacketHandler(PlayState playState, GameDataRetriever gameData)
	{
		this.playState = playState;
		this.gameData = gameData;
	}
	
	@Override
	public void handle(Connection connection, SpellPutInQuickAccessBarPacket packet)
	{
		int characterId = gameData.getCharacterIdByConnectionId(connection.getID());
		PlayerCharacter character = (PlayerCharacter)playState.getObject(characterId);
		
		SpellQuickAccessBarConfigurationElement quickAccessConfigElement = new SpellQuickAccessBarConfigurationElement();
		quickAccessConfigElement.setCharacter(character.getUserCharacterData());
		quickAccessConfigElement.setFieldPosition(packet.getCellPosition());
		CharacterSpell spell = spellRepository.findByIdentifier(packet.getSpellIdentifier());
		quickAccessConfigElement.setSpell(spell);
		
		character.putNewConfigElementInSpellQuickAccessBar(quickAccessConfigElement);
	}

}
