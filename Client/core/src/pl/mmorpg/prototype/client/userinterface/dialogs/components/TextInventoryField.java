package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import pl.mmorpg.prototype.client.resources.Assets;

public class TextInventoryField extends InventoryField
{
	private final String text;
	private final BitmapFont font = Assets.getFont();
	
	public TextInventoryField(String text)
	{
		this.text = text;
		setWidth(36);
		setHeight(28);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
		font.getData().setScale(0.85f);
		font.draw(batch, text, getX() + 24 - text.length()*2, getY() + 12);
	}
}
