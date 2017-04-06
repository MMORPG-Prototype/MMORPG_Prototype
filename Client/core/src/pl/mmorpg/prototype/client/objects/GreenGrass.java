package pl.mmorpg.prototype.client.objects;

import pl.mmorpg.prototype.client.resources.Assets;

public class GreenGrass extends GameObject
{

	public GreenGrass(long id)
	{
		super(Assets.get(Assets.Textures.Map.GREEN_GRASS), id);
	}
 
	@Override
	public void update(float deltaTime)
	{
	}

}
