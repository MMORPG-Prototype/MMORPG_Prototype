package pl.mmorpg.prototype.client.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;

public interface State
{
	void render(SpriteBatch batch);

	void update(float deltaTime);

	void reactivate();
	
	default void unregisterHandlers(PacketHandlerRegisterer packetHandlerRegisterer) {}
}
 