package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.ChatPane;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessagePacket;

public class ChatDialog extends Dialog
{
	private TextField chatTextField;
	private UserInterface linkedInterface;
	private ChatPane chatPane;

	public ChatDialog(UserInterface linkedInterface)
	{
		super("Chat", Settings.DEFAULT_SKIN);
		this.linkedInterface = linkedInterface;
		setBounds(0, 0, 400, 200);
		Table table = getContentTable();
		this.clear();
		this.align(Align.bottomLeft);
		
		chatPane = new ChatPane();
		table.add(chatPane).fillX();
		table.row();
		
		chatTextField = new TextField("", getSkin());
		
		chatTextField.addListener(new InputListener(){

			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				if(!ChatDialog.this.isVisible())
					chatTextField.setDisabled(true);
				else
					chatTextField.setDisabled(false);
				if(keycode == Keys.ENTER)
					sendMessage();
				return false;
			}
		});
			
		table.add(chatTextField).fillX();
		table.row();
		add(table);
	}

	private void sendMessage()
	{
		String message = chatTextField.getText();
		if(message.length() > 0)
			linkedInterface.userWantsToSendMessage(message);
		chatTextField.setText("");
			
	}

	public void addMessage(ChatMessagePacket packet)
	{
		chatPane.addMessage(packet);		
	};
	
	@Override
	public void setVisible(boolean visible)
	{
		if(visible)
			chatTextField.setText("");
		super.setVisible(visible);
	}

}
