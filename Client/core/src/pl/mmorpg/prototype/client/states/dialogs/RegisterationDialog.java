package pl.mmorpg.prototype.client.states.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import pl.mmorpg.prototype.client.states.RegisterationState;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.clientservercommon.packets.RegisterationPacket;

public class RegisterationDialog extends Dialog
{
	private RegisterationState linkedState;
	private TextField usernameTextField = new TextField("", Settings.DEFAULT_SKIN);
	private TextField passwordTextField = new TextField("", Settings.DEFAULT_SKIN);
	private TextField passwordRepeatTextField = new TextField("", Settings.DEFAULT_SKIN);
	private Label errorMessageLabel = new Label("", Settings.DEFAULT_SKIN);

	public RegisterationDialog(RegisterationState linkedState)
	{
		super("Register", Settings.DEFAULT_SKIN);
		this.linkedState = linkedState;
		this.linkedState = linkedState;

		passwordTextField.setPasswordCharacter('*');
		passwordTextField.setPasswordMode(true);
		passwordRepeatTextField.setPasswordCharacter('*');
		passwordRepeatTextField.setPasswordMode(true);
		errorMessageLabel.setColor(1.0f, 0.5f, 0.5f, 1.0f);

		text("Username");
		getContentTable().add(usernameTextField);
		getContentTable().row();
		text("Password");
		getContentTable().add(passwordTextField);
		getContentTable().row();
		text("Repeat password");
		getContentTable().add(passwordRepeatTextField);
		getContentTable().row();
		getContentTable().add();
		text(errorMessageLabel);
		getContentTable().row();
		button("Ok", DialogResults.OK);
		button("Cancel", DialogResults.CANCEL);
	}

	@Override
	protected void result(Object object)
	{
		if (object.equals(DialogResults.OK))
			linkedState.registerationDialogSubmited(new RegisterationPacket(usernameTextField.getText(), passwordTextField.getText(),
							passwordRepeatTextField.getText()));
		else
			linkedState.removeItself();
	}

	public void setErrorMessage(String errorMessage)
	{
		errorMessageLabel.setText(errorMessage);
	}

}
