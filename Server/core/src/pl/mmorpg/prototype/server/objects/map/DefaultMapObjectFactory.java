package pl.mmorpg.prototype.server.objects.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class DefaultMapObjectFactory implements MapFactory
{
	@Override
	public TiledMap produce()
	{
		TmxMapLoader mapLoader = new TmxMapLoader();
		TiledMap map = mapLoader.load("assets/Map/tiled.tmx");
		return map;
	}

}
