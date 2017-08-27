package pl.mmorpg.prototype.client.objects.interactive;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.client.objects.GameObject;

public class InteractiveGameObject extends GameObject
{
	public InteractiveGameObject(Texture lookout, long id)
	{
		super(lookout, id);
	}

	@Override
	public void update(float deltaTime)
	{
	}
}
