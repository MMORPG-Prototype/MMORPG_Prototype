package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class StringValueLabel<T> extends Label
{
	private T value = null;
	private final String originalText;
	
	public StringValueLabel(CharSequence text, Skin skin)
	{
		this(text, skin, null);
	}
	
	public StringValueLabel(CharSequence text, Skin skin, T value)
	{
		super(text, skin);
		this.value = value;
		originalText = text.toString();
		update();
	}
	
	public void setValue(T obj)
	{
		value = obj;
	}
	
	public void update()
	{
		if(value != null)
			this.setText(originalText + value.toString());
		else
			this.setText(originalText);
	}

	public T getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return originalText + value.toString();
	}
	
}
