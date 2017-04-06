package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class StringValueLabel<T> extends Label
{
	private T value;
	
	public StringValueLabel(CharSequence text, Skin skin)
	{
		super(text, skin);
	}
	
	public void setValue(T obj)
	{
		value = obj;
	}
	
	public void update()
	{
		this.setText(getText() + value.toString());
	}
	
}
