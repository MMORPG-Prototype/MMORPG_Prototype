package pl.mmorpg.prototype.server;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.server.resources.Assets;

public class GameServer extends ApplicationAdapter
{
	private SpriteBatch batch;

	@Override
	public void create()
	{
		batch = new SpriteBatch();
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();
	}

	@Override
	public void dispose()
	{
		Assets.dispose();
		batch.dispose();
	}
}
