package pl.mmorpg.prototype.client.items;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.client.objects.GameCharacter;

public abstract class Potion extends Item implements Useable
{
	public Potion(Texture texture, GameCharacter character)
	{
		super(texture);
	}
}
