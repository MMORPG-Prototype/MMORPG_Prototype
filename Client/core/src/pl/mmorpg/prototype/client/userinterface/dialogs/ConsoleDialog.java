package pl.mmorpg.prototype.client.userinterface.dialogs;

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
import pl.mmorpg.prototype.client.userinterface.dialogs.components.console.ConsolePane;

public class ConsoleDialog extends Dialog
{
	private TextField inputTextField;
	private UserInterface linkedInterface;
	private ConsolePane consolePane;

	public ConsoleDialog(UserInterface linkedInterface)
	{
		super("Console", Settings.DEFAULT_SKIN);
		this.linkedInterface = linkedInterface;
		setBounds(400, 700, 600, 150);
		Button closeButton = new CloseButton(this); 
		getTitleTable().add(closeButton).size(15, 15).padRight(-5).top().right();
		
		Table table = getContentTable(); 
		inputTextField = new TextField("", getSkin());
		inputTextField.addListener(new InputListener(){

			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				if(!ConsoleDialog.this.isVisible())
					inputTextField.setDisabled(true);
				if(keycode == Keys.ENTER)
				{
					addMessageToList();
					sendCommandToExecute();
				}
				return false;
			}

		});

		consolePane = new ConsolePane();
		table.add(consolePane).fillX();

		table.row();
		table.add(inputTextField).width(570).align(Align.bottom);
		table.align(Align.bottom);
	}

	private void addMessageToList()
	{
		String command = inputTextField.getText();
		if(command.length() > 0)
			consolePane.addMessage(command);	
		
	}
	
	private void sendCommandToExecute()
	{
		String command = inputTextField.getText();
		if(command.length() > 0)
			linkedInterface.sendCommandToExecute(command);
	}

	public void addMessage(String message)
	{	
		consolePane.addMessage(message);
	};
	
	@Override
	public void setVisible(boolean visible)
	{
		if(visible)
			inputTextField.setText("");
		super.setVisible(visible);
	}

	public void addErrorMessage(String error)
	{
		consolePane.addErrorMessage(error);
	}

}
