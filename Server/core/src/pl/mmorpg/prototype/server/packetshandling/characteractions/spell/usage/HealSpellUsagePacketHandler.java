package pl.mmorpg.prototype.server.packetshandling.characteractions.spell.usage;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.spells.HealSpellUsagePacket;
import pl.mmorpg.prototype.server.objects.monsters.spells.HealSpell;
import pl.mmorpg.prototype.server.objects.monsters.spells.Spell;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.states.PlayState;

public class HealSpellUsagePacketHandler extends SpellUsagePacketHandler<HealSpellUsagePacket>
{
    public HealSpellUsagePacketHandler(GameDataRetriever gameData, PlayState playState)
    {
        super(gameData, playState);
    }

	@Override
	protected Class<? extends Spell> getSpellType()
	{
		return HealSpell.class;
	}



}
