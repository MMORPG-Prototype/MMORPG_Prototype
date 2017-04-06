package pl.mmorpg.prototype.server.objects.map;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.server.objects.GameObject;

public abstract class MapSurroundingObject extends GameObject
{

	public MapSurroundingObject(Texture lookout, long id)
	{
		super(lookout, id);
	}

	@Override
	public void update(float deltaTime)
	{
	}
}
