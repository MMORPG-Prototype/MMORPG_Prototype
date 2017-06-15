package pl.mmorpg.prototype.server.headless;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;

public class NullOrthogonalTiledMapRenderer implements TiledMapRenderer, Disposable
{

	@Override
	public void setView(OrthographicCamera camera)
	{
	}

	@Override
	public void setView(Matrix4 projectionMatrix, float viewboundsX, float viewboundsY, float viewboundsWidth,
			float viewboundsHeight)
	{
	}

	@Override
	public void render()
	{
	}

	@Override
	public void render(int[] layers)
	{
	}

	@Override
	public void dispose()
	{
	}

	@Override
	public void renderObjects(MapLayer layer)
	{
	}

	@Override
	public void renderObject(MapObject object)
	{
	}

	@Override
	public void renderTileLayer(TiledMapTileLayer layer)
	{
	}

	@Override
	public void renderImageLayer(TiledMapImageLayer layer)
	{
	}

}
