package pl.mmorpg.prototype.client.spells;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.client.objects.icons.DraggableIcon;

public abstract class Spell extends DraggableIcon
{
	public Spell(Texture texture)
	{
		super(texture);
	}
	
	public abstract String getDescription();
	
	public abstract String getName();
	
}
