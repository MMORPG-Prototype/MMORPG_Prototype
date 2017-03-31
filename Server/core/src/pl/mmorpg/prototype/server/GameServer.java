package pl.mmorpg.prototype.server;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.StateManager;

public class GameServer extends ApplicationAdapter
{
	private SpriteBatch batch;
	private StateManager states;

	@Override
	public void create()
	{
		batch = new SpriteBatch();
	}

	@Override
	public void render()
	{
		update();
		clearScreen();
		batch.begin();
		batch.end();
	}

	private void update()
	{
		states.update(Gdx.graphics.getDeltaTime());

	}

	private void clearScreen()
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void dispose()
	{
		Assets.dispose();
		batch.dispose();
	}
}
