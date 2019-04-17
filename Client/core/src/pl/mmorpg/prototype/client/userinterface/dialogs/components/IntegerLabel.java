package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class IntegerLabel extends Label
{
	public IntegerLabel(Integer value, Skin skin)
	{
		super(String.valueOf(value), skin);
	}
	
	public int getIntValue()
	{
		return Integer.valueOf(getText().toString());
	}

}
