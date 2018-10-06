package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import org.apache.commons.text.WordUtils;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.states.helpers.Settings;

public class LineBreaker extends Table
{
	private final int wrapLength;

	public LineBreaker(String text, int wrapLength)
    {
    	this.wrapLength = wrapLength;
		setText(text);
    }


    public void setText(String text)
	{
		this.clear();
		String wrappedText = WordUtils.wrap(text, wrapLength);
		String[] lines = wrappedText.split(System.lineSeparator());
		for(String line : lines)
		{
			Label lineLabel = new Label(line, Settings.DEFAULT_SKIN);
			this.add(lineLabel);
			this.row();
		}
		this.pack();
	}
}
