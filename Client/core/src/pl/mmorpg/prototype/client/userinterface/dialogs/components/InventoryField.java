package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.helpers.Settings;

public class InventoryField extends Button implements ItemContainer
{
	private static final Texture NULL_TEXTURE = Assets.get("nullTexture.png");

	private SpriteDrawable drawable;
	private final Image nullImage;

	private Item item = null;

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
	public void put(Item item)
	{
		nullImage.remove();
		if (this.item != null)
			this.item.remove();
		this.item = item;
		
		add(item);
		drawable = item.getDrawable();
	}


	@Override
	public boolean hasItem()
	{
		return item != null;
	}

	@Override
	public Item getItem()
	{
		return item;
	}


	@Override
	public void removeItem()
	{
		if (item != null)
			item.remove();
		item = null;
		add(nullImage);
	}
}
