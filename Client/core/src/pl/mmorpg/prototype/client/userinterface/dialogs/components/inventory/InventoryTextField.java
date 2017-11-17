package pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import pl.mmorpg.prototype.client.items.Icon;
import pl.mmorpg.prototype.client.resources.Assets;

public class InventoryTextField<T extends Icon> extends ButtonField<T>
{
	private static BitmapFont font = Assets.getFont();
	private String text;
	private float subtitlesShiftX = 0.0f;
	private float subtitlesShiftY = 0.0f;

	public InventoryTextField(String text)
	{
		this.text = text;
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
		font.setColor(0.1f, 0.1f, 0.1f, 1);
		font.draw(batch, text, getX() + getWidth() / 2 + subtitlesShiftX, getY() + getHeight() / 2 + subtitlesShiftY);
	}

	public void setTextShiftX(float shiftX)
	{
		subtitlesShiftX = shiftX;
	}

	public void setTextShiftY(float shiftY)
	{
		subtitlesShiftY = shiftY;
	}
}
