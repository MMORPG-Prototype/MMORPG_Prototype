package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import pl.mmorpg.prototype.client.resources.Assets;

public class InventoryTextField extends InventoryField
{
	private static BitmapFont font = Assets.getFont();
	private String text;
	
	public InventoryTextField(String text)
	{
		this.text = text;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
		font.setColor(0.2f, 0.2f, 0.2f, 1);
		font.draw(batch, text, getX() + getWidth()/2 - 4, getY() + getHeight() / 2 + 6);
	}
}
