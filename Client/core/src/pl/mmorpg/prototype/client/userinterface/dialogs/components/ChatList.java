package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.states.helpers.Settings;

public class ChatList extends Table
{
	private List<Label> messages = new ArrayList<>();
	private List<Label> users = new ArrayList<>();
	
	public ChatList()
	{
		super(Settings.DEFAULT_SKIN);
		this.add(new Label("--------------", getSkin()));
		this.add(new Label("------------ Welcome --------------------------", getSkin()));
		this.row();
	}
	
	public void addMessage(String nickname, String message)
	{
		if(message.length() > 39)
		{
			addMessage(nickname, message.substring(0, 39));
			addMessage("", message.substring(39));
			return;
		}
		if(messages.size() > 100)
			messages.remove(0).remove();
		if(users.size() > 100)
			users.remove(0).remove();
		Label nicknameLabel = new Label(nickname + ": ", getSkin());
		users.add(nicknameLabel);
		Label label = new Label(message, getSkin());
		messages.add(label);
		this.add(nicknameLabel).padLeft(5).padRight(10).left();
		this.add(label).padLeft(10).padRight(5).left();
		this.row();
	}

}
