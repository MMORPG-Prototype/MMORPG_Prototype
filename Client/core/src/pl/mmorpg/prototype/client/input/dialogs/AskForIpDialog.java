package pl.mmorpg.prototype.client.input.dialogs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import pl.mmorpg.prototype.client.states.SettingsChoosingState;

public class AskForIpDialog extends Dialog
{
	private final static Integer OK = 0;
	private final static Integer CANCEL = 1;
	private TextField ipField;
	private SettingsChoosingState linkedState;

	public AskForIpDialog(SettingsChoosingState state)
	{
		super("Connect to server", Settings.DEFAULT_SKIN);
		this.linkedState = state;
		ipField = new TextField("localhost", Settings.DEFAULT_SKIN);
		ipField.addListener((event) ->
		{
			if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
				this.hide();
			return true;
		});
		this.button("Ok", OK);
		button("Cancel", CANCEL);
		text("Server ip: ");
		this.getContentTable().add(ipField);
	}

	@Override
	protected void result(Object object)
	{
		if(object.equals(OK))
			transferToConnectionState();
		else
			Gdx.app.exit();
	}

	private void transferToConnectionState()
	{
		linkedState.connect(ipField.getText());
	}

}
