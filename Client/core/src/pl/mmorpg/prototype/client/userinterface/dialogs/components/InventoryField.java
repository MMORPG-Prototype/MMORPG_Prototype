package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.items.ItemReference;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.helpers.Settings;

public class InventoryField extends Button implements ItemContainer
{
	private static final Texture NULL_TEXTURE = Assets.get("nullTexture.png");

	private SpriteDrawable drawable;
	private final Image nullImage;

	private ItemReference itemReference = null;

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
	public void put(ItemReference item)
	{
		this.removeActor(nullImage);
		if (this.itemReference != null)
			this.removeActor(this.itemReference);
		this.itemReference = item;
		
		add(item);
		drawable = item.getDrawable();
	}


	@Override
	public boolean hasItem()
	{
		return itemReference != null;
	}

	@Override
	public Item getItem()
	{
		if(itemReference == null)
			return null;
		return itemReference.getItem();
	}


	@Override
	public void removeItem()
	{
		if (itemReference != null)
			this.removeActor(itemReference);
		itemReference = null;
		add(nullImage);
	}
}
