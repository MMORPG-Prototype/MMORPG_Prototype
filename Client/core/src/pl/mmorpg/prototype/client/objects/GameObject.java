package pl.mmorpg.prototype.client.objects;

import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import pl.mmorpg.prototype.client.collision.interfaces.RectangleCollisionObject;
import pl.mmorpg.prototype.client.packethandlers.NullPacketHandlerRegisterer;
import pl.mmorpg.prototype.client.packethandlers.PacketHandler;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.Identifiable;

public abstract class GameObject extends Sprite implements RectangleCollisionObject, Identifiable
{
    private long id;
    private int layer = 0;
	private Collection<PacketHandler<?>> activePacketHandlers = new ArrayList<>();
	private PacketHandlerRegisterer registerer;

    public GameObject(Texture lookout, long id, PacketHandlerRegisterer registerer)
    {
        super(lookout);
		this.registerer = registerer;
        super.setRegion(lookout);
        this.setId(id);
    }
    
    @Override
    public Rectangle getCollisionRect()
    {
    	return getBoundingRectangle();
    }

    public void render(SpriteBatch batch)
    {
        draw(batch);
    }
    
    public TextureRegion getTextureRegion()
    {
    	return new TextureRegion(getTexture());
    }

    public abstract void update(float deltaTime);

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void onRemoval(GraphicObjectsContainer graphics)
    {
    }
    
    public void registerPacketHandler(PacketHandler<?> packetHandler)
	{
		activePacketHandlers.add(packetHandler);
		registerer.register(packetHandler);
	}
    
    public void unregisterHandlers(PacketHandlerRegisterer registerer)
    {
    	activePacketHandlers.forEach(handler -> registerer.unregister(handler));
    	activePacketHandlers.clear();
    }

	public int getLayer()
	{
		return layer;
	}

	public void setLayer(int layer)
	{
		this.layer = layer;
	}
	
	public static class CollisionMapGameObject extends GameObject
	{
		private Rectangle rectangle;

		public CollisionMapGameObject(Rectangle rectangle, int id)
		{
			super(Assets.get("nullTexture.png"), id, new NullPacketHandlerRegisterer());
			this.rectangle = rectangle;
		}
		
		@Override
	    public Rectangle getCollisionRect()
	    {
	    	return rectangle;
	    }

		@Override
		public void update(float deltaTime)
		{
		}
	}
}
