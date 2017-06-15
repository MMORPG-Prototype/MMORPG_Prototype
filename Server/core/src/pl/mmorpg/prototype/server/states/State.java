package pl.mmorpg.prototype.server.states;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class State
{
	public abstract void render(Batch batch);

	public abstract void update(float deltaTime);
}
