package pl.mmorpg.prototype.client.objects.interactive;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;

public class InteractiveGameObject extends GameObject
{
	public InteractiveGameObject(Texture lookout, long id, PacketHandlerRegisterer registerer)
	{
		super(lookout, id, registerer);
	}

	@Override
	public void update(float deltaTime)
	{
	}
}
