package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import pl.mmorpg.prototype.client.states.ChoosingCharacterState;
import pl.mmorpg.prototype.client.states.helpers.Settings;

public class CreatingCharacterDialog extends Dialog
{
	private TextField nicknameField = new TextField("Nickname", getSkin());
	private ChoosingCharacterState linkedState;
	
	public CreatingCharacterDialog(ChoosingCharacterState linkedState)
	{
		super("Create character", Settings.DEFAULT_SKIN);
		this.linkedState = linkedState;
		
		text("Nickname");
		getContentTable().add(nicknameField);
		button("Ok", DialogResults.OK);
	}
	
	@Override
	protected void result(Object object)
	{
		if(object.equals(DialogResults.OK))
			linkedState.userSubmitedCharacterCreation(nicknameField.getText());
		else
			linkedState.userCancelledCharacterCreation();
	}

}
