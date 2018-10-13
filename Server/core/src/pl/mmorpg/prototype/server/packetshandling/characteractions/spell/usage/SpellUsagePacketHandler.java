package pl.mmorpg.prototype.server.packetshandling.characteractions.spell.usage;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.spells.OffensiveSpell;
import pl.mmorpg.prototype.server.objects.monsters.spells.Spell;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class SpellUsagePacketHandler<T> extends PacketHandlerBase<T>
{
    private PlayState playState;
	private GameDataRetriever gameData;

	public SpellUsagePacketHandler(GameDataRetriever gameData, PlayState playState)
    {
        this.gameData = gameData;
        this.playState = playState;
    }
	
	@Override
	public void handle(Connection connection, T packet)
	{
		int characterId = gameData.getCharacterIdByConnectionId(connection.getID());
		PlayerCharacter character = (PlayerCharacter) playState.getObject(characterId);

		if (canCharacterUseSpell(character, getSpellType()))
			use(character, getSpellType());
	}
	
	protected abstract Class<? extends Spell> getSpellType();
	
	private boolean canCharacterUseSpell(PlayerCharacter character, Class<? extends Spell> spellType)
	{
		Spell spell = character.getKnownSpell(spellType);
		if(spell == null)
			return false;
		if(!character.hasMana(spell.getNeededMana()))
			return false;
		if(spell instanceof OffensiveSpell)
			return character.isTargetingAnotherMonster();
		return true;
	}
	
	private void use(PlayerCharacter character, Class<? extends Spell> spellType)
	{
		Spell spell = character.getKnownSpell(spellType);
		Monster target = spell instanceof OffensiveSpell ? character.getTargetedMonster() : character;
		spell.onUse(character, target, playState, playState);		
	}

}
