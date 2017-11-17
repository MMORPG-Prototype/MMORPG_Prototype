package pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import pl.mmorpg.prototype.client.items.Icon;
import pl.mmorpg.prototype.client.items.Reference;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.Container;

public class ButtonField<T extends Icon> extends Button implements Container<T>
{
	private static final Texture NULL_TEXTURE = Assets.get("nullTexture.png");

	private SpriteDrawable drawable;
	private final Image nullImage;

	private Reference<T> itemReference = null;
	
	public ButtonField()
	{
		super(Settings.DEFAULT_SKIN);
		nullImage = createNullImage();
		add(nullImage);
		drawable.setMinWidth(32);
		drawable.setMinHeight(32);
	}

	private Image createNullImage()
	{
		Sprite nullSprite = new Sprite(NULL_TEXTURE);
		drawable = new SpriteDrawable(nullSprite);
		return new Image(drawable);
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
	public void put(T content)
	{
		Reference<T> reference = new Reference<T>(content);
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