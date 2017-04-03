package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import pl.mmorpg.prototype.client.states.AuthenticationState;
import pl.mmorpg.prototype.client.states.helpers.Settings;

public class AuthenticationDialog extends Dialog
{
	private TextField usernameTextField = new TextField("Pankiev", Settings.DEFAULT_SKIN);
	private TextField passwordTextField = new TextField("password123", Settings.DEFAULT_SKIN);
	private AuthenticationState linkedState;
	private Label errorMessageLabel = new Label("", Settings.DEFAULT_SKIN);

	public AuthenticationDialog(AuthenticationState linkedState)
	{
		super("Authentication", Settings.DEFAULT_SKIN);
		this.linkedState = linkedState;
		passwordTextField.setPasswordCharacter('*');
		passwordTextField.setPasswordMode(true);
		errorMessageLabel.setColor(1.0f, 0.5f, 0.5f, 1.0f);
		text("Username");
		getContentTable().add(usernameTextField);
		getContentTable().row();
		text("Password");
		getContentTable().add(passwordTextField);
		getContentTable().row();
		getContentTable().add();
		text(errorMessageLabel);
		button("Login", DialogResults.OK);
		button("Cancel", DialogResults.CANCEL);
		button("Register", DialogResults.REGISTER);
		getContentTable().row();
	}

	@Override
	protected void result(Object object)
	{
		if (object.equals(DialogResults.OK))
			linkedState.sendAuthenticationRequest(usernameTextField.getText(), passwordTextField.getText());
		else if (object.equals(DialogResults.REGISTER))
			linkedState.userWantsToRegister();
		else
			linkedState.userPressedCancel();
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessageLabel.setText(errorMessage);
	}

	public String getUsername()
	{
		return usernameTextField.getText();
	}

}
