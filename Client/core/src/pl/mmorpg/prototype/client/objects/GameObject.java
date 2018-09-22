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
    private final long id;
    private int layer = 0;
    private String name;
	private final Collection<PacketHandler<?>> activePacketHandlers = new ArrayList<>();
	private final PacketHandlerRegisterer registerer;

    public GameObject(Texture lookout, long id, PacketHandlerRegisterer registerer)
    {
    	this(lookout, id, registerer, "No name");
    }
    
    public GameObject(Texture lookout, long id, PacketHandlerRegisterer registerer, String name)
    {
        super(lookout);
		this.registerer = registerer;
		this.id = id;
        super.setRegion(lookout);
        this.setName(name);
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
    	activePacketHandlers.forEach(registerer::unregister);
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
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public static class CollisionMapGameObject extends GameObject
	{
		private Rectangle rectangle;

		public CollisionMapGameObject(Rectangle rectangle, int id)
		{
			super(Assets.get("nullTexture.png"), id, new NullPacketHandlerRegisterer());
			this.rectangle = rectangle;
			setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
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
