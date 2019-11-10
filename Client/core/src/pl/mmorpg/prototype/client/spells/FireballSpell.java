package pl.mmorpg.prototype.client.spells;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.objects.icons.spells.Spell;
import pl.mmorpg.prototype.client.resources.Assets;

public class FireballSpell extends Spell
{
	public FireballSpell()
	{
		super(Assets.get("Spells/fireball.png"));
	}

	@Override
	public String getDescription()
	{
		return "Don't get burned";
	}

	@Override
	public Object makeUsagePacket()
	{
		return PacketsMaker.makeFireballSpellUsagePacket();
	}

}
