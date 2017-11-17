package pl.mmorpg.prototype.client.userinterface.dialogs.components.console;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import pl.mmorpg.prototype.client.states.helpers.Settings;

public class ConsoleList extends Table
{
	public ConsoleList()
	{
		super(Settings.DEFAULT_SKIN);
	}
	
	public void addMessage(String message)
	{
		addLabel(new Label(message, getSkin()));
	}
	
	public void addMessage(String message, Color color)
	{
		Label messageLabel = new Label(message, getSkin());
		messageLabel.setColor(color);
		addLabel(messageLabel);
	}
	
	private void addLabel(Label label)
	{
		this.add(label).align(Align.left);
		this.row();
	}

}
