package pl.mmorpg.prototype.client.states.dialogs.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.dialogs.Settings;

public class InventoryField extends Button implements ItemContainer
{
	private final Sprite sprite;
	private final SpriteDrawable drawable;
	private final Image image;
	private Item item = null;

	public InventoryField()
	{
		super(Settings.DEFAULT_SKIN);
		sprite = new Sprite((Texture) Assets.get("nullTexture.png"));
		drawable = new SpriteDrawable(sprite);
		image = new Image(drawable);
		add(image);
		drawable.setMinWidth(20);
		drawable.setMinHeight(20);
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
		this.item = item;
		setTexture(item.getTexture());
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

	public void setTexture(Texture texture)
	{
		sprite.setTexture(texture);
		setWidth(texture.getWidth());
		setHeight(texture.getHeight());
	}
}
