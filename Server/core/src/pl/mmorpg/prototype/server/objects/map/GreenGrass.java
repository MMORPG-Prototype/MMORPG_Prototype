package pl.mmorpg.prototype.server.objects.map;

import pl.mmorpg.prototype.server.resources.Assets;

public class GreenGrass extends MapSurroundingObject
{
	public GreenGrass(long id)
	{
		super(Assets.get("Map/greenGrass.png"), id);
	}

}
