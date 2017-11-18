package pl.mmorpg.prototype.client.objects.icons.spells;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.client.objects.icons.DraggableIcon;
import pl.mmorpg.prototype.client.spells.SpellIdentifier;
import pl.mmorpg.prototype.clientservercommon.packets.SpellIdentifiers;

public abstract class Spell extends DraggableIcon
{
	public Spell(Texture texture)
	{
		super(texture);
	}
	
	public SpellIdentifiers getIdentifier()
	{
		return SpellIdentifier.getSpellIdentifier(getClass());
	}
	
	public abstract String getDescription();
	
	public abstract Object makeUsagePacket();
}
