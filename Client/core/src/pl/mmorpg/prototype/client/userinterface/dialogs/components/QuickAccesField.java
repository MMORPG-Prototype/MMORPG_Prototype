package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.helpers.Settings;

public class QuickAccesField extends Button 
{
	private static final Texture NULL_TEXTURE = Assets.get("nullTexture.png");

	private SpriteDrawable drawable;
	private final Image nullImage;

	private String itemIdentifier = null;

	public QuickAccesField()
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
	
	public void setQuickAccessItem(String itemIdentifier)
	{
		this.itemIdentifier = itemIdentifier;
	}
	
	public void refresh(List<InventoryPage> inventoryPages)
	{
		
	}
	

}