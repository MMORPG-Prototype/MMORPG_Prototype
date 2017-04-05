package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import pl.mmorpg.prototype.client.states.ChoosingCharacterState;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class ChoosingCharacterDialog extends Dialog
{
	private ChoosingCharacterState linkedState;
	private ButtonGroup<CheckBox> checkBoxes = new ButtonGroup<>();
	private UserCharacterDataPacket[] characters;
	private Label messageForUser;

	public ChoosingCharacterDialog(ChoosingCharacterState linkedState, UserCharacterDataPacket[] characters)
	{
		super("Choose character", Settings.DEFAULT_SKIN);
		this.linkedState = linkedState;
		this.characters = characters;

		messageForUser = new Label("", getSkin());
		messageForUser.setColor(1, 0, 0 , 1);
		if(characters.length == 0)
			messageForUser.setText("No characters, please create one");
		text("Username");
		text("Level");
		text("Choose");
		getContentTable().row();
		getContentTable().add(messageForUser).center();
		getContentTable().row();
		for (UserCharacterDataPacket character : characters)
		{
			text(character.getNickname());
			text(character.getLevel().toString());
			CheckBox checkBox = new CheckBox("", Settings.DEFAULT_SKIN);
			checkBox.setName(character.getNickname());
			checkBoxes.add(checkBox);
			getContentTable().add(checkBox);
			getContentTable().row();
		}
		
		TextButton button = new TextButton("Ok", getSkin());
		button(button, DialogResults.OK);
		button("Cancel", DialogResults.CANCEL);
		button("Create new character", DialogResults.NEW_CHARACTER);
		getContentTable().row();
	}

	@Override
	protected void result(Object object)
	{
		if(object.equals(DialogResults.OK))
		{
			int checkedIndex = checkBoxes.getCheckedIndex();
			if(checkedIndex != -1)		
				linkedState.characterChoosen(characters[checkedIndex]);
			else
			{
				messageForUser.setText("Choose character, or create one");
				this.setVisible(true);
			}
		}
		else if(object.equals(DialogResults.NEW_CHARACTER))
			linkedState.userWantsToCreateCharacter();
		else
			Gdx.app.exit();
	}

}
