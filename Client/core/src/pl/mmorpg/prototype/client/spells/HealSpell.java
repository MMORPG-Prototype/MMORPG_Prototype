package pl.mmorpg.prototype.client.spells;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.objects.icons.spells.Spell;
import pl.mmorpg.prototype.client.resources.Assets;

public class HealSpell extends Spell
{

	public HealSpell()
	{
		super(Assets.get("Spells/heal.png"));
	}

	@Override
	public String getDescription()
	{
		return "Heal spell healing for 20 hp";
	}

	@Override
	public Object makeUsagePacket()
	{
		return PacketsMaker.makeHealSpellUsagePacket();
	}

}
