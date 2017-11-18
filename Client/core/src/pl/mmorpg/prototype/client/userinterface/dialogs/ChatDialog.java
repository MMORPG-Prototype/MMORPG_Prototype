package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.CloseButton;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.chat.ChatPane;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessageReplyPacket;

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
		Button closeButton = new CloseButton(this); 
		getTitleTable().add(closeButton).size(15, 15).padRight(-5).top().right();
		
		Table table = getContentTable(); 
		chatTextField = new TextField("", getSkin());
		
		chatTextField.addListener(new InputListener(){

			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				if(!ChatDialog.this.isVisible())
					chatTextField.setDisabled(true);
				
				if(keycode == Keys.ENTER)
					sendMessage();
				return false;
			}
		});

		chatPane = new ChatPane();
		table.add(chatPane).fillX();

		table.row();
		table.add(chatTextField).fillX().align(Align.bottom);
		table.align(Align.bottom);
	}

	private void sendMessage()
	{
		String message = chatTextField.getText();
		if(message.length() > 0)
			linkedInterface.userWantsToSendMessage(message);
		chatTextField.setText("");
			
	}

	public void addMessage(ChatMessageReplyPacket packet)
	{
		chatPane.addMessage(packet);		
	};
	
	@Override
	public void setVisible(boolean visible)
	{
		if(visible)
		{
			linkedInterface.focus(chatTextField);
			Gdx.app.postRunnable(() -> chatTextField.setText(""));
		}
		chatTextField.setText("");	
		super.setVisible(visible);
	}

}
