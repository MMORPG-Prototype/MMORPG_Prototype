package pl.mmorpg.prototype.client.objects.monsters.bodies;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.client.objects.GameObject;

public abstract class DeadBody extends GameObject
{
	public DeadBody(Texture lookout, long id)
	{
		super(lookout, id);
	}

	@Override
	public void update(float deltaTime)
	{
	}

}
