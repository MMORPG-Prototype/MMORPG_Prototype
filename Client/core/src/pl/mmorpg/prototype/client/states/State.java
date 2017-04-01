package pl.mmorpg.prototype.client.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface State
{
	void render(SpriteBatch batch);

	void update(float deltaTime);
}
