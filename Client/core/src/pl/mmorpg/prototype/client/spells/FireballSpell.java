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
		return "Some much longer description, you bastard, Some much longer description, you bastardSome much longer description, you bastardSome much longer description, you bastard";
	}

	@Override
	public Object makeUsagePacket()
	{
		return PacketsMaker.makeFireballSpellUsagePacket();
	}

}
