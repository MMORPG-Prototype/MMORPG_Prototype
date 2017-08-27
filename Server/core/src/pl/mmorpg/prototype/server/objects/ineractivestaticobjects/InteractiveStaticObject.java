package pl.mmorpg.prototype.server.objects.ineractivestaticobjects;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.server.objects.GameObject;

public class InteractiveStaticObject extends GameObject
{
	public InteractiveStaticObject(Texture lookout, long id)
	{
		super(lookout, id);
	}

	@Override
	public void update(float deltaTime)
	{
	}
	
}
