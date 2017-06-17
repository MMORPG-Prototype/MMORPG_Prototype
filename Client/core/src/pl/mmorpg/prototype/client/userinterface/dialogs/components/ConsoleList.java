package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import java.util.Collection;
import java.util.LinkedList;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import pl.mmorpg.prototype.client.states.helpers.Settings;

public class ConsoleList extends Table
{
	private Collection<String> messages = new LinkedList<>(); 
	
	public ConsoleList()
	{
		super(Settings.DEFAULT_SKIN);
	}
	
	public void addMessage(String message)
	{
		messages.add(message);
		this.add(new Label(message, getSkin())).align(Align.left);
		this.row();
	}

}
