package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.Texture;

public abstract class GameCharacter extends GameObject
{
	public GameCharacter(Texture lookout, long id)
	{
		super(lookout, id);
	}

}
