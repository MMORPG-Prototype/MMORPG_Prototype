package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import pl.mmorpg.prototype.client.items.InventoryIcon;
import pl.mmorpg.prototype.client.items.Reference;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.helpers.Settings;

public class InventoryField<T extends InventoryIcon> extends Button implements Container<T>
{
	private static final Texture NULL_TEXTURE = Assets.get("nullTexture.png");

	private SpriteDrawable drawable;
	private final Image nullImage;

	private Reference<T> itemReference = null;
	
	public InventoryField()
	{
		super(Settings.DEFAULT_SKIN);
		Sprite nullSprite = new Sprite(NULL_TEXTURE);
		drawable = new SpriteDrawable(nullSprite);

		nullImage = new Image(drawable);
		add(nullImage);
		drawable.setMinWidth(32);
		drawable.setMinHeight(32);
	}
	
	@Override
	public void setWidth(float width)
	{
		drawable.setMinWidth(width);
	}

	@Override
	public void setHeight(float height)
	{
		drawable.setMinHeight(height);
	}

	@Override
	public void put(T item)
	{
		Reference<T> reference = new Reference<T>(item);
		this.removeActor(nullImage);
		if (this.itemReference != null)
			this.removeActor(this.itemReference);
		this.itemReference = reference;
		
		add(reference);
		drawable = reference.getDrawable();
	}


	@Override
	public boolean hasContent()
	{
		return itemReference != null;
	}

	@Override
	public T getContent()
	{
		if(itemReference == null)
			return null;
		return itemReference.getObject();
	}

	@Override
	public void removeContent()
	{
		if (itemReference != null)
			this.removeActor(itemReference);
		itemReference = null;
		add(nullImage);
	}

}