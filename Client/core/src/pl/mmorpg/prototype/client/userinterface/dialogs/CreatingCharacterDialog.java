package pl.mmorpg.prototype.client.userinterface.dialogs;


import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import pl.mmorpg.prototype.client.states.ChoosingCharacterState;
import pl.mmorpg.prototype.client.states.helpers.Settings;

public class CreatingCharacterDialog extends Dialog
{
	private TextField nicknameField = new TextField("Nickname", getSkin());
	private ChoosingCharacterState linkedState;
	private Label errorMessage = new Label("", getSkin());
	
	public CreatingCharacterDialog(ChoosingCharacterState linkedState)
	{
		super("Create character", Settings.DEFAULT_SKIN);
		this.linkedState = linkedState;
		
		text("Nickname");
		getContentTable().add(nicknameField);
		getContentTable().row();
		getContentTable().add(errorMessage);
		getContentTable().row();
		button("Ok", DialogResults.OK);
		button("Cancel", DialogResults.CANCEL);
	}
	
	@Override
	protected void result(Object object)
	{
		if(object.equals(DialogResults.OK))
			linkedState.userSubmittedCharacterCreation(nicknameField.getText());
		else
			linkedState.userCancelledCharacterCreation();
	}
	
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage.setText(errorMessage);		
	}

}
