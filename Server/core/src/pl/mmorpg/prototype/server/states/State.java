package pl.mmorpg.prototype.server.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State
{
    public abstract void render(SpriteBatch batch);

	public abstract void update(float deltaTime);
}
