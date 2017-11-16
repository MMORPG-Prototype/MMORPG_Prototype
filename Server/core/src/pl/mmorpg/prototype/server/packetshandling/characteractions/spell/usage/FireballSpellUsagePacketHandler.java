package pl.mmorpg.prototype.server.packetshandling.characteractions.spell.usage;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.FireballSpellUsagePacket;
import pl.mmorpg.prototype.server.objects.monsters.spells.FireballSpell;
import pl.mmorpg.prototype.server.objects.monsters.spells.Spell;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.states.PlayState;

public class FireballSpellUsagePacketHandler extends SpellUsagePacketHandler<FireballSpellUsagePacket>
{

    public FireballSpellUsagePacketHandler(GameDataRetriever gameData, PlayState playState)
    {
        super(gameData, playState);
    }
    
	@Override
	protected Class<? extends Spell> getSpellType()
	{
		return FireballSpell.class;
	}


}
