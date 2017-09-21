package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import org.apache.commons.text.WordUtils;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.states.helpers.Settings;

public class LineBreaker extends Table
{
    public LineBreaker(String text, int wrapLength)
    {
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
